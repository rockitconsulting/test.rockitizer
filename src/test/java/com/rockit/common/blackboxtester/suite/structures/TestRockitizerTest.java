package com.rockit.common.blackboxtester.suite.structures;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
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

public class TestRockitizerTest {
	
	public static final Logger LOGGER = Logger.getLogger(TestRockitizerTest.class.getName());
	
	List<String> connectors = ImmutableList.of("MQPUT@RUNTIMEMONITORING.TRIGGER.@ENV@",
			"MQPUT@TEST.TRIGGER.@ENV@",
			"MQPUT@TEST2.TRIGGER.@ENV@",
			"MQPUT@TEST3.TRIGGER.@ENV@",
			"@TEST4.TRIGGER.@ENV@");
	
    
	
	@Test
	public void testAddStep(){
		
		TestBuilder testBuilder = new TestBuilder(TestRockitizerTest.class);
		TestStepBuilder testStepBuilder = testBuilder.addStep("a001TestMQ");

		String recordRootPath = "src/test/resources/";
		String replayRootPath = "target/replay/";
		
		assertTrue(testBuilder.getRecordRootPath(),testBuilder.getRecordRootPath().endsWith(recordRootPath));
		assertTrue(testBuilder.getReplayRootPath(),testBuilder.getReplayRootPath().endsWith(replayRootPath));
		
		String testName = TestRockitizerTest.class.getSimpleName();
		String recordOutputFolder = "src/test/resources/"+testName + "/" + Constants.OUTPUT_FOLDER;
		String replayOutputFolder = "target/replay/"+ testName + "/" + Constants.OUTPUT_FOLDER;
		
		assertTrue(testBuilder.getRecordOutputFolder() ,testBuilder.getRecordOutputFolder().endsWith(recordOutputFolder));
		assertTrue(testBuilder.getReplayOutputFolder(),testBuilder.getReplayOutputFolder().endsWith(replayOutputFolder));
		
		
		assertTrue(testBuilder.getInFolderName(), testBuilder.getInFolderName().equals(testName+"/"));;
		assertTrue(testBuilder.getOutFolderName(), testBuilder.getOutFolderName().equals(testName+"/output/"));;
		assertTrue(testBuilder.getTestName(), testBuilder.getTestName().equals("TestRockitizerTest"));
		assertTrue(testStepBuilder.getTestStepName() + "="  + testBuilder.getTestStepName() , testStepBuilder.getTestStepName().equals(  testBuilder.getTestStepName() ));
		assertTrue(testStepBuilder.getTestStepName(), testStepBuilder.getTestStepName().equals("a001TestMQ"));

		
		
		for (ConnectorFolder connectorFolder: testStepBuilder.getConnectorFolders() ) {
			assertTrue(connectors.contains(connectorFolder.getConnectorName()) );
		}
		
		//testStepBuilder.writeToConnectors(); //TODO write test
		
	}	

//	@Test
//	public void testAddAssertion() throws Exception {
//		throw new RuntimeException("not yet implemented");
//	}
//
//	@Test
//	public void testProceedAssertions() throws Exception {
//		throw new RuntimeException("not yet implemented");
//	}
//
//	@Test
//	public void testGetMasterRecordFolder() throws Exception {
//		throw new RuntimeException("not yet implemented");
//	}
//
//	@Test
//	public void testGetTestFolder() throws Exception {
//		throw new RuntimeException("not yet implemented");
//	}
//
//	@Test
//	public void testGetReplayFolder() throws Exception {
//		throw new RuntimeException("not yet implemented");
//	}
//
//	@Test
//	public void testGetReplayRecordFolder() throws Exception {
//		throw new RuntimeException("not yet implemented");
//	}
}
