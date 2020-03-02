package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import java.io.File;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "delete",
	sortOptions = false,
	headerHeading = "@|bold,underline Benutzung:|@%n%n",
	synopsisHeading = "%n",
	descriptionHeading = "%n@|bold,underline Description:|@%n%n",
	parameterListHeading = "%n@|bold,underline Parameters:|@%n",
	optionListHeading = "%n@|bold,underline Options:|@%n",
	header = "cli delete <testcase> [<teststep>] [<connector>]",
	description = "Stores the current contents of the index in a new commit " +
			"along with a log message from the user describing the changes.")

public class RockitizerDeleteTest implements Runnable {

	@Parameters(index = "0", arity = "1", description = " testCase name ")
	String testcase;

	@Parameters(index = "1", arity = "0..1", description = "testStep name ")
	String teststep;

	@Parameters(index = "2", arity = "0..1", description = " Connector : HTTP.<ID>, SCPPUT.<ID>, MQGET.<ID>, MQPUT.<ID>, FILEPUT.<ID>, FILEDEL.<ID>, FILEGET.<ID>, DBGET.<ID>, DBPUT.<ID>")
	String connector;
	
	@Override
	public void run() {
		
		LogUtils.disableLogging();
		
			if (teststep != null && connector != null) {
				delete(configuration().getFullPath() + this.testcase + File.separator + this.teststep + File.separator + this.connector);
			} else if (teststep != null) {
				delete(configuration().getFullPath() + this.testcase + File.separator + this.teststep);
			} else {
				delete(configuration().getFullPath() + this.testcase);
				deleteJunitClass(ConfigUtils.getAbsolutePathToJava() + testcase + ".java");
			}

		LogUtils.enableLogging();
	}

	
	private void deleteJunitClass(String path) {
		File theDir = new File(path);
		if (theDir.exists()) {
			theDir.delete();
			System.out.println("DIR: " + theDir.getName() + " deleted in: " + configuration().getFullPath());
		} else {
			System.out.println("DIR: " + theDir.getName() + " does not exist in: " + configuration().getFullPath());
		}
		
	}


	public void delete(String path) {

		File theDir = new File(path);
		if (theDir.exists()) {
			System.out.println("deleting directory: " + theDir.getName());
			boolean result = false;

			File[] allContents = theDir.listFiles();
			if (allContents != null) {
				for (File file : allContents) {
					delete(file.getPath());
				}
			}
				result = theDir.delete();
			
			if (result) {
				System.out.println("DIR: " + theDir.getName() + " deleted in: " + configuration().getFullPath());
			}
		} else {
			System.out.println("DIR: " + theDir.getName() + " does not exist in: " + configuration().getFullPath());
		}

	}


}
