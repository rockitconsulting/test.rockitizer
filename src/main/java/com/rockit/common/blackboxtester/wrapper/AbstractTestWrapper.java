package com.rockit.common.blackboxtester.wrapper;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;
import com.rockit.common.blackboxtester.suite.structures.TestBuilder;

public abstract class AbstractTestWrapper {
	
	private  TestBuilder testBuilder;
	public static final Logger LOGGER = Logger.getLogger(AbstractTestWrapper.class.getName());
	
	@Before
	public void cleanRecordFolderAndConnectors() throws IOException  { //TODO:Better to change method name to setUp 

		TestProtocol.writeHeading(testBuilder.getTestName(), "Configuration");
		TestProtocol.write(testBuilder);
		
		TestProtocol.writeHeading(testBuilder.getTestName(), "Executing  [" +  configuration().getString(Constants.MODE_KEY) + "]");
		
		testBuilder.addStep(Constants.BEFORE_FOLDER).execute();
		testBuilder.deleteOutputFolder();
		
	}

	
	@After
	public void Assertions() {
		if(!testBuilder.isReplayMode()) { //TODO check if the errors/exceptions occurred testBuilder.isInError()
			TestProtocol.write("Assertions are only possible in replay mode. Set suite.mode in config.properties");
			return;
		}		

		TestProtocol.writeHeading(testBuilder.getTestName(), "Assertion");
		testBuilder.proceedAssertions();
	}


	public TestBuilder newTestBuilderFor(Class<? extends AbstractTestWrapper> clazz) {
		testBuilder = new TestBuilder(clazz);
		return testBuilder;
	}
}
