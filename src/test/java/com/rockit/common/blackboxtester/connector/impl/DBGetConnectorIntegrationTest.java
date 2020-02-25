package com.rockit.common.blackboxtester.connector.impl;

import org.junit.Test;

public class DBGetConnectorIntegrationTest {

	@Test
	public void test() {
		
		DBGetConnector dbGet = new DBGetConnector("DBGET.GETBOOKS");
		dbGet.proceed();
	}

}
