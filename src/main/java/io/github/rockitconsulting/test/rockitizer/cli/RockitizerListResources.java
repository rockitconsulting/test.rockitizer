package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;
import io.github.rockitconsulting.test.rockitizer.validation.Validatable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import com.google.common.base.Strings;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

@CommandLine.Command(name = "list-resources", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "cli list-resources [-v[=<yaml|tree>]] [<env>]", description = "Generates listing of resources-<env>.yaml View type by default: tree.%n%n"
		+ "        E.g. tree view:                           E.g. yaml view:%n"
		+ "mqConnectors                                     mqConnectors:%n"
		+ "             @|bold,yellow \\_|@id:MQGET.ERROR                    - id: MQGET.ERROR%n"
		+ "               type:MQGET                          type: MQGET%n"
		+ "               queue:MQ.ERROR                      queue: MQ.ERROR%n"
		+ "               dsRefId:defaultMQ                   dsRefId: defaultMQ%n")
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
public class RockitizerListResources extends CommonCLI implements Runnable {

	enum ViewType {
		yaml, tree
	};

	@Parameters(index = "0", arity = "0..1", description = " current env, e.g. devp ")
	String env;

	@Option(defaultValue = "tree", names = { "-v", "--view" }, arity = "0..1", description = "type of view: ${COMPLETION-CANDIDATES}")
	ViewType view = ViewType.tree;

	@Override
	public void run() {

		if (env != null) {
			System.setProperty(Constants.ENV_KEY, env);
		}

		try {
			if (view == ViewType.tree) {
				treeRC();
			} else {
				System.out.println(FileUtils.readFile(configuration().getFullPath() + configuration().getRhApi().getResourcesFileName()));
			}
		} catch (IOException e) {
			System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,red Error: |@" + e));
		}

	}

	private void treeRC() throws IOException {

		ResourcesHolder rhyaml = configuration().getRhApi().resourcesHolderFromYaml();

		printPrintableResource(rhyaml.getMqConnectors(), CommandLine.Help.Ansi.AUTO.string("@|bold,yellow mqConnectors |@"));
		printPrintableResource(rhyaml.getFileConnectors(), CommandLine.Help.Ansi.AUTO.string("@|bold,yellow fileConnectors |@"));
		printPrintableResource(rhyaml.getHttpConnectors(), CommandLine.Help.Ansi.AUTO.string("@|bold,yellow httpConnectors |@"));
		printPrintableResource(rhyaml.getDbConnectors(), CommandLine.Help.Ansi.AUTO.string("@|bold,yellow dbConnectors |@"));
		printPrintableResource(rhyaml.getScpConnectors(), CommandLine.Help.Ansi.AUTO.string("@|bold,yellow scpConnectors |@"));
		printPrintableResource(rhyaml.getMqDataSources(), CommandLine.Help.Ansi.AUTO.string("@|bold,yellow mqDataSourcres |@"));
		printPrintableResource(rhyaml.getDbDataSources(), CommandLine.Help.Ansi.AUTO.string("@|bold,yellow dbDataSourcres |@"));
		printPrintableResource(rhyaml.getKeyStores(), CommandLine.Help.Ansi.AUTO.string("@|bold,yellow KeyStores |@"));
		printPrintableResource(rhyaml.getPayloadReplacer(), CommandLine.Help.Ansi.AUTO.string("@|bold,yellow payloadReplacer |@"));

	}

	private void printPrintableResource(List<? extends Validatable> resources, String root) {
		String rootPx = CommandLine.Help.Ansi.AUTO.string("@|bold,yellow \\_|@");
		String rootPrefix = "             " + rootPx;
		String linePrefix = Strings.padStart("", 15, ' ');

		if (resources.size() > 0) {
			System.out.println(root);
		}

		resources.forEach(r -> {
			r.getFieldsAsOrderedMap().forEach((k, v) -> {
				if (k.equals("id")) {
					System.out.println(rootPrefix + k + ":" + v);
				} else {
					System.out.println(linePrefix + k + ":" + v);
				}

			});
		});
	}

	private void printPrintableResource(Map<String, String> payloadReplacerMap, String root) {
		String rootPx = CommandLine.Help.Ansi.AUTO.string("@|bold,yellow \\_|@");
		String rootPrefix = "             " + rootPx;
		String linePrefix = Strings.padStart("", 15, ' ');

		if (payloadReplacerMap.size() > 0) {
			System.out.println(root);
		}
		
		
		payloadReplacerMap.forEach((k, v) -> {
			if (k.equals(payloadReplacerMap.keySet().iterator().next())) {
				System.out.println(rootPrefix + k + ":" + v);
			} else {
				System.out.println(linePrefix + k + ":" + v);
			}
		});

	}

}
