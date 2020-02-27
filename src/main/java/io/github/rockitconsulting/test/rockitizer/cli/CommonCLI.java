package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class CommonCLI {
	public static final Logger log = Logger.getLogger(CommonCLI.class.getName());

	/**
	 * CLI relevant: print testsuite
	 */
	public void listAll() {
		Iterable<File> testCases = FileUtils.listFolders(new File(configuration().getFullPath()));
		testCases.forEach(tcase -> {
			System.out.println("====================TC=======================================");
			System.out.println(tcase.getName());
			FileUtils.listFolders(tcase).forEach(tstep -> {
				System.out.println("	\\_" + tstep.getName());
				FileUtils.listFolders(tstep).forEach(connector -> {
					System.out.println("		\\__" + connector.getName());
					FileUtils.listFiles(connector).forEach(payload -> {
						System.out.println("			\\___" + payload.getName());

					});
				});

			});
			;

		});
	}

	public void listTC(String testcase, boolean recursive) throws IOException {

		TestCasesHolder tch1 = configuration().getTchApi().testCasesHolderFromYaml();

		if (recursive) {
			System.out.println(testcase);
			if (testcase != null && !testcase.contentEquals("all")) {
				tch1.getTestCases().forEach(tc -> {
					if (testcase.contentEquals(tc.getTestCaseName())) {
						System.out.println(tc.getTestCaseName());
						tc.getTestSteps().forEach(ts -> {
							System.out.println("	\\_" + ts.getTestStepName());
							ts.getConnectorRefs().forEach(cr -> {
								System.out.println("		\\__" + cr.getConRefId());
							});
						});
					}
				});
			} else {
				tch1.getTestCases().forEach(tc -> {
					System.out.println(tc.getTestCaseName());
					tc.getTestSteps().forEach(ts -> {
						System.out.println("	\\_" + ts.getTestStepName());
						ts.getConnectorRefs().forEach(cr -> {
							System.out.println("		\\__" + cr.getConRefId());
						});
					});
				});
			}
		} else {
			if(testcase.contentEquals("all")){
			tch1.getTestCases().forEach(tc -> {
				System.out.println(tc.getTestCaseName());
			});
			}
		}
	}
	
	public void listRC(String env, boolean recursive) throws IOException {
		
			ResourcesHolder rhyaml = configuration().getRhApi().resourcesHolderFromYaml();
			
			
			if(rhyaml.getDbConnectors().size()>0) {
				System.out.println("DBConnectors");
				rhyaml.getDbConnectors().forEach(conn -> {
					System.out.println("	\\_" + "id:"+ conn.getId()  +", type: " + conn.getType() + ( conn.getQuery()!=null?"query: "+ conn.getQuery():"" ) ); 
					
					
				});
				
			} 
		
	}

	public File findChildByName(final String context, final String name) {
		Iterable<File> childs = FileUtils.listFolders(new File(context));
		File file = StreamSupport.stream(childs.spliterator(), false).filter(f -> f.getName().equalsIgnoreCase(name)).findFirst().orElse(null);

		return file;

	}

	public void listConfig(String path) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			System.out.println(sb.toString());

		}

	}

	public void create(String path, String testcase) {

		if (testcase != null) {
			TemplateCLI tmp = new TemplateCLI();

			File file = new File(configuration().getFullPath().replaceFirst("/resources/", "/java/") + testcase + ".java");

			// Create the file
			try {
				if (file.createNewFile()) {
					tmp.createJunitClass(testcase, configuration().getFullPath().replaceFirst("/resources/", "/java/") + testcase + ".java");
					System.out.println("File is created!");
				} else {
					System.err.println("File already exists.");
				}
			} catch (IOException e) {
				System.err.println("Error occured: " + e);

			}
		}
		File theDir = new File(path);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;

			try {
				result = theDir.mkdir();
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

	public void delete(String path, String testcase) {

		if (testcase != null) {
			File jUnitFile = new File(configuration().getFullPath().replaceFirst("/resources/", "/java/") + testcase + ".java");

			if (jUnitFile.delete()) {
				System.out.println(testcase + ".java" + " File deleted successfully");
			} else {
				System.err.println(testcase + ".java" + " Failed to delete the file");
			}
		}
		File theDir = new File(path);
		// if the directory does not exist, create it
		if (theDir.exists()) {
			System.out.println("deleting directory: " + theDir.getName());
			boolean result = false;

			File[] allContents = theDir.listFiles();
			if (allContents != null) {
				for (File file : allContents) {
					delete(file.getPath(), null);
				}
			}

			try {
				result = theDir.delete();
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR: " + theDir.getName() + " deleted in: " + configuration().getFullPath());
			}
		} else {
			System.out.println("DIR: " + theDir.getName() + " does not exist in: " + configuration().getFullPath());
		}

		LogUtils.enableLogging();

	}

	public void deleteConfig(String path) {

		File jUnitFile = new File(path);

		if (jUnitFile.delete()) {
			System.out.println(" File deleted successfully");
		} else {
			System.out.println(" Failed to delete the file");
		}

	}

}
