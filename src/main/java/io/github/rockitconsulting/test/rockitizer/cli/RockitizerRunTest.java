package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

@CommandLine.Command(name = "run", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "cli run <testName | all> [<record | replay>] [<env>]", description = "runs junit or complete test suite in console mode")
public class RockitizerRunTest extends JUnitCore implements Runnable {

	enum Mode {
		record, replay
	};

	@Parameters(index = "0", arity = "1", description = ": MyJUnitTest or all ")
	String testname;
	@Parameters(index = "1", arity = "0..1", description = ": ${COMPLETION-CANDIDATES}")
	Mode mode;
	@Parameters(index = "2", arity = "0..1", description = ": e.g. env = dev")
	String env;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		List<Result> results = new ArrayList<>();

		try {

			if (mode != null) {
				System.setProperty(Constants.MODE_KEY, mode.name());
			}
			if (env != null) {
				System.setProperty(Constants.ENV_KEY, env);
			}

			if (testname != null) {
				if (testname.equalsIgnoreCase("all")) {
					FileUtils.listFiles(new File(ConfigUtils.getAbsolutePathToJava())).forEach(test -> {
						try {

							results.add(run(Class.forName(test.getName().replace(".java", ""))));
							System.out.println();
						} catch (Exception e) {
							System.err.println(" Error: " + e.getMessage());

						}

					});
				} else {
					results.add(run(Class.forName(testname)));
				}

			}

		} catch (Throwable thr) {
			System.err.println(" Error: " + thr.getMessage());
		}

		int sumErrors = results.stream().mapToInt(Result::getFailureCount).sum();
		int sumRuns = results.stream().mapToInt(Result::getRunCount).sum();

		System.out.println("Result: " + (sumErrors > 0 ? "NOK" : "OK"));
		System.out.println("Total runs " + sumRuns);
		if (sumErrors > 0) {
			System.err.println("Total errors " + sumErrors);
		}

	}

}
