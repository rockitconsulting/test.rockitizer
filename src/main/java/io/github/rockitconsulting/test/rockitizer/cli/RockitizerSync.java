package io.github.rockitconsulting.test.rockitizer.cli;

import java.io.IOException;
import java.util.List;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;
import picocli.CommandLine;

@CommandLine.Command(
		name = "sync",
		sortOptions = false,
		headerHeading = "@|bold,underline Usage:|@%n%n",
		synopsisHeading = "%n",
		descriptionHeading = "%n@|bold,underline Description:|@%n%n",
		parameterListHeading = "%n@|bold,underline Parameters:|@%n",
		optionListHeading = "%n@|bold,underline Options:|@%n",
		header = "cli sync",
		description = "Syncronizing test folder structure with resources-<env>.yaml and testcases.yaml"
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

public class RockitizerSync implements Runnable {

	//@Parameters(index = "0", arity = "0..1", description = ": env = dev => sync filesystem with environment dependent resources-dev.yaml")
	//String env;

	@Override
	public void run() {
		/*
		if (env != null) {
			System.setProperty(Constants.ENV_KEY, env);
		}
		*/
		try {
			List<String> messages = ValidationUtils.syncConfig();
			System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Successfully: |@" + "testcases.yaml has been sucessfully generated from filesystem "));

			if (messages.isEmpty()) {
				//System.out.println(((env != null) ? "resources-" + env + ".yaml" : "resources.yaml") + " was up-to-date, no additions.");
				System.out.println("resources.yaml resources.yaml was up-to-date, no additions.");
			} else {
				//System.out.println(((env != null) ? "resources-" + env + ".yaml" : "resources.yaml") + " updated with following configuration:");
				System.out.println("resources.yaml updated with following configuration:");
				messages.forEach(m -> System.out.println(m));
			}
		} catch (IOException e) {
			System.err.println(CommandLine.Help.Ansi.AUTO.string("@|bold,red Sync failed: |@" + e));
		}

	}

}
