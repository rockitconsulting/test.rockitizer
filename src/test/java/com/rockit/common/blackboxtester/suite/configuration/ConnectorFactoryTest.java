
package com.rockit.common.blackboxtester.suite.configuration;
import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;

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

	static final String ENV = configuration().getString(Constants.ENV_IDX_KEY);
	
	@Test
	public void mqGetList() {
		List<Connector> connector = ConnectorFactory.connectorByFolder("MQGET@");
		assertTrue( String.valueOf( connector.size()) , connector.size()>1);
	}
	


	@Test
	public void mqGetConfiguredList() {
		List<Connector> connector = ConnectorFactory.connectorByFolder("MQGET@TEST.LIST");
		assertTrue( String.valueOf( connector.size()) , connector.size()>1);
	}

	@Test
	public void mqGetSingleQueue() {
		List<Connector> connector = ConnectorFactory.connectorByFolder("MQGET@SPLITCUSTOMER.INOUT.@ENV@");
		assertTrue( String.valueOf( connector.size()) , connector.size()>0);
	}

	
	@Test
	public void testConnectorByFolder() {
		
		List<Connector> connector = ConnectorFactory.connectorByFolder("MQPUT@RUNTIMEMONITORING.TRIGGER.@ENV@");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof MQPutConnector);
		
		connector = ConnectorFactory.connectorByFolder("DBPUT@SQL.QUERY");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof DBPutConnector);
		
		connector = ConnectorFactory.connectorByFolder("SCPPUT@CONN1");
		assertTrue("SCPPUT@CONN1 instance of " + connector.get(0), connector.get(0) instanceof SCPPutConnector);
		
		connector = ConnectorFactory.connectorByFolder("HTTPGET@ELASTIC.LOGENTRY.GET");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof HTTPGetConnector);
		
		connector = ConnectorFactory.connectorByFolder("DBGET@SQL.QUERY");
		assertTrue(connector.get(0).getId(), connector.get(0) instanceof DBGetConnector);
		
		
		connector = ConnectorFactory.connectorByFolder("MQGET@TEST.LIST");
		assertTrue(connector.size()==4);

		connector = ConnectorFactory.connectorByFolder("MQGET@TEST.EMPTY.LIST");
		assertTrue(connector.size()==0);
	}
	
	
	@Test(expected=RuntimeException.class)
	public void testUnknownConnector() {
		ConnectorFactory.connectorByFolder("UNKOWN@TEST@ENV@");
		
	}
	
	@Test
	public void testConnectorNameByFolder(){
		assertTrue("MQGET@TEST."+ENV, ConnectorFactory.connectorNameByFolder("MQGET@TEST.@ENV@").equals("MQGET@TEST."+ENV));
	}
	
	@Test
	public void testFolderNameByConnector(){
		assertTrue("MQGET@TEST.@ENV@", ConnectorFactory.folderNameByConnector("MQGET@TEST."+ENV).equals("MQGET@TEST.@ENV@"));
	}
}
