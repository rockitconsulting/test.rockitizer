
package com.rockit.common.blackboxtester.suite.configuration;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.rockit.common.blackboxtester.connector.Connector;
import com.rockit.common.blackboxtester.connector.impl.DBGetConnector;
import com.rockit.common.blackboxtester.connector.impl.DBPutConnector;
import com.rockit.common.blackboxtester.connector.impl.HTTPGetConnector;
import com.rockit.common.blackboxtester.connector.impl.MQPutConnector;
import com.rockit.common.blackboxtester.connector.impl.SCPPutConnector;

public class ConnectorFactoryTest {

	
	@Test(expected=RuntimeException.class)
	public void notsupported() {
		ConnectorFactory.connectorByFolder("UNKOWN@TEST@ENV@");
		
	}

	
	

	@Test
	public void mqGetSingleQueue() {
		List<Connector> connector = ConnectorFactory.connectorByFolder("MQPUT.MYSPLITCUSTOMER");
		assertTrue( String.valueOf( connector.size()) , connector.size()>0);
	}

	
	@Test
	public void testConnectorByFolder() {
		
		List<Connector> connector = ConnectorFactory.connectorByFolder("MQPUT.MYSPLITCUSTOMER");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof MQPutConnector);
		
		connector = ConnectorFactory.connectorByFolder("DBPUT.SQL.QUERY");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof DBPutConnector);
		
		connector = ConnectorFactory.connectorByFolder("SCPPUT.CONN1");
		assertTrue("SCPPUT.CONN1 instance of " + connector.get(0), connector.get(0) instanceof SCPPutConnector);
		
		connector = ConnectorFactory.connectorByFolder("HTTPGET.ELASTIC.LOGENTRY.GET");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof HTTPGetConnector);
		
		connector = ConnectorFactory.connectorByFolder("DBGET.SQL.QUERY");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof DBGetConnector);
		
	}
	
	
	@Test(expected=RuntimeException.class)
	public void testUnknownConnector() {
		ConnectorFactory.connectorByFolder("UNKOWN.TEST");
		
	}
	
}
