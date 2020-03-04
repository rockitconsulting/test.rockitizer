package com.rockit.common.blackboxtester.suite.structures;

import static com.rockit.common.blackboxtester.suite.configuration.ConnectorFactory.connectorByFolder;
import static com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors.MQGET;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.connector.Connector;
import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.util.FileUtils;

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

public class ConnectorFolder extends AbstractTestFolder {
	public static final Logger LOGGER = Logger.getLogger(ConnectorFolder.class.getName());

	public ConnectorFolder(String testName, String testStepName, String connectorName) {
		super(testName, testStepName, connectorName);
	}

	public void execute() {
		List<Connector> connectors = getConnectorByFolderName();
		if (connectors.isEmpty())
			LOGGER.info(getTestStepName() + "\t Constains no Connector.");
		for (Connector connector : connectors) {
			execute(connector);
		}

	}

	private void execute(Connector connector) {
		if (MQGET.toString().equalsIgnoreCase(connector.getType())) {
			saveResponses(connector);
		} else if (FileUtils.listFiles(getInFolder()).iterator().hasNext()) {

			for (File input : FileUtils.listFiles(getInFolder())) {

				if (!input.getName().equalsIgnoreCase(Constants.GITIGNORE)) {
					((WriteConnector) connector).setRequest(input);
					LOGGER.info(getTestStepName() + "\t [Connector:" + connector.getId() + "] - Writing ...");
				}
				connector.proceed();

				if (connector instanceof ReadConnector) {
					saveResponse(connector, input.getName());
				}
			}

		} else {
			connector.proceed();
			if (connector instanceof ReadConnector) {
				saveResponse(connector, "0.txt");
			}
		}
	}

	private void saveResponses(Connector connector) {
		int idx = 0;
		do {

			connector.proceed();
			String response = ((ReadConnector) connector).getResponse();

			if (null != response) {
				// LOGGER.info(getTestStepName()+"\t [connector:"+
				// connector.getName() +"] - Saving connector "+
				// connector.getName() +" content to output folder " +
				// getOutFolder() );
				try {
					LOGGER.info(getTestStepName() + "\t [Connector:" + connector.getId() + "] - Reading ...");
					Files.write(response.getBytes("UTF-8"), new File(getOutFolder() + "/" + idx + ".txt"));
				} catch (IOException e) {
					LOGGER.error("can not write output file " + idx, e);
					throw new GenericException(e);
				}
				idx++;
			}
		} while (null != ((ReadConnector) connector).getResponse() && MQGET.toString().equalsIgnoreCase(connector.getType()));
	}

	private void saveResponse(Connector connector, String fileName) {

		if (fileName.equalsIgnoreCase(Constants.GITIGNORE)) {
			fileName = "0.txt";
		} // TODO redesign

		String response = ((ReadConnector) connector).getResponse();

		if (null != response) {
			try {
				LOGGER.info(getTestStepName() + "\t [Connector:" + connector.getId() + "] - Reading ...");
				Files.write(response.getBytes("UTF-8"), new File(getOutFolder() + "/" + fileName));
			} catch (IOException e) {
				LOGGER.error("can not write output file " + fileName, e);
				throw new GenericException(e);
			}

		}
	}

	private List<Connector> getConnectorByFolderName() {
		return connectorByFolder(getInFolder().getName());

	}

}