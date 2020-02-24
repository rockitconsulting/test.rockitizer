package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class CommonCLI {
	public static Logger log = Logger.getLogger(CommonCLI.class.getName());

	
	public CommonCLI() {
		System.setProperty(Constants.INIT_CLI_KEY, "CLI");
		
	}
	
	/**
	 * CLI relevant: print testsuite
	 */
	public void printAllTests() {
		Iterable<File> testCases = FileUtils.listFolders(new File(configuration().getFullPath()));
		testCases.forEach(tcase -> {
			log.info("====================TC=======================================");
			log.info(tcase.getName());
			FileUtils.listFolders(tcase).forEach(tstep -> {
				log.info("	\\_" + tstep.getName());
				FileUtils.listFolders(tstep).forEach(connector -> {
					log.info("		\\__" + connector.getName());
					FileUtils.listFiles(connector).forEach(payload -> {
						log.info("			\\___" + payload.getName());

					});
				});

			});
			;

		});
	}

	public File findChildByName(final String context, final String name) {
		Iterable<File> childs = FileUtils.listFolders(new File(context));
		File file = StreamSupport.stream(childs.spliterator(), false).filter(f -> f.getName().equalsIgnoreCase(name)).findFirst().get();

		return file;

	}

}
