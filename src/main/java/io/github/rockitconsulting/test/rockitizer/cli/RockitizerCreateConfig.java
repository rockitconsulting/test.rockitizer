package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

@CommandLine.Command(name = "create-env", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", 
descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", 
header = "cli create-env [<env>]", description = " Generates environment dependent configuration from test structure under /src/test/resources ")
public class RockitizerCreateConfig implements Runnable {

	@Parameters(index = "0",arity = "0..1", description = ": env = dev => generation of resources-dev.yaml and tastcases.yaml")
	String env;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {


		try {
			if (env != null) {
				System.out.println(" setting  environment " + env + " and forcing config generation from filesystem");
				System.setProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY, "CLI");
				System.setProperty(Constants.ENV_KEY, env);
			}

			String resourcesFile = configuration().getFullPath() + configuration().getRhApi().getResourcesFileName();
			String testcasesFile = configuration().getFullPath() + configuration().getTchApi().getTestcasesFileName();

			System.out.println(" Result: ");
			System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Success:|@" + " generated new " + testcasesFile + " for environemnt " + env));
			System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Success:|@" + " generated new " + resourcesFile + " for environemnt " + env));

		} catch (Throwable thr) {
			System.err.println(CommandLine.Help.Ansi.AUTO.string("@|bold,red Error: |@" + thr.getMessage()));
		}

		System.clearProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY);
		LogUtils.enableLogging();
	}

}
