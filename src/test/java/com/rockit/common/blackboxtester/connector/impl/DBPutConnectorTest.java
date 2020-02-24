package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class DBPutConnectorTest {

	public static Logger log = Logger.getLogger(DBPutConnectorTest.class.getName());

	
	@Test
	public void test() throws URISyntaxException, IOException {
	
		DBPutConnector dbPutConnector = new DBPutConnector("DBPUT.CLAEN");
		
		Path src = Paths.get(ClassLoader.getSystemResource("connectors/db/DBPutConnector/multiLinePayloadWithComments.sql").toURI());
		File multi = new File(src.toString());
		assertTrue(multi.exists());
		dbPutConnector.setRequest(multi);
		Assert.assertTrue(dbPutConnector.extractSQLCommandsFromPayload().size()==2);
		dbPutConnector.extractSQLCommandsFromPayload().forEach( cmd -> log.info(cmd) );

		
		
		

		
	}

}
