package com.rockit.common.blackboxtester.wrapper;

import java.io.File;
import java.io.IOException;

import io.github.rockitconsulting.test.rockitizer.configuration.Configuration;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.structures.TestBuilder;

public class AbstractTestWrapperTest extends AbstractTestWrapper {
	public static Logger logger = Logger.getLogger(AbstractTestWrapperTest.class.getName());
	TestBuilder testBuilder = newTestBuilderFor(AbstractTestWrapperTest.class);

	@Test
	public void testRecord() {
		Configuration.configuration().setRunMode(Configuration.RunModeTypes.RECORD);
		Assert.assertTrue(testBuilder.isRecordFolderExists());
		Assert.assertEquals(this.getClass().getSimpleName(), testBuilder.getTestName());
		Assert.assertEquals(Constants.BEFORE_FOLDER, testBuilder.getTestStepName());
		Assert.assertEquals(testBuilder.getInFolderName(),"AbstractTestWrapperTest/");
		Assert.assertEquals(testBuilder.getOutFolderName(),"AbstractTestWrapperTest/output/");
		

	}
	
	@Test()
	public void testReplayWithNoOutputFolder() throws IOException {
		Configuration.configuration().setRunMode(Configuration.RunModeTypes.REPLAY);
		Assert.assertTrue(testBuilder.isRecordFolderExists());
		Assert.assertEquals(this.getClass().getSimpleName(), testBuilder.getTestName());
		Assert.assertEquals(Constants.BEFORE_FOLDER, testBuilder.getTestStepName());
		Assert.assertEquals(testBuilder.getInFolderName(),"AbstractTestWrapperTest/");
		Assert.assertEquals(testBuilder.getOutFolderName(),"AbstractTestWrapperTest/output/");
		Assert.assertTrue(new File(testBuilder.getReplayFolder()+"/" + "output").mkdirs());
		
		
	}


}
