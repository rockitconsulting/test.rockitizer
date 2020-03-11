package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

@CommandLine.Command(name = "create-env", sortOptions = false, headerHeading = "@|bold,underline Usage:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "cli create-env [<env>]", description = " Generates environment dependent configuration from test structure under /src/test/resources ")
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
public class RockitizerCreateConfig implements Runnable {

	@Parameters(index = "0", arity = "0..1", description = ": env = dev => generation of resources-dev.yaml and tastcases.yaml")
	String env;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		if (env != null) {
			System.out.println("...setting  environment " + env);
			System.setProperty(Constants.ENV_KEY, env);
		}
		
		System.out.println("...forcing config generation from filesystem");
		System.setProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY, "CLI");

		try {

			String resourcesFile = configuration().getFullPath() + configuration().getRhApi().getResourcesFileName();
			String testcasesFile = configuration().getFullPath() + configuration().getTchApi().getTestcasesFileName();

			System.out.println(" Result: ");
			System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Success:|@" + " generated new " + testcasesFile + " for environemnt " + env));
			System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Success:|@" + " generated new " + resourcesFile + " for environemnt " + env));

		} catch (Throwable thr) {
			System.err.println(CommandLine.Help.Ansi.AUTO.string("@|bold,red Error: |@" + thr.getMessage()));
		}

		System.clearProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY);
	}

}
