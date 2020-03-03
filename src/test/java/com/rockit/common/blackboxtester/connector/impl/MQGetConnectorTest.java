package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MQGetConnectorTest {

	@Test
	public void testMQGetConnectorMapping() {
		MQGetConnector mqc = new MQGetConnector("MQGET.ERROR");
		assertEquals("MQGET.", mqc.getType());
		assertEquals("MQGET.ERROR", mqc.getId());
		assertEquals("MQ.ERROR", mqc.getQName());
		assertEquals("SYSTEM.BKR.CONFIG", mqc.channelname);
		assertEquals("localhost", mqc.hostname);
		assertEquals("QM1", mqc.qManager);
		assertEquals(1414, mqc.port);
		assertEquals(0, MQAccessor.cache.size());
		assertEquals("localhost", mqc.getMqEnv().get("hostname"));
		assertEquals("admin", mqc.getMqEnv().get("password"));
		assertEquals(1414, mqc.getMqEnv().get("port"));
		assertEquals("SYSTEM.BKR.CONFIG", mqc.getMqEnv().get("channel"));
		assertEquals(true, mqc.getMqEnv().get("Use MQCSP authentication"));
		assertEquals("admin", mqc.getMqEnv().get("userID"));

		mqc.getMqEnv().forEach((k, v) -> {
			System.out.println(k + ": " + v);
		});

		System.out.println(mqc.getQName());
	}

}
