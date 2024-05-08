package com.rockit.common.blackboxtester.suite.structures;

import org.junit.Assert;
import org.junit.Test;

import com.rockit.common.blackboxtester.exceptions.GenericException;

public class ConnectorFolderTest {

	@Test
	public void testConnectorFolder() {
		ConnectorFolder cf = new ConnectorFolder("AbstractTestWrapperTest", "MyTestStep", "MQPUT.TEST"); 
		Assert.assertEquals("MQPUT.", cf.getConnectorType());
		Assert.assertEquals("MQPUT.TEST", cf.getConnectorName());
		Assert.assertEquals("AbstractTestWrapperTest", cf.getTestName());
		Assert.assertEquals("MyTestStep", cf.getTestStepName());
	}

	@Test(expected=GenericException.class)
	public void testExecuteEmpty() {
		ConnectorFolder cf = new ConnectorFolder("AbstractTestWrapperTest", "MyTestStep", "MQPUT.TEST1");
		cf.execute();
	}

	@Test
	public void testSplitFilename() {
		String filename = "response.json";
		
		Assert.assertEquals(filename.split("\\.")[0], "response");
		Assert.assertEquals(filename.split("\\.")[1], "json");
	}
	


}
