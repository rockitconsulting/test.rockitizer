package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "delete-test",
	sortOptions = false,
	headerHeading = "@|bold,underline Benutzung:|@%n%n",
	synopsisHeading = "%n",
	descriptionHeading = "%n@|bold,underline Description:|@%n%n",
	parameterListHeading = "%n@|bold,underline Parameters:|@%n",
	optionListHeading = "%n@|bold,underline Options:|@%n",
	header = "cli delete-test <testcase> [<teststep>] [<connector>]",
	description = "Stores the current contents of the index in a new commit " +
			"along with a log message from the user describing the changes.")

public class RockitizerDeleteTest implements Runnable {

	@Parameters(index = "0", arity = "1", description = " testCase name ")
	String testcase;

	@Parameters(index = "1", arity = "0..1", description = "testStep name ")
	String teststep;

	@Parameters(index = "2", arity = "0..1", description = " Connector : HTTP.<ID>, SCPPUT.<ID>, MQGET.<ID>, MQPUT.<ID>, FILEPUT.<ID>, FILEDEL.<ID>, FILEGET.<ID>, DBGET.<ID>, DBPUT.<ID>")
	String connector;
	
	@Override
	public void run() {
		
		
			String path;
		
			if (teststep != null && connector != null) {
				path= configuration().getFullPath() + this.testcase + File.separator + this.teststep + File.separator + this.connector;
				FileUtils.deleteDirectory( new File (path) );
			} else if (teststep != null) {
				path= configuration().getFullPath() + this.testcase + File.separator + this.teststep;
				FileUtils.deleteDirectory( new File (path) );
				
			} else {
				path = configuration().getFullPath() + this.testcase;
				FileUtils.deleteDirectory( new File (path) );
				new File(ConfigUtils.getAbsolutePathToJava() + testcase + ".java").delete();//NOSONAR
				
			}
			
		System.out.println("Deleted: " + path);
			
	}
}
