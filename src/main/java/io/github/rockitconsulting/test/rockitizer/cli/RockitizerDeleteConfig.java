package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "delete-config",
sortOptions = false,
headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n",
descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "Delete Configuration",
description = "Stores the current contents of the index in a new commit " +
		"along with a log message from the user describing the changes.")

public class RockitizerDeleteConfig implements Runnable {

	@Parameters(index = "0", arity = "1", description = ": [resources | testcases]")
    public String configuration;
	
	@Parameters(index = "1", arity = "0..1", description = ": [%env%] - e.g. env = dev => <configuration>-dev.yaml will be deleted")
    public String environment;
	
	public String path;
	
	@Override
	public void run() {
		
		LogUtils.disableLogging();

		CommonCLI cli = new CommonCLI();
		
		if (environment != null){
			
			path = configuration().getFullPath() + configuration + "." + environment + ".yaml";
			
		}else{

			path = configuration().getFullPath() + configuration + ".yaml";

		}
		
		cli.deleteConfig(path);
		
		LogUtils.enableLogging();
		
	}

}
