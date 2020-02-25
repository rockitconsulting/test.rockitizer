package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import java.io.File;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;


import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "create-teststep",
	sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n",
		synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n",
			parameterListHeading = "%n@|bold,underline Parameters:|@%n",
				optionListHeading = "%n@|bold,underline Options:|@%n",
					header = "(Working) Create TestSteps",
						description = "Stores the current contents of the index in a new commit "
								+ "along with a log message from the user describing the changes.")
public class RockitizerCreateTS implements Runnable {

	final String rootPath = ConfigUtils.getAbsolutePathToResources();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";

	@Parameters(index = "0", arity = "1", description = "TestcaseName.....")
	String testcase;

	@Parameters(index = "1", arity = "1", description = "TeststepName.....")
	String teststep;

	@Override
	public void run() {

		LogUtils.disableLogging();
		
		System.out.println("----------------------------------------------------->");
		
		CommonCLI cli = new CommonCLI();
		
		File theDir = new File(configuration().getFullPath() + testcase);
		
		if (theDir.exists()) {
			cli.create(configuration().getFullPath() + this.testcase + "/" + this.teststep, null);
		}else{
			System.out.println("DIR: " + theDir.getName() + " does not exist in: "
					+ configuration().getFullPath());
		}
		LogUtils.enableLogging();
	}

}
