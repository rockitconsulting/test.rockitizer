package io.github.rockitconsulting.test.rockitizer.common;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

/**
 * Base CLI class with context setup for JUnit Support
 *
 *
 */
public class RuntimeContext {


	private String absolutePath = ConfigUtils.getAbsoluteRootPath();
	private String relativePath = "";

	public String getFullPath() {
		return absolutePath + relativePath;
	}


	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}


}
