package com.rockit.common.blackboxtester.suite.structures;

import static com.rockit.common.blackboxtester.suite.configuration.ConnectorFactory.connectorByFolder;
import static com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors.MQGET;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.connector.Connector;
import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;


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

	public ConnectorFolder(String testName, String testStepName, String connectorName) {
		super(testName, testStepName, connectorName);
	}

	/**
	 * Iterating over connectors and applying the needed logic on each
	 */
	public void execute() {
		List<Connector> connectors = getConnectorByFolderName();
		if (connectors.isEmpty()) {
			throw new GenericException(" teststep cannot be empty " + getInFolder() );
		}
		
		for (Connector connector : connectors) {
			execute(connector);
		}

	}

	private void execute(Connector connector) {
		if (MQGET.toString().equalsIgnoreCase(connector.getType())) {
			handleMQGetConnector(connector);
			return;
		}
		
		if (FileUtils.listFiles(getInFolder(), true).iterator().hasNext()) {
			handlePayloads(connector);
		} else {
			handleReadConnector(connector);
		}
	}

	private void handleReadConnector(Connector connector) {
		connector.proceed();
		if (connector instanceof ReadConnector) {
			saveResponse(connector, "0.txt");
		}
	}

	private void handlePayloads(Connector connector) {
		for (File input : FileUtils.listFiles(getInFolder(), true)) {
			((WriteConnector) connector).setRequest(input);
			TestProtocol.write(getTestStepName() + "\t [Connector:" + connector.getId() + "] - Writing ...");
			connector.proceed();

			if (connector instanceof ReadConnector) {
				saveResponse( connector, input.getName() );
			}
		}
	}

	private void handleMQGetConnector(Connector connector) {
		int idx = 0;
		do {

			connector.proceed();
			String response = ((ReadConnector) connector).getResponse();

			if (null != response) {
				try {
					TestProtocol.write(getTestStepName() + "\t [Connector:" + connector.getId() + "] - Reading ...");
					Files.write(response.getBytes("UTF-8"), new File(getOutFolder() + "/" + idx + ".txt"));
				} catch (IOException e) {
					throw new GenericException("can not write output file " + idx, e);
				}
				idx++;
			}
		} while (null != ((ReadConnector) connector).getResponse() && MQGET.toString().equalsIgnoreCase(connector.getType()));
	}

	private void saveResponse(Connector connector, String fileName) {

		String response = ((ReadConnector) connector).getResponse();

		if (null != response) {
			try {
				TestProtocol.write(getTestStepName() + "\t [Connector:" + connector.getId() + "] - Reading ...");
				Files.write(response.getBytes("UTF-8"), new File(getOutFolder() + "/" + fileName));
			} catch (IOException e) {
				throw new GenericException("can not write output file " + fileName,e);
			}

		}
	}

	private List<Connector> getConnectorByFolderName() {
		return connectorByFolder(getInFolder().getName());

	}

}