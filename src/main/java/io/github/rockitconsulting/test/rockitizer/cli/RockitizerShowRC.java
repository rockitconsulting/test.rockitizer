package io.github.rockitconsulting.test.rockitizer.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "show-resources",
sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working) cli show-resources [<env>]",
description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")

public class RockitizerShowRC implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
