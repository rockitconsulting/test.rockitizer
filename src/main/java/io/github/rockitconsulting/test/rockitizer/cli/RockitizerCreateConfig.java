package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

@CommandLine.Command(name = "create-config",
sortOptions = false,
headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n",
descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "Record changes to the repository.",
description = "Stores the current contents of the index in a new commit " +
    	"along with a log message from the user describing the changes.")

public class RockitizerCreateConfig implements Runnable {

	@Parameters(index = "0", description = "Environment.....")
    String environment;
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		if(environment != null ) {
			System.out.println( " setting  environment " + environment + " and forcing config generation from filesystem");
			System.setProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY, "CLI");
			System.setProperty(Constants.ENV_KEY, environment ) ;
		}
		
		String resourcesFile = configuration().getFullPath() + configuration().getRhApi().getResourcesFileName(); 
		String testcasesFile = configuration().getFullPath() + configuration().getTchApi().getTestcasesFileName(); 
		
		System.out.println( " Result: ");
		System.out.println( " generated new " + testcasesFile + " for environemnt " + environment);
		System.out.println( " generated new " + resourcesFile + " for environemnt " + environment);
				
	
		//remove fs init mode
		System.clearProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY);

	}

}
