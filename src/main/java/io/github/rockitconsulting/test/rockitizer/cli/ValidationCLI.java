package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;

public class ValidationCLI {

	/**
	 * deleting all .gitignore payloads created for git compatibility
	 * 
	 */
	public void cleanGitIgnore()  {
		ValidationUtils.cleanGitIgnore();
	}

	/**
	 * create .gitignore payloads for getter connectors in order to enable the
	 * git commit for proprietary framework folder structure
	 * 
	 */
	public void addGitIgnore()  {
		ValidationUtils.fixGitEmptyFoldersProblem();
	}

	/**
	 * Validate configuration is in sync: testcases vs resources 
	 * 
	 */
	public void validateConnectorRefExists()  {
		ValidationUtils.validateConnectorRefExists();
	}
		
	/**
	 * Validate resources configuration 
	 * 
	 */
	public void validateResources()  {
		ValidationUtils.validateResources();
	}

}
