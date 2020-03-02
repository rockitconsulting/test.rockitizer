package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestCase;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.IOException;

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

	enum ViewType {
		yaml, tree
	};

	
	@Parameters(index = "0", arity = "1", description = "test case name or all")
	String testcase;
	
	@Option( defaultValue="true", names = { "-r", "--recursive" }, arity = "0..1", description = "recursive true|false")
	boolean recursive=true;

	@Option(defaultValue = "yaml", names = { "-v", "--view" }, arity = "0..1", description = "type of view: ${COMPLETION-CANDIDATES}")
	ViewType view = ViewType.yaml;

	
	@Override
	public void run() {


		

		try {
			if (view == ViewType.tree) {
				treeTC();
			} else {
				System.out.println(FileUtils.readFile(configuration().getFullPath() + configuration().getTchApi().getTestcasesFileName()));
			}
			
			
			
		} catch (IOException e) {
			System.err.println("Error: " + e);
		}
	}
	
	public void treeTC() throws IOException {

		TestCasesHolder tch1 = configuration().getTchApi().testCasesHolderFromYaml();

		if (recursive) {
			System.out.println(testcase);
			if (testcase != null && !testcase.contentEquals("all")) {
				tch1.getTestCases().forEach(tc -> {
					if (testcase.contentEquals(tc.getTestCaseName())) {
						printTC(tc);
					}
				});
			} else {
				tch1.getTestCases().forEach(tc -> {
					printTC(tc);
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

	private void printTC(TestCase tc) {
		System.out.println(tc.getTestCaseName());
		tc.getTestSteps().forEach(ts -> {
			System.out.println("	\\_" + ts.getTestStepName());
			ts.getConnectorRefs().forEach(cr -> {
				System.out.println("		\\__" + cr.getConRefId());
			});
		});
	}

}
