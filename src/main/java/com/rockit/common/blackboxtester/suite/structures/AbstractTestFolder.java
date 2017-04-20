package com.rockit.common.blackboxtester.suite.structures;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;

import java.io.File;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class AbstractTestFolder {
	public static final Logger LOGGER = Logger.getLogger(ConnectorFolder.class.getName());

	private String inFolderName;
	private String outFolderName;


	private String testName;
	private String testStepName;
	private String connectorName;
	private String connectorType;
	
	
	protected AbstractTestFolder(String testName) {
		this.testName = testName; 
		this.inFolderName = testName + "/";
		this.outFolderName = testName + "/" + Constants.OUTPUT_FOLDER + "/";
		
	}
	
	protected AbstractTestFolder(String testName, String testStepName) {
		this.testName = testName;
		this.testStepName = testStepName;
		this.inFolderName = testName + "/"+ testStepName + "/";
		this.outFolderName = testName + "/" + Constants.OUTPUT_FOLDER + "/" + testStepName + "/";
		
	}
	
	protected AbstractTestFolder(String testName, String testStepName, String connectorName) {
		this.testName = testName;
		this.testStepName = testStepName;
		this.connectorName = connectorName;
		this.inFolderName = testName + "/"+ testStepName + "/" + connectorName + "/";
		this.outFolderName = testName + "/" + Constants.OUTPUT_FOLDER + "/" + testStepName + "/" + connectorName + "/";
		this.connectorType = connectorName.split("@")[0] + "@";
		
	}

	

	public String getTestName() {
		return testName;
	}

	public String getConnectorName() {
		return connectorName;
	}

	public String getConnectorType() {
		return connectorType;
	}
	
	public String getOutFolderName() {
		return outFolderName;
	}

	public String getInFolderName() {
		return inFolderName;
	}

	public String getTestStepName() {
		return testStepName;
	}


	public File getInFolder() {
		return new File(getBasePath() + inFolderName);
	}

	protected File getOutFolder() {
		return getOrCreateFolder( getBasePath() + File.separator + outFolderName);
	}


	protected String getBasePath() {
		return isReplayMode() ? getReplayRootPath() : getRecordRootPath();
	}

	public boolean isReplayMode() {
		return configuration().getString(Constants.MODE_KEY).contains("replay");
	}

	protected String getRecordRootPath() {
		return new File("").getAbsolutePath().replace('\\', '/') + Constants.RECORD_PATH;
	}

	public String getReplayRootPath() {
		return new File("").getAbsolutePath().replace('\\', '/') + Constants.REPLAY_PATH;
	}

	private File getOrCreateFolder(String path) {
			File file = new File(path);
			if (!file.exists()) {
				LOGGER.debug(" creating folder " + file);
				file.mkdirs();
			}
			if (!file.isDirectory()) {
				LOGGER.error("Folders only allowed");
				throw new GenericException(new RuntimeException("Folders only allowed"));
			}
		
		return file;
	}


	public void setTestStepName(String testStepName) {
		this.testStepName = testStepName;
	}

}
