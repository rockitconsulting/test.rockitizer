package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;


import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "create-teststep", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "Record changes to the repository.", description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")
public class RockitizerCreateTS implements Runnable {

	final String rootPath = ConfigUtils.getAbsoluteRootPath();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";

	@Parameters(index = "0", arity = "1", description = "TestcaseName.....")
	String testcase;

	@Parameters(index = "1", arity = "1", description = "TeststepName.....")
	String teststep;

	@Override
	public void run() {

		// TODO Check if testcase exist
		// TODO Check if teststep exist
		// TODO Create teststep
		// TODO Append to junit test
	}

}
