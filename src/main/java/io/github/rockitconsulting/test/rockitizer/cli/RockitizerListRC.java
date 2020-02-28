package io.github.rockitconsulting.test.rockitizer.cli;


import java.io.IOException;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "list-resources",
sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working) cli list-resources [<env>]  [ -r]",
description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")

public class RockitizerListRC implements Runnable {

	@Parameters(index = "0", arity = "0..1", description = "<env>")
	String env;
	
	@Option(names = { "-r", "--recursive" }, arity = "0..1", description = "the archive file")
	boolean recursive;

	@Override
	public void run() {

		LogUtils.disableLogging();

		CommonCLI cli = new CommonCLI();
		
		if(!env.contentEquals(null)){
		System.setProperty(Constants.ENV_KEY, env);
		}
		
		try {
			cli.listRC(env, recursive);
		} catch (IOException e) {
				System.out.println("Error: " + e);
			e.printStackTrace();
		}

		LogUtils.enableLogging();

	}
}
