package com.rockit.common.blackboxtester.connector.impl;

import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnectorCfg;

import org.junit.Test;

import com.rockit.common.blackboxtester.exceptions.ConnectorException;

public class HTTPConnectorIT {

	@Test(expected=ConnectorException.class)
	public void testUnknownHostException() {
		HTTPConnectorCfg cfg = new HTTPConnectorCfg();
		cfg.setUrl("http://dhfjdhf.com");
		cfg.setMethod("POST");
		cfg.setTimeout("400");
		
		new HTTPConnector(cfg).proceed();
	}
	
	@Test
	public void test404PnF() {
		HTTPConnectorCfg cfg = new HTTPConnectorCfg();
		cfg.setUrl("http://SCHARRDEV01:7800/BookService2");
		cfg.setMethod("POST");
		cfg.setTimeout("400");
		
		new HTTPConnector(cfg).proceed();
	}

}
