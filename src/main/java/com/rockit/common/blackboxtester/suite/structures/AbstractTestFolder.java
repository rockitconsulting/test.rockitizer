package com.rockit.common.blackboxtester.suite.structures;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.Configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

/**
*  Test.Rockitizer - API regression testing framework 
*   Copyright (C) 2020  rockit.consulting GmbH
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see http://www.gnu.org/licenses/.
*
*/

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
		this.inFolderName = testName + "/" + testStepName + "/";
		this.outFolderName = testName + "/" + Constants.OUTPUT_FOLDER + "/" + testStepName + "/";

	}

	protected AbstractTestFolder(String testName, String testStepName, String connectorName) {
		this.testName = testName;
		this.testStepName = testStepName;
		this.connectorName = connectorName;
		this.inFolderName = testName + "/" + testStepName + "/" + connectorName + "/";
		this.outFolderName = testName + "/" + Constants.OUTPUT_FOLDER + "/" + testStepName + "/" + connectorName + "/";
		this.connectorType = ConfigUtils.connectorTypeFromConnectorId(connectorName) + ".";

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
		File inFoler = new File(getBasePath() + inFolderName);
		if (!inFoler.exists())
			throw new GenericException("Folder " + inFoler + " doesn't exist!");
		return new File(getBasePath() + inFolderName);
	}

	protected File getOutFolder() {
		return getOrCreateFolder(getBasePath() + File.separator + outFolderName);
	}

	protected String getBasePath() {
		return isReplayMode() ? getReplayRootPath() : getRecordRootPath();
	}

	public boolean isReplayMode() {
		 return configuration().getRunMode()==Configuration.RunModeTypes.REPLAY;
	}
	public boolean isAssertMode() {
		 return configuration().getRunMode()==Configuration.RunModeTypes.ASSERT;
	}
	public boolean isRecordMode() {
		 return configuration().getRunMode()==Configuration.RunModeTypes.RECORD;
	}

	
	public String getRecordRootPath() {
		return ConfigUtils.getAbsolutePathToRoot() + Constants.RECORD_PATH;
	}

	public String getReplayRootPath() {
		return ConfigUtils.getAbsolutePathToRoot() + Constants.REPLAY_PATH;
	}

	private File getOrCreateFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			LOGGER.debug(" creating folder " + file);
			file.mkdirs();
		}
		if (!file.isDirectory()) {
			LOGGER.error("Folders only allowed");
			throw new GenericException(new RuntimeException("File found, but folders only allowed:" + path));
		}

		return file;
	}

	public void setTestStepName(String testStepName) {
		this.testStepName = testStepName;
	}
	
	
	public String printDescription() {
		return (this.getDescrptionText()!=null?"," + "DESC: ["+ this.getDescrptionText()+ "]":"");
	}
	
	private String getDescrptionText() {
		return FileUtils.readFile( new File(getRecordRootPath()+ "/" + inFolderName+ "/" + Constants.DESCRIPTION_TXT) );
	}

}
