package io.github.rockitconsulting.test.rockitizer.cli;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

/**
 * Base CLI class with context setup for JUnit Support
 *
 *
 */
public class RuntimeContextCLI {

	static final String OUTPUT = Constants.OUTPUT_FOLDER;


	private String absolutePath = ConfigUtils.getAbsoluteRootPath();
	private String relativePath = "";

	public String getFullPath() {
		return absolutePath + relativePath;
	}


	public String getAbsolutePath() {
		return absolutePath;
	}

	void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	String getRelativePath() {
		return relativePath;
	}

	void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}


}
