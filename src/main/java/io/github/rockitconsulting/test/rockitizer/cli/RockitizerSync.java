package io.github.rockitconsulting.test.rockitizer.cli;

import java.io.IOException;
import java.util.List;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "sync", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "cli sync [<env>]", description = "syncronizing test folder structure with resources-<env>.yaml and testcases.yaml")
public class RockitizerSync implements Runnable {

	@Parameters(index = "0", arity = "0..1", description = ": env = dev => sync filesystem with environment dependent resources-dev.yaml")
	String env;

	@Override
	public void run() {

		if (env != null) {
			System.setProperty(Constants.ENV_KEY, env);
		}

		try {
			List<String> messages = ValidationUtils.syncConfig();
			System.out.println("testcases.yaml has been sucessfully generated from filesystem ");

			if (messages.isEmpty()) {
				System.out.println(((env != null) ? "resources-" + env + ".yaml" : "resources.yaml") + " was up-to-date, no additions.");

			} else {
				System.out.println(((env != null) ? "resources-" + env + ".yaml" : "resources.yaml") + " updated with following configuration:");
				messages.forEach(m -> System.out.println(m));
			}
		} catch (IOException e) {
			System.err.println("Sync failed:" + e);
		}

	}

}
