package io.github.rockitconsulting.test.rockitizer.validation;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import static io.github.rockitconsulting.test.rockitizer.validation.ValidationHolder.validationHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.ConnectorRef;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestCase;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestStep;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class ValidationUtils {

	/**
	 *  ImmutableMap.&lt;String, String&gt;builder(). put(k, v). put(k, v).
	 *        build()
	 * 
	 * @param context
	 * @param fields
	 * @return
	 */
	public static Map<Context, List<Message>> checkFieldsValid(Context context, Map<String, String> fields) {

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
	 * 
	 * 
	 */
	public static void validateConnectorRefExists() {

		configuration()
				.getTestCasesHolder()
				.getTestCases()
				.forEach(
						tc -> tc.getTestSteps().forEach(
								ts -> ts.getConnectorRefs().forEach(
										conRef -> {
											if (configuration().getResourcesHolder().findResourceByRef(conRef) == null) {

												validationHolder().add(
														new Context.Builder().withId(conRef.getConRefId()),
														new Message(Message.LEVEL.ERROR, "Connector " + conRef.getConRefId()
																+ " exists in testcases but not in resources"));

											}
										})));

	}

	/**
	 * Git does not allow empty folders to be checked in. bugfixing it.
	 */
	public static void cleanGitIgnore() {

		Iterable<File> testCases = FileUtils.listFolders(new File(configuration().getFullPath()));

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
	public static void fixGitEmptyFoldersProblem() {

		Iterable<File> testCases = FileUtils.listFolders(new File(configuration().getFullPath()));

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
													new Context.Builder().withConnector(connector),
													new Message(Message.LEVEL.WARN, "Connector is empty. Creating the " + Constants.GITIGNORE
															+ " file to enforce the github checkin "));

										} catch (Exception e) {
											validationHolder().add(
													new Context.Builder().withConnector(connector),
													new Message(Message.LEVEL.ERROR, "Connector is empty. Creating the " + Constants.GITIGNORE
															+ " failed with error " + e.getMessage()));

										}

									}

								});

					});

		});

	}

	/**
	 * Process validation for all resources
	 * 
	 * 
	 */
	public static void validateResources() {

		ValidationUtils.validateResources(configuration().getResourcesHolder().getMqConnectors());
		ValidationUtils.validateResources(configuration().getResourcesHolder().getDbConnectors());
		ValidationUtils.validateResources(configuration().getResourcesHolder().getFileConnectors());
		ValidationUtils.validateResources(configuration().getResourcesHolder().getHttpConnectors());
		ValidationUtils.validateResources(configuration().getResourcesHolder().getScpConnectors());

		ValidationUtils.validateResources(configuration().getResourcesHolder().getMqDataSources());
		ValidationUtils.validateResources(configuration().getResourcesHolder().getDbDataSources());
		ValidationUtils.validateResources(configuration().getResourcesHolder().getKeyStores());

	}

	/**
	 * Mirror validation of filesystem and tescases sync
	 * 
	 * @throws IOException
	 */
	public static void validateTestCasesAndFileSystemInSync() throws IOException {
		String testcases = configuration().getTchApi().getTestcasesFileName();

		TestCasesHolder tch1 = configuration().getTchApi().testCasesHolderFromYaml();
		TestCasesHolder tch2 = configuration().getTchApi().testCasesHolderFromFileSystem();

		validateAndCollectErrorContext(tch1, tch2).forEach(
				c -> ValidationHolder.validationHolder().add(c, new Message(Message.LEVEL.ERROR, "exists in " + testcases + ", but not found in FileSystem")));
		validateAndCollectErrorContext(tch2, tch1).forEach(
				c -> ValidationHolder.validationHolder().add(c, new Message(Message.LEVEL.ERROR, "exists in FileSystem, but not found in " + testcases)));

	}

	/**
	 * Mirror validation of filesystem and tescases sync
	 * 
	 * @param tch1
	 * @param tch2
	 * @return
	 */
	private static List<Context> validateAndCollectErrorContext(TestCasesHolder tch1, TestCasesHolder tch2) {
		List<Context> errorContext = new ArrayList<>();

		tch1.getTestCases().forEach(tc1 -> {

			TestCase matchCase = tch2.getTestCases().stream().filter(tc -> tc.equals(tc1)).findAny().orElse(null);

			if (matchCase == null) {
				errorContext.add(new Context.Builder().withTestCase(tc1.getTestCaseName()));

			} else {

				tc1.getTestSteps().forEach(ts1 -> {
					TestStep matchStep = matchCase.getTestSteps().stream().filter(ts2 -> ts2.equals(ts1)).findAny().orElse(null);

					if (matchStep == null) {
						errorContext.add(new Context.Builder().withTestStep(tc1.getTestCaseName(), ts1.getTestStepName()));

					} else {

						ts1.getConnectorRefs().forEach(cref1 -> {

							ConnectorRef matchCref2 = matchStep.getConnectorRefs().stream().filter(conreffs -> conreffs.equals(cref1)).findAny().orElse(null);

							if (matchCref2 == null) {
								errorContext.add(new Context.Builder().withConnector(tc1.getTestCaseName(), ts1.getTestStepName(), cref1.getConRefId()));

							}

						});

					}

				});

			}
		});
		return errorContext;
	}

	
	/**
	 *  validation directory structure and tescases 
	 *  make warnings for empty testcases/tessteps
	 * 
	 * @throws IOException
	 */
	public static void validateNotAllowedEmptyStructures() throws IOException {
		String testcases = configuration().getTchApi().getTestcasesFileName();

		TestCasesHolder tch1 = configuration().getTchApi().testCasesHolderFromYaml();
		TestCasesHolder tch2 = configuration().getTchApi().testCasesHolderFromFileSystem();

		validateNotAllowedEmptyStructures(tch1).forEach(
				c -> ValidationHolder.validationHolder().add(c, new Message(Message.LEVEL.WARN, "empty structure in " + testcases )));
		validateNotAllowedEmptyStructures(tch2).forEach(
				c -> ValidationHolder.validationHolder().add(c, new Message(Message.LEVEL.WARN, "empty structure in FileSystem")));

	}
	
	
	/**
	 * make warnings for empty testcases/tessteps
	 * 
	 * @param tch1
	 * @param tch2
	 * @return
	 */
	private static List<Context> validateNotAllowedEmptyStructures(TestCasesHolder tch1) {
		List<Context> errorContext = new ArrayList<>();

		tch1.getTestCases().forEach(tc1 -> {

			if (tc1.getTestSteps().isEmpty()) {
				errorContext.add(new Context.Builder().withTestCase(tc1.getTestCaseName()));
				return;

			}

			tc1.getTestSteps().forEach(ts1 -> {

				if (ts1.getConnectorRefs().isEmpty()) {
					errorContext.add(new Context.Builder().withTestStep(tc1.getTestCaseName(), ts1.getTestStepName()));

				}

			});

		});
		return errorContext;
	}

	/**
	 * Process validation for single resource
	 * 
	 * @param validatables
	 */
	private static void validateResources(List<? extends Validatable> validatables) {
		validatables.forEach(c -> validationHolder().addAll(c.validate()));

	}

	private static void checkValid(String paramName, String paramValue, List<Message> messages) {
		if (paramValue == null) {
			messages.add(new Message(Message.LEVEL.ERROR, " mandatory param [" + paramName + "] cannot be 'null'"));
		} else if (paramValue.startsWith("@") && paramValue.endsWith("@")) {
			messages.add(new Message(Message.LEVEL.ERROR, " mandatory param [" + paramName + "] with value '" + paramValue
					+ "' must exist and cannot contain @placeholders@"));
		}
	}

}
