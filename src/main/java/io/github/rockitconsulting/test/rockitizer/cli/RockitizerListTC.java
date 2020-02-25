package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "list-testcase", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "Record changes to the repository.", description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")
public class RockitizerListTC implements Runnable {

	@Parameters(index = "0", arity = "1", description = "TestcaseName.....")
	String testcase;

	@Override
	public void run() {

		LogUtils.disableLogging();

		CommonCLI cli = new CommonCLI();

		cli.listTC(this.testcase);

		LogUtils.enableLogging();

	}

}
