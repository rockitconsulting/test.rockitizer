package com.rockit.common.blackboxtester.exceptions;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConnectorExceptionTest {

	@Test
	public void testConnectorExceptionException() {
		ConnectorException ce = new ConnectorException( new Exception("test"));
		assertEquals("test", ce.getCause().getMessage());
		
	}

	@Test
	public void testConnectorExceptionString() {
		ConnectorException ce = new ConnectorException( "test");
		assertEquals("test", ce.getMessage());

	}

}
