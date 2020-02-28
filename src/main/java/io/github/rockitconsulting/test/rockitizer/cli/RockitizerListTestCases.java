package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "list-testcases",
sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "(Working) cli list-testcases [<testcaseName> | <all>] [<-r true>]",
description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")
public class RockitizerListTestCases implements Runnable {

	@Parameters(index = "0", arity = "1", description = "<TestcaseName>")
	String testcase;
	
	@Option(names = { "-r", "--recursive" }, arity = "0..1", description = "the archive file")
	boolean recursive;
	
	@Override
	public void run() {

		LogUtils.disableLogging();

		

//		try {
//		//	cli.listTC(this.testcase,this.recursive);
//		} catch (IOException e) {
//			System.out.println("Error: " + e);
//			e.printStackTrace();
//		}

		LogUtils.enableLogging();

	}

}
