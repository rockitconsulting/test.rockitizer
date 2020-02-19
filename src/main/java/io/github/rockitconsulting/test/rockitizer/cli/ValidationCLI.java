package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;
import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;

import java.io.File;
import java.io.IOException;

public class ValidationCLI {

	/**
	 * deleting all .gitignore payloads created for git compatibility
	 * 
	 * @throws IOException
	 */
	public void cleanGitIgnore()  {
		ValidationUtils.cleanGitIgnore();
	}

	/**
	 * create .gitignore payloads for getter connectors in order to enable the
	 * git commit for proprietary framework folder structure
	 * 
	 * @throws IOException
	 */
	public void addGitIgnore()  {
		ValidationUtils.fixGitEmptyFoldersProblem();
	}

	/**
	 * Validate configuration is in sync: testcases vs resources 
	 * @throws IOException
	 */
	public void validateConnectorRefExists()  {
		ValidationUtils.fixGitEmptyFoldersProblem();
	}
		
	public void validateStructure() throws IOException {

		Iterable<File> testCases = FileUtils.listFolders(new File(configuration().getFullPath()));
	}

}
