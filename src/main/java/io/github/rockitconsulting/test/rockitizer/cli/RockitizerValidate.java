package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationHolder;
import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;

import java.io.IOException;

import picocli.CommandLine;

import com.google.common.base.Joiner;

@CommandLine.Command(name = "validate", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header = "Record changes to the repository.", description = "Stores the current contents of the index in a new commit "
		+ "along with a log message from the user describing the changes.")
public class RockitizerValidate implements Runnable {


//	@Parameters(index = "0", arity = "1", description = "TestcaseName.....")
//	String testcase;
//
//	@Parameters(index = "1", arity = "1", description = "TeststepName.....")
//	String teststep;

	@Override
	public void run() {

			System.out.println("adding .gitignore to enforce empty folders commit");
			ValidationUtils.fixGitEmptyFoldersProblem();
			System.out.println("added .gitignore. re-run validation");
			ValidationUtils.validateConnectorRefExists();
			ValidationUtils.validateResources();
	
			try {
				ValidationUtils.validateTestCasesAndFileSystemInSync();
				ValidationUtils.validateNotAllowedEmptyStructures();

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(ValidationHolder.validationHolder().size()>0) {
				System.out.println("Result: Not valid with validation messages:");
				ValidationHolder.validationHolder().forEach((k, v) -> System.out.println( k + " - " + Joiner.on(";").join(v)));
			} else {
				System.out.println("Validation successfull. Please commit the changes if nescessary.");
				
			}
			
			
			
	
	
		
		
		
	}

}
