package com.rockit.common.blackboxtester.suite.structures;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

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

public class AbstractTestFolderTest {

	public static final Logger LOGGER = Logger.getLogger(AbstractTestFolderTest.class.getName());
	
	private static final String TEST_FOLDER = "TestRockitizerTest";
	

	
	@Test
	public void parseTestBuilder() {
		String p1 = "RuntimeMonitoringTest/";
		String p1output = "RuntimeMonitoringTest/output/";
		
		AbstractTestFolder asf =  new AbstractTestFolder("RuntimeMonitoringTest");
		assertTrue(asf.getInFolderName(),p1.equals( asf.getInFolderName() ));
		LOGGER.info( asf.getOutFolderName() + " = "+ p1output );
		assertTrue(asf.getOutFolderName() + " = "+ p1output, p1output.equals( asf.getOutFolderName() ));
		
	}
	
	
	@Test
	public void parseStepbuilder() {
		String p2 ="RuntimeMonitoringTest/0BEFORE/";
		String p2output = "RuntimeMonitoringTest/output/0BEFORE/";
		
		AbstractTestFolder asf =  new AbstractTestFolder("RuntimeMonitoringTest","0BEFORE");
		
		assertTrue(asf.getInFolderName(),p2.equals( asf.getInFolderName() ));
		LOGGER.info( asf.getOutFolderName() + " = "+ p2output );
		assertTrue(asf.getOutFolderName() + " = "+ p2output, p2output.equals( asf.getOutFolderName() ));
		
		assertTrue(Constants.BEFORE_FOLDER.equals( asf.getTestStepName()));

		
	}
	
	
	
	@Test
	public void parseConstructor() {
		
		AbstractTestFolder asf;

		
		asf =  new AbstractTestFolder("RuntimeMonitoringTest","0BEFORE","MQPUT.OUTPUT.TEST");
		assertTrue("RuntimeMonitoringTest".equals( asf.getTestName() ));
		assertTrue(Constants.BEFORE_FOLDER.equals( asf.getTestStepName() ));
		assertTrue("MQPUT.OUTPUT.TEST".equals( asf.getConnectorName() ));
		assertTrue("MQPUT. equals " +  asf.getConnectorType() , "MQPUT.".equals( asf.getConnectorType() ));
		
		
		
		asf =  new AbstractTestFolder("RuntimeMonitoringTest","a001FireTrigger","MQ.RUNTIMEMONITORING.TRIGGER");
		assertTrue("RuntimeMonitoringTest".equals( asf.getTestName() ));
		assertTrue("a001FireTrigger".equals( asf.getTestStepName() ));
		assertTrue("MQ.RUNTIMEMONITORING.TRIGGER".equals( asf.getConnectorName() ));
		assertTrue("RuntimeMonitoringTest/output/a001FireTrigger/MQ.RUNTIMEMONITORING.TRIGGER/".equals(asf.getOutFolderName().replace("\\", "/")));
		assertTrue("RuntimeMonitoringTest/a001FireTrigger/MQ.RUNTIMEMONITORING.TRIGGER/".equals(asf.getInFolderName().replace("\\", "/")));

		asf =  new AbstractTestFolder("RuntimeMonitoringTest");
		assertTrue("RuntimeMonitoringTest".equals( asf.getTestName() ));
		assertTrue("RuntimeMonitoringTest/output/".equals(asf.getOutFolderName() ));
		assertTrue("RuntimeMonitoringTest/".equals(asf.getInFolderName() ));
		
		
		
	}


	@Test
	public void testGetInFolder() {
		
		AbstractTestFolder asf =  new AbstractTestFolder(TEST_FOLDER);
		try {
			assertTrue( asf.getInFolder().toString().endsWith(TEST_FOLDER));
		} catch (GenericException ge) {
			assertTrue( ge.getMessage().contains((TEST_FOLDER)));
			
		}
		

	}

	@Test
	public void testGetBasePath()  {
		AbstractTestFolder asf =  new AbstractTestFolder(TEST_FOLDER);
		String path = asf.getBasePath();
		
		if(asf.isReplayMode()){
			assertTrue("/target/replay/", path.endsWith(Constants.REPLAY_PATH) );
			
		}else {
			assertTrue("/src/test/resources/", path.endsWith(Constants.RECORD_PATH));
		}
	}	
}
