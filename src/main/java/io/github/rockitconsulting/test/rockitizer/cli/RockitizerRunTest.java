package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.Configuration.RunModeTypes;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

@CommandLine.Command(name = "run", sortOptions = false, 
headerHeading = "@|bold,underline Usage:|@%n%n", synopsisHeading = "%n", 
descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", 
optionListHeading = "%n@|bold,underline Options:|@%n", header = "cli run <testName | all> [<record | replay>] [<env>]", 
description = "Runs JUnit test or complete test suite in console, optionally requiring mode and environment.%n"
			+ "Supported modes are:%n"
			+ "  - RECORD (executing of master test/suite under src/test/resources/, keeping results under src/test/resources/<testcase>/output);%n"
			+ "  - REPLAY (copying master test to target, executing it and performing assertions);%n"
			+ "  - ASSERT (comparing the RECORD and REPLAY outputs). Best practice to use for tuning the assertions in order to get the best coverage.%n",
footer = "%n%nNotice: For run, you can use following options:  CLI, IDE/Standalone Maven Starter, IDE JUnit Starter."
)

/**
*  Test.Rockitizer - API regression testing framework 
*   Copyright (C) 2020  rockit.consulting GmbH
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see http://www.gnu.org/licenses/.
*
*/

public class RockitizerRunTest extends JUnitCore implements Runnable {


	@Parameters(index = "0", arity = "1", description = ": MyJUnitTest or all ")
	String testname;
	@Parameters(index = "1", arity = "0..1", description = ": ${COMPLETION-CANDIDATES}")
	RunModeTypes mode;
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
				addListener(new TextListener(System.out));
				
				if (testname.equalsIgnoreCase("all")) {
					FileUtils.listFiles(new File(ConfigUtils.getAbsolutePathToJava())).forEach(test -> {
						try {

							results.add(run(Class.forName(test.getName().replace(".java", ""))));
							System.out.println();
						} catch (Exception e) {
							registerJunitError(results, e);
						}

					});
				} else {
					results.add(run(Class.forName(testname)));
				}

			}

		} catch (Throwable thr) {
			registerJunitError(results, thr);
		}

		int sumErrors = results.stream().mapToInt(Result::getFailureCount).sum();
		int sumRuns = results.stream().mapToInt(Result::getRunCount).sum();

		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold Result: |@") + (sumErrors > 0 ? CommandLine.Help.Ansi.AUTO.string("@|bold,red NOK|@") : CommandLine.Help.Ansi.AUTO.string("@|bold,green OK|@")));
		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold Total runs |@" + sumRuns));
		if (sumErrors > 0) {
			System.err.println(CommandLine.Help.Ansi.AUTO.string("@|bold,red Total errors |@" + sumErrors));
		}

	}

	private void registerJunitError(List<Result> results, Throwable thr) {
		System.err.println(CommandLine.Help.Ansi.AUTO.string("@|bold,red Error: |@" + thr.getClass().getSimpleName() + " " + thr.getMessage()));
		Result result = new Result();
		result.getFailures().add(new Failure(null, thr));
		results.add(result);
	}

}
