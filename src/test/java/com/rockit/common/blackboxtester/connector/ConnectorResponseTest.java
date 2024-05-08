package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConnectorResponseTest {

	@Test
	public void testConnectorResponseJSON() {
		ConnectorResponse cr = new ConnectorResponse ("file", "{}");
		assertEquals("file", cr.getFilePrefix());
		assertEquals(".json", cr.getFileExt());
		
	}

	@Test
	public void testConnectorResponseXML() {
		ConnectorResponse cr = new ConnectorResponse ("file", "<aaa>bbb</aaa>");
		assertEquals("file", cr.getFilePrefix());
		assertEquals(".xml", cr.getFileExt());
		
	}

	@Test
	public void testConnectorResponseTXT() {
		ConnectorResponse cr = new ConnectorResponse ("file", "aaaaa");
		assertEquals("file", cr.getFilePrefix());
		assertEquals(".txt", cr.getFileExt());
		
	}

	
	@Test
	public void testConnectorResponseWithFileType() {
		ConnectorResponse cr = new ConnectorResponse ("file.xml", "aaaaa");
		assertEquals("file", cr.getFilePrefix());
		assertEquals(".xml" , cr.getFileExt());
		
	}
	
	@Test
	public void testConnectorResponseWithDot() {
		ConnectorResponse cr = new ConnectorResponse ("file.", "aaaaa");
		assertEquals("file", cr.getFilePrefix());
		assertEquals(".txt" , cr.getFileExt());
		
	}


}
