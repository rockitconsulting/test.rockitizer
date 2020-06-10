package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
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

public class MQGetConnectorIT {
    MQPutConnector mqPut = new MQPutConnector("MQPUT.IN.MQ2MQ");
    MQGetConnector mqGet = new MQGetConnector("MQGET.OUT.MQ2MQ");


    @Before
    public void before() {
		assertEquals("MQPUT.", mqPut.getType());
		assertEquals("SYSTEM.BKR.CONFIG", mqPut.channelname);
		assertEquals("localhost", mqPut.hostname);
		assertEquals("QM1", mqPut.qManager);
		assertEquals(1414, mqPut.port);
		//assertEquals(0, MQAccessor.cache.size());
		assertEquals("localhost", mqPut.getMqEnv().get("hostname"));
		assertEquals("admin", mqPut.getMqEnv().get("password"));
		assertEquals(1414, mqPut.getMqEnv().get("port"));
		assertEquals("SYSTEM.BKR.CONFIG", mqPut.getMqEnv().get("channel"));
		assertEquals(true, mqPut.getMqEnv().get("Use MQCSP authentication"));
		assertEquals("admin", mqPut.getMqEnv().get("userID"));


		mqPut.getMqEnv().forEach((k, v) -> {
			System.out.println(k + ": " + v);
		});

    	
    }
	@Test
	public void testMqPutMqGetWithRFH2Envelope() throws URISyntaxException, InterruptedException, IOException {
		
//		Path src = Paths.get(ClassLoader.getSystemResource("IntegrationTests/MQPutPayloadWithRFH2Header.xml").toURI());
//		File payload = new File(src.toString());
//		
//		mqPut.setRequest(payload);
//		mqPut.proceed();
//		Thread.sleep(2000);
		mqGet.proceed();
		System.out.println(mqGet.getResponse());
		
		
//		assertEquals( new String(Files.readAllBytes( src )).replace("\r", "").replace("\n", "").replaceAll(">\\s+<", "><"), 
//				mqGet.getResponse().replace("\r", "").replace("\n", "").replaceAll(">\\s+<", "><") 
//		);
	}

	@Test
	public void testMqPutMqGetWithMQMDEnvelope() throws URISyntaxException, InterruptedException, IOException {
		
		Path src = Paths.get(ClassLoader.getSystemResource("IntegrationTests/MQPutPayloadWithMQMDHeader.xml").toURI());
		File payload = new File(src.toString());
		
		mqPut.setRequest(payload);
		mqPut.proceed();
		Thread.sleep(2000);
		mqGet.proceed();
		assertEquals( new String(Files.readAllBytes( src )).replace("\r", "").replace("\n", "").replaceAll(">\\s+<", "><"), 
				mqGet.getResponse().replace("\r", "").replace("\n", "").replaceAll(">\\s+<", "><") 
		);
	}
	
	
	@Test
	public void testMqPutMqGetRaw() throws URISyntaxException, IOException, InterruptedException {
		Path src = Paths.get(ClassLoader.getSystemResource("IntegrationTests/MQPutPayload.xml").toURI());
		File payload = new File(src.toString());
		
		mqPut.setRequest(payload);
		mqPut.proceed();
		Thread.sleep(2000);
		mqGet.proceed();
		
		String msg = mqGet.getResponse().replace("\r", "").replace("\n", "").replaceAll(">\\s+<", "><");
		
		String res = msg.substring(msg.indexOf("<body>")+6, msg.length()-19);
		
		
		System.out.println(msg);
		System.out.println("---------------------------");
		System.out.println(res);
		
		
		assertEquals( new String(Files.readAllBytes( src )).replace("\r", "").replace("\n", "").replaceAll(">\\s+<", "><"), 
				res  
		);
	
	}
}
