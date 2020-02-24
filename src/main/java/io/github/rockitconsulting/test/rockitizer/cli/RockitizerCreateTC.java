package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import java.io.File;
import java.io.IOException;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "create-testcase",
sortOptions = false,
headerHeading = "@|bold,underline Benutzung:|@%n%n",
synopsisHeading = "%n",
descriptionHeading = "%n@|bold,underline Description:|@%n%n",
parameterListHeading = "%n@|bold,underline Parameters:|@%n",
optionListHeading = "%n@|bold,underline Options:|@%n",
header = "Record changes to the repository.",
description = "Stores the current contents of the index in a new commit " +
    	"along with a log message from the user describing the changes.")


public class RockitizerCreateTC implements Runnable {
	
	@Parameters(index = "0", arity = "1", description = "TestcaseName.....")
    String testcase;

	@Override
	public void run() {
		
		// TODO Check if testcase exist 	done
		// TODO Create testcase 			done
		// TODO Create junit test 
		
		CommonCLI cli = new CommonCLI();
		TemplateCLI tmp = new TemplateCLI();
		
		File file = new File(configuration().getFullPath().replaceFirst("/resources/", "/java/")+ this.testcase + ".java");
		  
		//Create the file
		try {
			if (file.createNewFile())
			{
				tmp.createJunitClass(this.testcase, configuration().getFullPath().replaceFirst("/resources/", "/java/")+ this.testcase + ".java");
			    System.out.println("File is created!");
			} else {
			    System.out.println("File already exists.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cli.create(configuration().getFullPath() + this.testcase);
		
	}

}
