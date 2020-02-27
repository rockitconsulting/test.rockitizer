package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;

import java.io.IOException;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "show-resources",
sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working) cli show-resources [<env>]",
description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")

public class RockitizerShowRC implements Runnable {

	String path;

	@Parameters(index = "0", arity = "0..1", description = ": [%env%] - e.g. env = dev => <configuration>-dev.yaml will be listed")
	String environment;

	@Override
	public void run() {

		LogUtils.disableLogging();

		CommonCLI cli = new CommonCLI();
		
		if (environment != null){
			
			path = configuration().getFullPath() + "resources-" + environment + ".yaml";
			
		}else{

			path = configuration().getFullPath() + "resources.yaml";

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
