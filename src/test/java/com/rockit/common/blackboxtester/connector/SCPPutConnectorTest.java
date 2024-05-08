package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.assertTrue;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.impl.SCPPutConnector;

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

public class SCPPutConnectorTest {
	
	public static final Logger LOGGER = Logger.getLogger(SCPPutConnectorTest.class.getName());
	
//	private static final String USER = configuration().getString("SCPPUT@CONN1"+Constants.SCP_USR_KEY);
//	private static final String PASSWORD = configuration().getString("SCPPUT@CONN1"+Constants.SCP_PWD_KEY);
//	private static final String DESTINATION = configuration().getString("SCPPUT@CONN1"+Constants.SCP_DEST_PATH_KEY);;
//	private static final String HOST = configuration().getString("SCPPUT@CONN1"+Constants.SCP_HOST_KEY);;
	
	private  SCPPutConnector scpConnector;
	
	@Before
	public  void setUp() {
	 scpConnector = new SCPPutConnector("SCPPUT.FILE");

	}
	


	
	@Test
	public void testType(){
		assertTrue(scpConnector.getType(), scpConnector.getType().equals("SCPPUT."));
	}
	
	@Test
	public void testName(){
		assertTrue(scpConnector.getId(), scpConnector.getId().equals("SCPPUT.FILE"));
	}
	
	@Test
	public void testId(){
		assertTrue(scpConnector.getId(), scpConnector.getId().equals("SCPPUT.FILE"));
	}

}
