package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;

public class CommonCLI {
	public static Logger log = Logger.getLogger(CommonCLI.class.getName());

	
	
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

	public void create(String path) {

		File theDir = new File(path);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created in: " + configuration().getFullPath());
			}
		} else {
			System.out.println("DIR already exists in: " + configuration().getFullPath());
		}

	}

	public void delete(String path) {

		File theDir = new File(path);
		// if the directory does not exist, create it
		if (theDir.exists()) {
			System.out.println("deleting directory: " + theDir.getName());
			boolean result = false;

			File[] allContents = theDir.listFiles();
			if (allContents != null) {
				for (File file : allContents) {
					delete(file.getPath());
				}
			}

			try {
				theDir.delete();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR: " + theDir.getName() + " deleted in: " + configuration().getFullPath());
			}
		} else {
			System.out.println("DIR: " + theDir.getName() + " does not exist in: " + configuration().getFullPath());
		}

	}

}
