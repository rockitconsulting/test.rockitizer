package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class HTTPConnectorIntegrationTest {
	
	HTTPConnector_old c = new HTTPConnector_old("HTTP.TESTPOST");

	@Test
	public void testPutProceed() {
		assertNotNull(c);
//		c.setUrlStr("http://localhost:8080/putjson");
//		
//		c.setMethod("PUT");
//		c.setRequest("<xml/>");
		c.proceed();
		
	}


}
