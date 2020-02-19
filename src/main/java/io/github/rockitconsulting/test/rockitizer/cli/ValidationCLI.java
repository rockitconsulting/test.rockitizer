package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.common.RuntimeContext;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;
import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;

import java.io.File;
import java.io.IOException;

public class ValidationCLI extends RuntimeContext {

	/**
	 * deleting all .gitignore payloads created for git compatibility
	 * 
	 * @throws IOException
	 */
	public void cleanGitIgnore() throws IOException {
		ValidationUtils.cleanGitIgnore(new File(getFullPath()));
	}

	/**
	 * create .gitignore payloads for getter connectors in order to enable the
	 * git commit for proprietary framework folder structure
	 * 
	 * @throws IOException
	 */
	public void addGitIgnore() throws IOException {
		ValidationUtils.fixGitEmptyFoldersProblem(new File(getFullPath()));
	}

	public void validateStructure() throws IOException {

		Iterable<File> testCases = FileUtils.listFolders(new File(getFullPath()));
	}

}
