package io.github.rockitconsulting.test.rockitizer.cli;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "create-configuration",
sortOptions = false,
headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n",
descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "Record changes to the repository.",
description = "Stores the current contents of the index in a new commit " +
    	"along with a log message from the user describing the changes.")

public class RockitizerCreateConfig implements Runnable {

	@Parameters(index = "0", arity = "1", description = "ConfigurationName.....")
    String configuration;
	
	@Parameters(index = "1", description = "Environment.....")
    String environment;
	
	@Override
	public void run() {
		// TODO Check environment 
		// TODO Check create configuration
		
	}

}