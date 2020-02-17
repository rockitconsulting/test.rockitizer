package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

/**
 * Base CLI class with context setup for JUnit Support
 *
 *
 */
public class RuntimeContextCLI {

	static final String OUTPUT = "output";

	private String resourcesFileName = "resources.yaml";
	private String testcasesFileName = "testcases.yaml";

	private String absolutePath = ConfigUtils.getAbsoluteRootPath();
	private String relativePath = "";

	String getFullPath() {
		return absolutePath + relativePath;
	}

	String getResourcesFileName() {
		return resourcesFileName;
	}

	void setResourcesFileName(String resourcesFileName) {
		this.resourcesFileName = resourcesFileName;
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

	public String getTestcasesFileName() {
		return testcasesFileName;
	}

	public void setTestcasesFileName(String testcasesFileName) {
		this.testcasesFileName = testcasesFileName;
	}

}
