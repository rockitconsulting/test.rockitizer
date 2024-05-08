package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.FileConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.nio.charset.Charset;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.connector.ConnectorResponse;
import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;

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

public class FileGetConnector implements ReadConnector {

	private String id;
	private String srcPath;
	private ConnectorResponse response;

	/**
	 * @param id  - FILEPut and key (connectorFolder)
	 */
	public FileGetConnector(String id){
		this.id = id;
		FileConnectorCfg cfg = (FileConnectorCfg) configuration().getConnectorById(id);
		this.srcPath = cfg.getPath();
	}
	



	@Override
	public void proceed() {
		try {
			File f = new File(srcPath);
			if(f.isDirectory()) {
				f = FileUtils.lastFile(f);
			}
			
			
			this.response = new ConnectorResponse(f.getName(), Files.asCharSource(f, Charset.defaultCharset()).read() ) ;
			
		} catch (Exception e) {
			throw new ConnectorException("[Connector:"+getId()+"] \t Connector error: " + getType() , e);
		}

	}

	/**
	 * @param srcPath - enabling junit
	 */
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	

	public String getSrcPath() {
		return srcPath;
	}

	public String getType() {
		return  Connectors.FILEGET.toString();
	}


	@Override
	public String getId() {
		return id;
	}


	@Override
	public ConnectorResponse getResponse() {
		return response;
	}


	@Override
	public void setReponse(ConnectorResponse response) {
		throw new ConnectorException("[Connector:" + getId() + "] \t Set method is not allowed");
	}





}
