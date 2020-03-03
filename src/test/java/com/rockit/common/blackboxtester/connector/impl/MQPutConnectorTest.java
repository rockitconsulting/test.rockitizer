package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MQPutConnectorTest {

	@Test
	public void testMQPutConnectorMapping() {
		MQPutConnector mqc = new MQPutConnector("MQPUT.IN.MQ2MQ");
		assertEquals("MQPUT.", mqc.getType());
		assertEquals("MQPUT.IN.MQ2MQ", mqc.getId());
		assertEquals("MQ.IN.MQ2MQ", mqc.getQName());
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
