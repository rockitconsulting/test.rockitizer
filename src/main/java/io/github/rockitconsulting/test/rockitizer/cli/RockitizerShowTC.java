package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;

import java.io.IOException;

import picocli.CommandLine;

@CommandLine.Command(name = "show-testcases",
sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working) cli show-testcases",
description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")

public class RockitizerShowTC implements Runnable {

	String path;

	@Override
	public void run() {

		LogUtils.disableLogging();

		CommonCLI cli = new CommonCLI();
		
		path = configuration().getFullPath() + "testcases.yaml";

		try {
			cli.listConfig(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LogUtils.enableLogging();

	}

}
