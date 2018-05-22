package com.rockit.common.blackboxtester.suite.structures;
import static com.rockit.common.blackboxtester.suite.configuration.SettingsHolder.cacheByConnector;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.settings.MQHeader;
import com.rockit.common.blackboxtester.connector.settings.SettingsBuilder;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;

public class TestStepBuilderTest {
	public static final Logger LOGGER = Logger.getLogger(TestStepBuilderTest.class.getName());
	TestStepBuilder testStepBuilder;
	
	@Before
	public void setUp() {
		testStepBuilder = new TestStepBuilder("TestRockitzerTest", "addMQ001");
				
		testStepBuilder.addSettings("MQPUT.TEST.TRIGGER.01", SettingsBuilder.addMQHeader().
				setExpiry(1).
				setMsgType(0).
				setCodedCharSetId(819).
				setMsgFormat("test").
				setReplyToQ("test").
				setReplyToQMgr("test"));
	}
	
	@Test //TODO rewrite according to current enums. No text for constants please
	public void testDefaultAddSettings(){
		
		MQHeader defaultSettings = (MQHeader) cacheByConnector(Connectors.MQPUT.toString(), null);

		assertTrue(defaultSettings.getType().equals(Connectors.MQPUT.toString()));

		assertTrue(defaultSettings.getValues().equals("-1, 8, 1208, , , MQSTR "));
		
	}
	
	@Test
	public void testAddSettings(){
		
		MQHeader settings = (MQHeader) cacheByConnector(Connectors.MQPUT.toString(), "TEST.TRIGGER.01");
		
		assertTrue(settings.getType().equals(Connectors.MQPUT.toString()));
		
		
		assertTrue(settings.getValues(), settings.getValues().equals("1, 0, 819, test, test, test"));
		
		//testStepBuilder.execute();
//		testStepBuilder.getConnectorFolders();
		
		//testStepBuilder.relativize("");
		
	}
	
	
}
