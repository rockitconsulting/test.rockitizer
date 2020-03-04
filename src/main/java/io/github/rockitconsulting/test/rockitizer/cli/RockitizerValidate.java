package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationHolder;
import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;

import java.io.IOException;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import com.google.common.base.Joiner;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

@CommandLine.Command(name = "validate", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", 
descriptionHeading = "%n@|bold,underline Description:|@%n%n", parameterListHeading = "%n@|bold,underline Parameters:|@%n", 
optionListHeading = "%n@|bold,underline Options:|@%n", header = "cli validate [-gitfix[=<true|false>]] [<env>]", description = " Complete validation for the testsuite.")
public class RockitizerValidate implements Runnable {

	@Option(defaultValue = "false", showDefaultValue=CommandLine.Help.Visibility.ALWAYS, names = { "-gitfix", "--addgitignore" }, arity = "0..1", description = "add .gitignore to all GET connector folders to allow get commit.")
	boolean gitfix=false;

	@Parameters(index = "0", arity = "0..1", description = ": env = dev => validation with environment dependent resources-dev.yaml")
	String env;

	
	@Override
	public void run() {

		if (env != null) {
			System.setProperty(Constants.ENV_KEY, env);
		}

		
		if (gitfix) {
			System.out.println("adding .gitignore to enforce empty folders commit");
			ValidationUtils.fixGitEmptyFoldersProblem();
			System.out.println("added .gitignore. re-run validation and commit if valid");
		}

		ValidationUtils.validateConnectorRefExists();
		ValidationUtils.validateResources();

		try {
			ValidationUtils.validateTestCasesAndFileSystemInSync();
			ValidationUtils.validateNotAllowedEmptyStructures();
			ValidationUtils.validateSyncJavaAndTestCases();

			if (ValidationHolder.validationHolder().size() > 0) {
				System.err.println("Result: Not valid with validation messages:");
				ValidationHolder.validationHolder().forEach((k, v) -> System.err.println(k + " - " + Joiner.on(";").join(v)));
			} else {
				System.out.println("Validation successfull. Please commit the changes if nescessary.");

			}

		} catch (IOException e) {
			System.err.println("Validation error:" + e);
		}
	}

}
