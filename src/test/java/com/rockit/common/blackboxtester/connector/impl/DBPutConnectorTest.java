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


/**
*  Test.Rockitizer - API regression testing framework 
*   Copyright (C) 2020  rockit.consulting GmbH
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see http://www.gnu.org/licenses/.
*
*/

public class DBPutConnectorTest {

	public static Logger log = Logger.getLogger(DBPutConnectorTest.class.getName());

	
	@Test
	public void testMultiline() throws URISyntaxException, IOException {
	
		DBPutConnector dbPutConnector = new DBPutConnector("DBPUT.CLAEN");
		
		Path src = Paths.get(ClassLoader.getSystemResource("connectors/db/DBPutConnector/multiLinePayloadWithComments.sql").toURI());
		File multi = new File(src.toString());
		assertTrue(multi.exists());
		dbPutConnector.setRequest(multi);
		Assert.assertTrue(dbPutConnector.extractSQLCommandsFromPayload().size()==2);
		dbPutConnector.extractSQLCommandsFromPayload().forEach( cmd -> log.info(cmd) );

		
		
		

		
	}

	@Test
	public void testSemicolon() throws URISyntaxException, IOException {
	
		DBPutConnector dbPutConnector = new DBPutConnector("DBPUT.CLAEN");
		
		Path src = Paths.get(ClassLoader.getSystemResource("connectors/db/DBPutConnector/multiSemicolonPayloadWithComments.sql").toURI());
		File multi = new File(src.toString());
		assertTrue(multi.exists());
		dbPutConnector.setRequest(multi);
		Assert.assertEquals(3, dbPutConnector.extractSQLCommandsFromPayload().size());
		dbPutConnector.extractSQLCommandsFromPayload().forEach( cmd -> {
			cmd = cmd.replaceAll(DBPutConnector.LINE_SEPARATOR,  " ");
			Assert.assertTrue(!cmd.contains(DBPutConnector.LINE_SEPARATOR));
			log.info(cmd);	
		});

		
		
		

		
	}
	
	
}
