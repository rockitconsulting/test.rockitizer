package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;


import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "create-testcase",
sortOptions = false,
headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n",
descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "Record changes to the repository.",
description = "Stores the current contents of the index in a new commit " +
    	"along with a log message from the user describing the changes.")


public class RockitizerCreateTC implements Runnable {
	
	final String rootPath  = ConfigUtils.getAbsoluteRootPath();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";
	
	@Parameters(index = "0", arity = "1", description = "TestcaseName.....")
    String testcase;

	@Override
	public void run() {
		
		// TODO Check if testcase exist
		// TODO Create testcase
		// TODO Create junit test
		
	}

}
