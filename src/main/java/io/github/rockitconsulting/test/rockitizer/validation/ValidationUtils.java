package io.github.rockitconsulting.test.rockitizer.validation;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import static io.github.rockitconsulting.test.rockitizer.validation.model.ValidationHolder.validationHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class ValidationUtils {

	private static void checkValid(String paramName, String paramValue, List<Message> messages) {
		if (paramValue == null) {
			messages.add(new Message( Message.LEVEL.ERROR, " mandatory param [" + paramName + "] cannot be 'null'") );
		} else if (paramValue.startsWith("@") && paramValue.endsWith("@")) {
			messages.add( new Message( Message.LEVEL.ERROR, " mandatory param [" + paramName + "] with value '" + paramValue + "' must exist and cannot contain @placeholders@") );
		}
	}

	/**
	 * @usage ImmutableMap.<String, String>builder(). put(k, v). put(k, v).
	 *        build()
	 * 
	 * @param context
	 * @param fields
	 * @return
	 */
	public static Map<Context, List<Message>> checkValid(Context context, Map<String, String> fields) {

		List<Message> messages = new ArrayList<>();

		fields.forEach((f, v) -> {
			ValidationUtils.checkValid(f, v, messages);
		});

		Map<Context, List<Message>> res = new LinkedHashMap<>();
		if (!messages.isEmpty()) {
			res.put(context, messages);
		}
		return res;

	}

	
	/**
	 * Check if the testcases are synchron with resources description
	 * @throws IOException
	 */
	public static void validateConnectorRefExists() throws IOException {
		
		configuration().getTestCasesHolder().getTestCases().forEach(tc -> tc.getTestSteps().forEach(ts -> ts.getConnectorRefs().forEach(conRef -> {
			if (configuration().getResourcesHolder().findResourceByRef(conRef) == null) {
				
				validationHolder().add(
						new Context(conRef.getConRefId()),
						ImmutableList
								.<Message> builder()
								.add(new Message(Message.LEVEL.ERROR, "Connector " + conRef.getConRefId() + " exists in testcases but not in resources")).build());
				
			}
		})));
		
	}

	
	/**
	 * Git does not allow empty folders to be checked in. bugfixing it.
	 */
	public static void cleanGitIgnore(File root) {

		Iterable<File> testCases = FileUtils.listFolders(root);

		testCases.forEach(tcase -> {
			FileUtils.listFolders(tcase).forEach(tstep -> {
				if (tstep.getName().equalsIgnoreCase(Constants.OUTPUT_FOLDER)) {
					return;
				}

				FileUtils.listFolders(tstep).forEach(connector -> {
					FileUtils.listFiles(connector).forEach(payload -> {
						if (payload.getName().equals(Constants.GITIGNORE)) {
							payload.delete();
						}
					});
				});
			});

		});

	}

	/**
	 * Git does not allow empty folders to be checked in. bugfixing it.
	 */
	public static void fixGitEmptyFoldersProblem(File root) {

		Iterable<File> testCases = FileUtils.listFolders(root);

		testCases.forEach(tcase -> {
			FileUtils.listFolders(tcase).forEach(
					tstep -> {
						if (tstep.getName().equalsIgnoreCase(Constants.OUTPUT_FOLDER)) {
							return;
						}

						FileUtils.listFolders(tstep).forEach(
								connector -> {

									if (!FileUtils.listFiles(connector).iterator().hasNext()) {

										try {
											new File(connector.getAbsolutePath() + File.separator + Constants.GITIGNORE).createNewFile();
											validationHolder().add(
													new Context(connector),
													ImmutableList
															.<Message> builder()
															.add(new Message(Message.LEVEL.WARN, "Connector is empty. Creating the " + Constants.GITIGNORE
																	+ " file to enforce the github checkin ")).build());

										} catch (Exception e) {
											validationHolder().add(
													new Context(connector),
													ImmutableList
															.<Message> builder()
															.add(new Message(Message.LEVEL.ERROR, "Connector is empty. Creating the " + Constants.GITIGNORE
																	+ " failed with error " + e.getMessage())).build());
										}

									}

								});

					});

		});

	}

}
