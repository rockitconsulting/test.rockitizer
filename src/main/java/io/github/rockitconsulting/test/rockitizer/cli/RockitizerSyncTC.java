package io.github.rockitconsulting.test.rockitizer.cli;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "sync-testcase",
	sortOptions = false,
	headerHeading = "@|bold,underline Benutzung:|@%n%n",
	synopsisHeading = "%n",
	descriptionHeading = "%n@|bold,underline Description:|@%n%n",
	parameterListHeading = "%n@|bold,underline Parameters:|@%n",
	optionListHeading = "%n@|bold,underline Options:|@%n",
	header = "Synchronice TestCases",
	description = "Stores the current contents of the index in a new commit " +
			"along with a log message from the user describing the changes.")

public class RockitizerSyncTC implements Runnable {

	@Parameters(index = "0", arity = "1", description = "TestcaseName.....")
    String testcase;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
