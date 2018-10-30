package com.rockit.common.blackboxtester.suite.structures;

//import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
//import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class HttpConnectorTest {
	
	public static final Logger LOGGER = Logger.getLogger(HttpConnectorTest.class.getName());
	
	List<String> connectors = ImmutableList.of("HTTP.TESTPOST");
	
    
	
	@Test
	public void testAddStep(){
		
		TestBuilder testBuilder = new TestBuilder(HttpConnectorTest.class);
		testBuilder.addStep("a001DoHttpTests").execute();
		//TestStepBuilder testStepBuilder = testBuilder.addStep("a001DoHttpTests");

//		assertTrue(testBuilder.getRecordRootPath(),testBuilder.getRecordRootPath().endsWith("src/test/resources/"));
//		assertTrue(testBuilder.getReplayRootPath(),testBuilder.getReplayRootPath().endsWith("target/replay/"));
//		
//		String testName = HttpConnectorTest.class.getSimpleName();
//		assertTrue(testBuilder.getRecordOutputFolder() ,testBuilder.getRecordOutputFolder().endsWith("src/test/resources/"+testName + "/" + Constants.OUTPUT_FOLDER));
//		assertTrue(testBuilder.getReplayOutputFolder(),testBuilder.getReplayOutputFolder().endsWith("target/replay/"+ testName + "/" + Constants.OUTPUT_FOLDER));
//		
//		
//		assertTrue(testBuilder.getInFolderName(), testBuilder.getInFolderName().equals(testName+"/"));;
//		assertTrue(testBuilder.getOutFolderName(), testBuilder.getOutFolderName().equals(testName+"/output/"));;
//		assertTrue(testBuilder.getTestName(), testBuilder.getTestName().equals("HttpConnectorTest"));
//		assertTrue(testStepBuilder.getTestStepName() + "="  + testBuilder.getTestStepName() , testStepBuilder.getTestStepName().equals(  testBuilder.getTestStepName() ));
//		assertTrue(testStepBuilder.getTestStepName(), testStepBuilder.getTestStepName().equals("a001DoHttpTests"));
//
//		
//		
//		for (ConnectorFolder connectorFolder: testStepBuilder.getConnectorFolders() ) {
//			assertTrue(connectors.contains(connectorFolder.getConnectorName()) );
//		}
		
	//	testStepBuilder.execute();
		
	}	
	
	
}
