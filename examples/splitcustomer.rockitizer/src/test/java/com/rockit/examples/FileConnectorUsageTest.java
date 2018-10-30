package com.rockit.examples;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.rockit.common.blackboxtester.suite.structures.TestBuilder;
import com.rockit.common.blackboxtester.wrapper.AbstractTestWrapper;

public class FileConnectorUsageTest extends AbstractTestWrapper {
	public static Logger logger = Logger.getLogger(FileConnectorUsageTest.class.getName());
	public TestBuilder testBuilder = newTestBuilderFor(FileConnectorUsageTest.class);


	@Test
	public void checkFileConnectorFunctionality() throws Exception {
		testBuilder.addStep("a001Putfiles")
                    .execute().sleep(1000);
		
		testBuilder.addStep("a002Getfiles")
        .execute();

		
	}
}
