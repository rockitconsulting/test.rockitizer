package io.github.rockitconsulting.test.rockitizer.cli;

import java.io.IOException;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "list-testcases",
sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working) cli list-testcases [<testcaseName>] [<true>]",
description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")
public class RockitizerListTC implements Runnable {

	@Parameters(index = "0", arity = "1", description = "<TestcaseName>")
	String testcase;
	
	@Parameters(index = "1", arity = "0..1", description = ".......")
    boolean recursive;
	
	@Override
	public void run() {

		LogUtils.disableLogging();

		CommonCLI cli = new CommonCLI();

		try {
			cli.listTC(this.testcase,this.recursive);
		} catch (IOException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}

		LogUtils.enableLogging();

	}

}
