package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;

import java.io.File;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "delete-teststep",
sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working) cli delete-teststep <testcaseName> <teststepName>",
description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")
public class RockitizerDeleteTS implements Runnable {

	@Parameters(index = "0", arity = "1", description = "<TestcaseName>")
	String testcase;

	@Parameters(index = "1", arity = "1", description = "<TeststepName>")
	String teststep;

	@Override
	public void run() {
		
		LogUtils.disableLogging();
		
		System.out.println("----------------------------------------------------->");

		CommonCLI cli = new CommonCLI();

		File theDir = new File(configuration().getFullPath() + testcase);

		if (theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			cli.delete(configuration().getFullPath() + this.testcase + "/" + this.teststep, null);
		} else {
			System.out.println("DIR: " + theDir.getName() + " does not exist in: " + configuration().getFullPath());
		}
		LogUtils.enableLogging();

	}

}
