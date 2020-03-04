package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestCase;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.IOException;

import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "list-testcases", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "cli list-testcases [-v[=<yaml|tree>]]", description = "Listing testcases from testcases.yaml")
public class RockitizerListTestCases implements Runnable {

	enum ViewType {
		yaml, tree
	};


	@Option(defaultValue = "tree", names = { "-v", "--view" }, arity = "0..1", description = "type of view: ${COMPLETION-CANDIDATES}")
	ViewType view = ViewType.tree;

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

		tch1.getTestCases().forEach(tc -> {
			printTC(tc);
		});
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
