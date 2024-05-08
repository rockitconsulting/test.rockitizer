package com.rockit.common.blackboxtester.suite.configuration;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.Connector;
import com.rockit.common.blackboxtester.connector.impl.DBGetConnector;
import com.rockit.common.blackboxtester.connector.impl.DBPutConnector;
import com.rockit.common.blackboxtester.connector.impl.HTTPConnector;
import com.rockit.common.blackboxtester.connector.impl.MQPutConnector;

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

public class ConnectorFactoryTest {
	
	
	@Before
	public void before() {
		TestObjectFactory.resetConfigurationToDefault();
		Assert.assertNotNull(configuration());
		Assert.assertEquals(configuration().getTchApi().getTestcasesFileName(), "testcases.yaml");
		Assert.assertEquals(configuration().getRhApi().getResourcesFileName(), "resources.yaml");
	}


	@Test(expected = RuntimeException.class)
	public void notsupported() {
		ConnectorFactory.connectorByFolder("UNKOWN@TEST@ENV@");

	}

	@Test
	public void mqGetSingleQueue() {
		List<Connector> connector = ConnectorFactory.connectorByFolder("MQPUT.IN.MQ2DB");
		assertTrue(String.valueOf(connector.size()), connector.size() > 0);
	}

	@Test
	public void testConnectorByFolder() {

		List<Connector> connector = ConnectorFactory.connectorByFolder("MQPUT.IN.MQ2DB");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof MQPutConnector);

		connector = ConnectorFactory.connectorByFolder("DBPUT.CLAEN");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof DBPutConnector);

	
		connector = ConnectorFactory.connectorByFolder("HTTP.ADDBOOK");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof HTTPConnector);

		connector = ConnectorFactory.connectorByFolder("DBGET.GETBOOKS");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof DBGetConnector);

	}

	@Test(expected = RuntimeException.class)
	public void testUnknownConnector() {
		ConnectorFactory.connectorByFolder("UNKOWN.TEST");

	}

}
