package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

/**
 * Base CLI class with context setup for JUnit Support
 *
 *
 */
public class RuntimeContext {


	private String absolutePath = ConfigUtils.getAbsoluteRootPath();
	private String relativePath = "";

	String getFullPath() {
		return absolutePath + relativePath;
	}


	String getAbsolutePath() {
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
