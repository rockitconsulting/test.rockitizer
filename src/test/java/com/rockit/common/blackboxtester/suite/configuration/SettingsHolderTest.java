package com.rockit.common.blackboxtester.suite.configuration;

import static com.rockit.common.blackboxtester.suite.configuration.SettingsHolder.cache;
import static com.rockit.common.blackboxtester.suite.configuration.SettingsHolder.cacheByConnector;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.settings.MQHeader;
import com.rockit.common.blackboxtester.connector.settings.SettingsBuilder;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;


public class SettingsHolderTest {

	@Before
	public void setUp() {
		
		cache().put("MQPUT.TEST.TRIGGER.YD1",SettingsBuilder.addMQHeader().
				setExpiry(1).
				setMsgType(0).
				setCodedCharSetId(819).
				setMsgFormat("test").
				setReplyToQ("test").
				setReplyToQMgr("test"));
	}

	
	@Test
	public void testDefaultSettings(){
		
		MQHeader mQDefaultSettings = (MQHeader) cacheByConnector(Connectors.MQPUT.toString(), null);
		
		assertTrue(mQDefaultSettings.getType().equals(Connectors.MQPUT.toString()));
		
		assertTrue(mQDefaultSettings.getValues().equals("-1, 8, 1208, , , MQSTR "));
		
	}
	
	
	@Test
	public void testConnectorSettings(){
		
		MQHeader mQConnectorSettings = (MQHeader) cacheByConnector(Connectors.MQPUT.toString(), "TEST.TRIGGER.YD1");
		
		assertTrue(mQConnectorSettings.getType().equals(Connectors.MQPUT.toString()));

		assertTrue(mQConnectorSettings.getValues(), mQConnectorSettings.getValues().equals("1, 0, 819, test, test, test"));
		
	}
}
