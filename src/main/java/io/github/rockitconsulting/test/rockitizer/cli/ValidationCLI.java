package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class ValidationCLI extends RuntimeContextCLI { 
	
	public void validateStructure() throws IOException {

		Iterable<File> testCases = FileUtils.listFolders(new File(getFullPath()));
	}
	
}
