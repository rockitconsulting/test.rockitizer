package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import java.io.File;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "delete-env", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "cli delete-env [<env>]", description = "Delete environment")

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

public class RockitizerDeleteConfig implements Runnable {

	@Parameters(index = "0", arity = "0..1", description = ": env = dev => deletion of resources-dev.yaml")
	public String env;

	private String path;

	@Override
	public void run() {

		if (env != null) {

			path = configuration().getFullPath() + "resources-" + env + ".yaml";

		} else {

			path = configuration().getFullPath() + "resources.yaml";

		}

		deleteConfig(path);

	}

	public void deleteConfig(String path) {

		if (new File(path).delete()) {
			System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Configuration deleted successfully: |@" + path));
		} else {
			System.err.println(CommandLine.Help.Ansi.AUTO.string("@|bold,red Failed to delete the file: |@" + path));
		}

	}

}
