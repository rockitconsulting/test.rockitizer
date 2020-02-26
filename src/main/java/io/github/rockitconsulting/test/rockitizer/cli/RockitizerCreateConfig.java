package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

@CommandLine.Command(name = "create-config", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "(Working) Create Configuration", description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")
public class RockitizerCreateConfig implements Runnable {

	@Parameters(index = "0", description = ": [%env%] - e.g. env = dev => <configuration>-dev.yaml will be created")
	String environment;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		LogUtils.disableLogging();

		try {
			if (environment != null) {
				System.out.println(" setting  environment " + environment + " and forcing config generation from filesystem");
				System.setProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY, "CLI");
				System.setProperty(Constants.ENV_KEY, environment);
			}

			String resourcesFile = configuration().getFullPath() + configuration().getRhApi().getResourcesFileName();
			String testcasesFile = configuration().getFullPath() + configuration().getTchApi().getTestcasesFileName();

			System.out.println(" Result: ");
			System.out.println(" generated new " + testcasesFile + " for environemnt " + environment);
			System.out.println(" generated new " + resourcesFile + " for environemnt " + environment);

		} catch (Throwable thr) {
			System.err.println(" Error: " + thr.getMessage());
		}

		System.clearProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY);
		LogUtils.enableLogging();
	}

}
