package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

public class MQGetConnectorTest {

	@Test
	public void testMQGetConnectorMapping() {
		MQGetConnector mqc = new MQGetConnector("MQGET.ERROR");
		assertEquals("MQGET.", mqc.getType());
		assertEquals("MQGET.ERROR", mqc.getId());
		assertEquals("MQ.ERROR", mqc.getMqConnConfig().getQueue());
		assertEquals("SYSTEM.BKR.CONFIG", mqc.getMqDataSource().getChannel());
		assertEquals("localhost", mqc.getMqDataSource().getHost());
		assertEquals("QM1", mqc.getMqDataSource().getQmgr());
		assertEquals(1414, Integer.valueOf(mqc.getMqDataSource().getPort()).intValue());
		assertEquals(0, MQAccessor.cacheQmgr.size());
		assertEquals("localhost", mqc.getMqEnv().get("hostname"));
		assertEquals("admin", mqc.getMqEnv().get("password"));
		assertEquals(1414, mqc.getMqEnv().get("port"));
		assertEquals("SYSTEM.BKR.CONFIG", mqc.getMqEnv().get("channel"));
		assertEquals(true, mqc.getMqEnv().get("Use MQCSP authentication"));
		assertEquals("admin", mqc.getMqEnv().get("userID"));

		mqc.getMqEnv().forEach((k, v) -> {
			System.out.println(k + ": " + v);
		});

		System.out.println(mqc.getMqConnConfig().getQueue());
	}

}
