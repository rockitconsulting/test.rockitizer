package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.impl.HTTPConnector;
import com.rockit.common.blackboxtester.connector.impl.http.ResponseHeader;

/**
 * Test.Rockitizer - API regression testing framework Copyright (C) 2020
 * rockit.consulting GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http:www.gnu.org/licenses/.
 *
 */

public class HTTPConnectorWithJSONTest {

	public static final Logger LOGGER = Logger.getLogger(HTTPConnectorWithJSONTest.class.getName());

	Map<String, List<String>> headerMap;
	HTTPConnector httpConnector;

	@Before
	public void setUp() throws URISyntaxException {
		TestObjectFactory.resetConfigurationToDefault();
		httpConnector = new HTTPConnector("HTTP.JADDBOOK");

		headerMap = new HashMap<String, List<String>>();
		headerMap.put("null", Arrays.asList("HTTP/1.1 200 OK"));
		headerMap.put("Server", Arrays.asList("Apache-Coyote/1.1"));
		headerMap.put("Content-Length", Arrays.asList("5047"));
		headerMap.put("Date", Arrays.asList("Thu, 22 Oct 2020 11:05:45 GMT"));
		headerMap.put("Content-Type", Arrays.asList("application/json"));
	}

	@Test
	public void testType() {
		assertEquals("HTTP.", httpConnector.getType());
	}

	@Test
	public void testId() {
		assertEquals("HTTP.JADDBOOK", httpConnector.getId());
	}

	@Test
	public void validJSONObjectTest() throws URISyntaxException, IOException {
		Path payloadPath = Paths.get(ClassLoader.getSystemResource("connectors/http/HttpConnector/testinginput.json").toURI());
		File payloadFile = new File(payloadPath.toString());
		assertTrue(payloadFile.exists());

		File jsonObjectFile = new File(payloadPath.toString());
		assertTrue(jsonObjectFile.exists());

		String jsonObjectString = FileUtils.readFileToString(jsonObjectFile);
		assertTrue(httpConnector.isJSONStringValid(jsonObjectString));
	}

	@Test
	public void invalidJSONObjectTest() throws URISyntaxException, IOException {
		Path jsonObjectFilePath = Paths.get(ClassLoader.getSystemResource("connectors/http/HttpConnector/JSONObject.json").toURI());
		File jsonObjectFile = new File(jsonObjectFilePath.toString());
		assertTrue(jsonObjectFile.exists());

		String jsonObjectString = FileUtils.readFileToString(jsonObjectFile);
		assertFalse(httpConnector.isJSONStringValid(jsonObjectString));
	}

	@Test
	public void invalidJSONArrayTest() throws URISyntaxException, IOException {
		Path jsonArrayFilePath = Paths.get(ClassLoader.getSystemResource("connectors/http/HttpConnector/JSONArray.json").toURI());
		File jsonArrayFile = new File(jsonArrayFilePath.toString());
		assertTrue(jsonArrayFile.exists());

		String jsonArrayString = FileUtils.readFileToString(jsonArrayFile);
		assertFalse(httpConnector.isJSONStringValid(jsonArrayString));
	}

	@Test
	public void buildJSONResponseStringTest() throws URISyntaxException, IOException {
		Path payloadPath = Paths.get(ClassLoader.getSystemResource("connectors/http/HttpConnector/testinginput.json").toURI());
		File payloadFile = new File(payloadPath.toString());
		assertTrue(payloadFile.exists());

		String result = FileUtils.readFileToString(payloadFile);

		ResponseHeader newResonseHeader = new ResponseHeader(headerMap);
		JSONObject responseHeader = newResonseHeader.getResponsHeader();

		String responseString = httpConnector.buildJSONResponseString(responseHeader.toString(), result);

		assertTrue(responseString.contains(responseHeader.toString()));
		assertTrue(responseString.contains(result));

	}
}
