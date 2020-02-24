package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import java.io.File;
import java.io.IOException;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

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
	
	@Override
	public void run() {
		
		if(environment != null ) {
			System.out.println( " setting  environment " + environment );
			System.setProperty(Constants.ENV_KEY, environment ) ;
		}
		
		String resourcesFile = configuration().getFullPath() + configuration().getRhApi().getResourcesFileName(); 

		if ( !new File(resourcesFile ).exists() )  {
				System.out.println( " creating new " + resourcesFile + " for environemnt " + environment);
			} else {
				System.out.println( " overwriting " + resourcesFile + " for environemnt " + environment);
			}
				
		try {
			configuration().getRhApi().resourcesHolderToYaml(null);			
			
		} catch (IOException e) {
			System.out.println( " error occured:  " + e.getMessage());
			e.printStackTrace();
		}

		
		String testcasesFile = configuration().getFullPath() + configuration().getTchApi().getTestcasesFileName(); 

		if ( !new File(testcasesFile ).exists() )  {
				System.out.println( " creating new " + testcasesFile + " for environemnt " + environment);
			} else {
				System.out.println( " overwriting " + testcasesFile + " for environemnt " + environment);
			}
				
		try {
			configuration().getRhApi().resourcesHolderToYaml(null);			
			
		} catch (IOException e) {
			System.out.println( " error occured:  " + e.getMessage());
			e.printStackTrace();
		}

	}

}
