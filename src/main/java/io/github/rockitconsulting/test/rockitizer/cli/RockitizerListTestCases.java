package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import java.io.IOException;

import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
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

	@Parameters(index = "0", arity = "0..1", description = "<TestcaseName>")
	String testcase;
	
	@Option(names = { "-r", "--recursive" }, arity = "0..1", description = "the archive file")
	boolean recursive;
	
	@Override
	public void run() {

		LogUtils.disableLogging();

		

		try {
			treeTC(this.testcase,this.recursive);
		} catch (IOException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}

		LogUtils.enableLogging();

	}
	
	public void treeTC(String testcase, boolean recursive) throws IOException {

		TestCasesHolder tch1 = configuration().getTchApi().testCasesHolderFromYaml();

		if (recursive) {
			System.out.println(testcase);
			if (testcase != null && !testcase.contentEquals("all")) {
				tch1.getTestCases().forEach(tc -> {
					if (testcase.contentEquals(tc.getTestCaseName())) {
						System.out.println(tc.getTestCaseName());
						tc.getTestSteps().forEach(ts -> {
							System.out.println("	\\_" + ts.getTestStepName());
							ts.getConnectorRefs().forEach(cr -> {
								System.out.println("		\\__" + cr.getConRefId());
							});
						});
					}
				});
			} else {
				tch1.getTestCases().forEach(tc -> {
					System.out.println(tc.getTestCaseName());
					tc.getTestSteps().forEach(ts -> {
						System.out.println("	\\_" + ts.getTestStepName());
						ts.getConnectorRefs().forEach(cr -> {
							System.out.println("		\\__" + cr.getConRefId());
						});
					});
				});
			}
		} else {
			if (testcase.contentEquals("all")) {
				tch1.getTestCases().forEach(tc -> {
					System.out.println(tc.getTestCaseName());
				});
			}
		}
	}

}
