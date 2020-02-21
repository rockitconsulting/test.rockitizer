package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import org.apache.log4j.Logger;

import picocli.CommandLine;

@CommandLine.Command(name = "list", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "Record changes to the repository.", description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")
public class RockitizerList implements Runnable {

	public static Logger log = Logger.getLogger(RockitizerList.class.getName());

	final String rootPath = ConfigUtils.getAbsoluteRootPath();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";

	@CommandLine.Option(names = { "-tc", "--testcase" }, paramLabel = "<TestcaseName>", description = "List Testcase")
	String testcase;

	@CommandLine.Option(names = { "-config", "--configuration" }, paramLabel = "<ConfigurationName>", description = "List config.properties")
	String configuration;

	@CommandLine.Option(names = { "-env", "--environment" }, paramLabel = "<Environment>", description = "List environment")
	String environment;

	@Override
	public void run() {

		if (this.testcase == null && this.configuration == null) {

			printAllTests();

		} else {

			if (this.testcase != null) {

				printTestCase(this.testcase);

			} else if (this.configuration != null) {

			}

		}

	}

	public void printAllTests() {

	}

	public void printTestCase(String testcase) {

	}

}
