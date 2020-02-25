package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;

@CommandLine.Command(name = "list",
sortOptions = false,
headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n",
descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working) List All",
description = "Stores the current contents of the index in a new commit " +
    	"along with a log message from the user describing the changes.")

public class RockitizerListAll implements Runnable {

	
	
	@Override
	public void run() {
		
		LogUtils.disableLogging();
		
		CommonCLI cli = new CommonCLI();
		
		cli.listAll();
		
		LogUtils.enableLogging();
		
	}





}
