package com.rockit.common.blackboxtester.connector;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.impl.SCPPutConnector;
import com.rockit.common.blackboxtester.exceptions.GenericException;

public class SCPPutConnectorTest {
	
	public static final Logger LOGGER = Logger.getLogger(SCPPutConnectorTest.class.getName());
	
//	private static final String USER = configuration().getString("SCPPUT@CONN1"+Constants.SCP_USR_KEY);
//	private static final String PASSWORD = configuration().getString("SCPPUT@CONN1"+Constants.SCP_PWD_KEY);
//	private static final String DESTINATION = configuration().getString("SCPPUT@CONN1"+Constants.SCP_DEST_PATH_KEY);;
//	private static final String HOST = configuration().getString("SCPPUT@CONN1"+Constants.SCP_HOST_KEY);;
	
	private  SCPPutConnector scpConnector;
	
	@Before
	public  void setUp() {
	 scpConnector = new SCPPutConnector("SCPPUT.CONN1");

	}
	


	
	@Test
	public void testType(){
		assertTrue(scpConnector.getType(), scpConnector.getType().equals("SCPPUT."));
	}
	
	@Test
	public void testName(){
		assertTrue(scpConnector.getName(), scpConnector.getName().equals("SCPPUT.CONN1"));
	}
	
	@Test
	public void testId(){
		assertTrue(scpConnector.getId(), scpConnector.getId().equals("SCPPUT.CONN1"));
	}

}
