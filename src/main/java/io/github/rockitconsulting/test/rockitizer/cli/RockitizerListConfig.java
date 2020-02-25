package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import java.io.IOException;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "list-config",
sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working) List Configurations",
description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")
public class RockitizerListConfig implements Runnable {

	String path;

	@Parameters(index = "0", arity = "1", description = "ConfigurationName.....resources or testcases")
	String configuration;

	@Parameters(index = "1", arity = "0", description = "Environment.....")
	String environment;

	@Override
	public void run() {

		LogUtils.disableLogging();

		CommonCLI cli = new CommonCLI();
		
		if (environment != null){
			
			path = configuration().getFullPath() + configuration + "." + environment + ".yaml";
			
		}else{

			path = configuration().getFullPath() + configuration + ".yaml";

		}
		
		try {
			cli.listConfig(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LogUtils.enableLogging();

	}
}
