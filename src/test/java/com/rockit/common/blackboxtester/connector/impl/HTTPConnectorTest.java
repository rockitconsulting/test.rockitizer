package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.junit.Test;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class HTTPConnectorTest {

	HTTPConnector c = new HTTPConnector("HTTP.TESTPOST");

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
	public void testParseJSON() {
		String myJson = "{\"widget\": \"center\"}";
		String expected = "<widget>center</widget>";

		assertEquals(expected, c.parseJSON(myJson));

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
		fail("Not yet implemented");
	}

	@Test
	public void testSetReponse() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRequestFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRequestString() {
		fail("Not yet implemented");
	}

}
