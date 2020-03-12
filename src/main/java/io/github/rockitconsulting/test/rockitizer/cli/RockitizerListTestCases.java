package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestCase;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.IOException;

import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "list-testcases", 
sortOptions = false, 
headerHeading = "@|bold,underline Usage:|@%n%n", 
synopsisHeading = "%n", 
descriptionHeading = "%n@|bold,underline Description:|@%n%n", 
parameterListHeading = "%n@|bold,underline Parameters:|@%n", 
optionListHeading = "%n@|bold,underline Options:|@%n", 
header = "cli list-testcases [-v[=<yaml|tree>]]", 
description = "Listing testcases from testcases.yaml. View type by default: tree.%n%n"
		+ "             E.g. tree view:                             E.g. yaml view:%n"
		+ " FILEinFILEOutTest                               - testCaseName: FILEinFILEOutTest%n"
		+ "       \\_0BEFORE                                   testSteps:%n"
		+ "              \\__FILEDEL.IN.FILE2FILE              - testStepName: 0BEFORE%n"
		+ "              \\__FILEDEL.OUT.FILE2FILE               connectorRefs:%n"
		+ "       \\_a001FILEPutMessage                          - conRefId: FILEDEL.IN.FILE2FILE%n"
		+ "              \\__FILEPUT.IN.FILE2FILE                - conRefId: FILEDEL.OUT.FILE2FILE%n"
		+ "                      \\__testinput.xml             - testStepName: a001FILEPutMessage%n"
		+ "       \\_a002FILEGetMessage                          connectorRefs: %n"
		+ "              \\__FILEGET.OUT.FILE2FILE               - conRefId: FILEPUT.IN.FILE2FILE%n"
		+ "                                                       payloads:%n"
		+ "                                                       - fileName: testinput.xml%n"
		+ "                                                   - testStepName: a002FILEGetMessage%n"
		+ "                                                     connectorRefs:%n"
		+ "                                                     - conRefId: FILEGET.OUT.FILE2FILE"
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
			System.err.println(CommandLine.Help.Ansi.AUTO.string("@|bold,red Error: |@" + e));
		}
	}

	public void treeTC() throws IOException {

		TestCasesHolder tch1 = configuration().getTchApi().testCasesHolderFromYaml();

		tch1.getTestCases().forEach(tc -> {
			printTC(tc);
		});
	}

	private void printTC(TestCase tc) {
		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,yellow " + tc.getTestCaseName() + "|@"));
		tc.getTestSteps().forEach(ts -> {
			System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,yellow  	\\_|@" + ts.getTestStepName()));
			ts.getConnectorRefs().forEach(cr -> {
				System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,yellow 		\\__|@" + cr.getConRefId()));
				cr.getPayloads().forEach(pl -> {
					System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,yellow  	 		\\__|@" + pl.getFileName()));
				});
			});
		});
	}

}
