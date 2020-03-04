package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "delete-test",
	sortOptions = false,
	headerHeading = "@|bold,underline Benutzung:|@%n%n",
	synopsisHeading = "%n",
	descriptionHeading = "%n@|bold,underline Description:|@%n%n",
	parameterListHeading = "%n@|bold,underline Parameters:|@%n",
	optionListHeading = "%n@|bold,underline Options:|@%n",
	header = "cli delete-test <testcase> [<teststep>] [<connector>]",
	description = "Deletes the test objects in the file system " +
			"along with a log message from the user describing the changes.")

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

public class RockitizerDeleteTest implements Runnable {

	@Parameters(index = "0", arity = "1", description = " testCase name ")
	String testcase;

	@Parameters(index = "1", arity = "0..1", description = "testStep name ")
	String teststep;

	@Parameters(index = "2", arity = "0..1", description = " Connector : HTTP.<ID>, SCPPUT.<ID>, MQGET.<ID>, MQPUT.<ID>, FILEPUT.<ID>, FILEDEL.<ID>, FILEGET.<ID>, DBGET.<ID>, DBPUT.<ID>")
	String connector;
	
	@Override
	public void run() {
		
		
			String path;
		
			if (teststep != null && connector != null) {
				path= configuration().getFullPath() + this.testcase + File.separator + this.teststep + File.separator + this.connector;
				FileUtils.deleteDirectory( new File (path) );
			} else if (teststep != null) {
				path= configuration().getFullPath() + this.testcase + File.separator + this.teststep;
				FileUtils.deleteDirectory( new File (path) );
				
			} else {
				path = configuration().getFullPath() + this.testcase;
				FileUtils.deleteDirectory( new File (path) );
				new File(ConfigUtils.getAbsolutePathToJava() + testcase + ".java").delete();//NOSONAR
				System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Successfully Deleted: |@" + ConfigUtils.getAbsolutePathToJava() + testcase + ".java"));
			}
			
		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Successfully Deleted: |@" + path));
			
	}
}
