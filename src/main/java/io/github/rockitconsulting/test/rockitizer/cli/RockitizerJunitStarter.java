package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;

import java.io.File;

import org.junit.runner.JUnitCore;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

@CommandLine.Command(name = "test", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "(Working) Create Configuration", description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")
public class RockitizerJunitStarter extends JUnitCore implements Runnable {

	@Parameters(index = "0", arity = "1", description = ": [%%testname%%] - e.g. MyJUnitTest or all")
	String testname;
	@Parameters(index = "1", arity = "0..1", description = ": [%%mode%%] - e.g. record or replay")
	String mode;
	@Parameters(index = "2", arity = "0..1", description = ": [%%env%%] - e.g. env = dev")
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
			if (testname != null) {
				if (testname.equalsIgnoreCase("all")) {
					FileUtils.listFiles(new File(ConfigUtils.getAbsolutePathToJava())).forEach(test -> {
						try {

							run(Class.forName(test.getName().replace(".java", "")));
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
						} catch (Exception e) {
							System.err.println(" Error: " + e.getMessage());

						}

					});
				} else {
					run(Class.forName(testname));
				}

			}

			if (mode != null) {
				System.setProperty(Constants.MODE_KEY, mode);
			}
			if (environment != null) {
				System.setProperty(Constants.ENV_KEY, environment);
			}

		} catch (Throwable thr) {
			System.err.println(" Error: " + thr.getMessage());
		}

		LogUtils.enableLogging();

	}

}
