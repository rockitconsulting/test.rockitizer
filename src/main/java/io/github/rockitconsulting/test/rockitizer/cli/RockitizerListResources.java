package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;
import io.github.rockitconsulting.test.rockitizer.validation.Validatable;

import java.io.IOException;
import java.util.List;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import com.google.common.base.Strings;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

@CommandLine.Command(name = "list-resources", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "cli list-resources [-v[=<yaml|tree>]] [<env>]", description = "listing of resources-<env>.yaml")
public class RockitizerListResources extends CommonCLI implements Runnable {

	enum ViewType {
		yaml, tree
	};

	@Parameters(index = "0", arity = "0..1", description = " current environment")
	String env;

	@Option(defaultValue = "yaml", names = { "-v", "--view" }, arity = "0..1", description = "type of view: ${COMPLETION-CANDIDATES}")
	ViewType view = ViewType.yaml;

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
			System.out.println("Error: " + e);
		}


	}

	private void treeRC() throws IOException {

		ResourcesHolder rhyaml = configuration().getRhApi().resourcesHolderFromYaml();

		printPrintableResource(rhyaml.getMqConnectors(), "mqConnectors");
		printPrintableResource(rhyaml.getFileConnectors(), "fileConnectors");
		printPrintableResource(rhyaml.getDbConnectors(), "dbConnectors");
		printPrintableResource(rhyaml.getScpConnectors(), "scpConnectors");
		printPrintableResource(rhyaml.getMqDataSources(), "mqDataSourcres");
		printPrintableResource(rhyaml.getDbDataSources(), "dbDataSourcres");
		printPrintableResource(rhyaml.getKeyStores(), "KeyStores");

	}

	private void printPrintableResource(List<? extends Validatable> resources, String root) {
		String rootPrefix = Strings.padStart("\\_", 15, ' ');
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

}
