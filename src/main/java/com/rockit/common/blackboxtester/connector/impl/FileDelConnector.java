package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.FileConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;

import com.rockit.common.blackboxtester.connector.Connector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;

/**
 * Test.Rockitizer - API regression testing framework Copyright (C) 2020
 * rockit.consulting GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 *
 */

public class FileDelConnector implements Connector {

	private String id;

	private String srcPath;

	/**
	 * @param id
	 *            - FILEPut and key (connectorFolder)
	 */
	public FileDelConnector(String id) {
		this.id = id;
		FileConnector cfg = (FileConnector) configuration().getConnectorById(id);
		this.srcPath = cfg.getPath();
	}

	@Override
	public void proceed() {
		try {
			File f = new File(srcPath);
			FileUtils.deleteFilesRecursive(f);

		} catch (Exception e) {
			new ConnectorException("[Connector:" + getId() + "] \t Connector error: " + getType(), e);
		}

	}

	/**
	 * @param srcPath
	 *            - enabling junit
	 */
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getType() {
		return Connectors.FILEDEL.toString();
	}

	@Override
	public String getId() {
		return id;
	}

}
