package io.github.rockitconsulting.test.rockitizer.validation.model;

import java.io.File;

/**
 * Context for validation errors
 *
 */
public class Context {

	private String id;
	private String rootPath;
	private String testCase;
	private String testStep;
	private String connector;
	
	@SuppressWarnings("unused")
	private Context() {}
	

	/**
	 * Context based on the FS location for testcases validation 
	 * @param connectorFolder
	 */
	public Context(File connectorFolder) {
		connector = connectorFolder.getName();
		testStep = connectorFolder.getParentFile().getName();
		testCase  = connectorFolder.getParentFile().getParentFile().getName();
		rootPath  = connectorFolder.getParentFile().getParentFile().getParentFile().getAbsolutePath();
	}

	/**
	 * Context based on the id for resources validation 
	 * @param id 
	 */
	public Context(String id) {
		this.id  = id;
	}
	
	public String getRootPath() {
		return rootPath;
	}

	public String getTestCase() {
		return testCase;
	}
	public String getTestStep() {
		return testStep;
	}
	public String getConnector() {
		return connector;
	}


	@Override
	public String toString() {
		return "Context [ " + 
				 (id!=null?"id=" + id:"") + 
				 (rootPath!=null?"rootPath=" + rootPath:"") +
				 (testCase!=null?", testCase=" + testCase:"") +
				 (testStep!=null?", testStep=" + testStep:"") +
				 (connector!=null?", connector=" + connector:"") +
				  "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connector == null) ? 0 : connector.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rootPath == null) ? 0 : rootPath.hashCode());
		result = prime * result + ((testCase == null) ? 0 : testCase.hashCode());
		result = prime * result + ((testStep == null) ? 0 : testStep.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Context other = (Context) obj;
		if (connector == null) {
			if (other.connector != null)
				return false;
		} else if (!connector.equals(other.connector))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (rootPath == null) {
			if (other.rootPath != null)
				return false;
		} else if (!rootPath.equals(other.rootPath))
			return false;
		if (testCase == null) {
			if (other.testCase != null)
				return false;
		} else if (!testCase.equals(other.testCase))
			return false;
		if (testStep == null) {
			if (other.testStep != null)
				return false;
		} else if (!testStep.equals(other.testStep))
			return false;
		return true;
	}



	
}
