package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class HTTPConnectorTest {

	HTTPConnector_old c = new HTTPConnector_old("HTTP.TESTPOST");
//	private StringBuilder resultBuilder;

	@Test
	public void testHTTPConnector() {
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
		assertEquals("HTTP.TESTPOST", c.getName());
	}

	@Test
	public void testGetId() {
		assertEquals(c.getId(), c.getType() + c.getName());
	}

	@Test
	public void testGetType() {
		assertEquals(c.getType(), Constants.Connectors.HTTP.toString());
	}

	@Test
	public void testGetResponse() {
//		StringBuilder resultBuilder = null;
//		assertEquals(c.getResponse(), resultBuilder.toString());
	}

	@Test
	public void testSetReponse() {
//		StringBuilder resultBuilder = null;
//		String response = null;
//		assertEquals(c.setReponse(response), this.resultBuilder = new StringBuilder(response));
	}

	@Test
	public void testSetRequestFile() {
//		fail("Not yet implemented");
	}

	@Test
	public void testSetRequestString() {
//		fail("Not yet implemented");
	}

}
