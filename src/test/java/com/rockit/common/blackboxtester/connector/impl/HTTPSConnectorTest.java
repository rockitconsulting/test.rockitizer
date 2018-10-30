package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class HTTPSConnectorTest {

	HTTPSConnector c = new HTTPSConnector("HTTPS.TESTPOST");
//	private StringBuilder resultBuilder;

	@Test
	public void testHTTPSConnector() {
		assertNotNull(c);
		c.getId();

	}

	@Test
	public void testProceed() {
		c.proceed();
		String response = c.getResponse();
		assertNotNull(response);
	}

	@Test
	public void testGetName() {
		assertEquals("HTTPS.TESTPOST", c.getName());
	}

	@Test
	public void testGetId() {
		assertEquals(c.getId(), c.getType() + c.getName());
	}

	@Test
	public void testGetType() {
		assertEquals(c.getType(), Constants.Connectors.HTTPS.toString());
	}

}
