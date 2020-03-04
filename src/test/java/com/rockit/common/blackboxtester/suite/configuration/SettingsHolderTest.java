package com.rockit.common.blackboxtester.suite.configuration;

import static com.rockit.common.blackboxtester.suite.configuration.SettingsHolder.cache;
import static com.rockit.common.blackboxtester.suite.configuration.SettingsHolder.cacheByConnector;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.settings.MQHeader;
import com.rockit.common.blackboxtester.connector.settings.SettingsBuilder;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;

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
