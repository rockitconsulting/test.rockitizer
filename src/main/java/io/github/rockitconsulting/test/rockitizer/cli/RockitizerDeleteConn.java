package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;

import java.io.File;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "delete-connector",
sortOptions = false,
headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n",
descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working) cli delete-connector <testcaseName> <teststepName> <connectorName>",
description = "Stores the current contents of the index in a new commit " +
		"along with a log message from the user describing the changes.")

public class RockitizerDeleteConn implements Runnable {

	@Parameters(index = "0", arity = "1", description = "<TestcaseName>")
    String testcase;
	
	@Parameters(index = "1", arity = "1", description = "<TeststepName>")
    String teststep;
	
	@Parameters(index = "2", arity = "1", description = "<ConnectorName>")
    String connector;
	
	@Override
	public void run() {
		
		LogUtils.disableLogging();
		
		System.out.println("----------------------------------------------------->");
		
		CommonCLI cli = new CommonCLI();

		File caseDir = new File(configuration().getFullPath() + this.testcase);

		if (caseDir.exists()) {
			File stepDir = new File(configuration().getFullPath() + this.testcase + "/" + this.teststep);
			if (stepDir.exists()) {
				cli.delete(configuration().getFullPath() + this.testcase + "/" + this.teststep + "/" + this.connector, null);
			} else {
				System.out.println("DIR: " + stepDir.getName() + " does not exist in: " + configuration().getFullPath());
			}
		} else {
			System.out.println("DIR: " + caseDir.getName() + " does not exist in: " + configuration().getFullPath());
		}
		LogUtils.enableLogging();
		
	}

}
