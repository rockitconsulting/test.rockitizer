package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.impl.HTTPGetConnector;

public class HTTPGetConnectorJSONTest {

	public static final Logger LOGGER = Logger.getLogger(HTTPGetConnectorJSONTest.class.getName());


	@Test
	public void testJSONObject() throws IOException {
		String requestUrl  = "HTTPGET.JSON.GET";	
		String myJson =  "{\"widget\": \"center\"}";  
		String expected = "<widget>center</widget>";
			
			HTTPGetConnector myHTTPGetConnector = new HTTPGetConnector(requestUrl);
			
		try {
		
			assertEquals(expected, myHTTPGetConnector.parseJSON(myJson));
			
		} catch (JSONException je) {
			
		}

	}
	

	@Test
	public void testJSONArray() throws IOException {
		String requestUrl  = "HTTPGET.JSONARRAY.GET";	
		
		HTTPGetConnector myHTTPGetConnector = new HTTPGetConnector(requestUrl);
		String myJson =  "[{\"widget\": \"center\"}]";  
		String expected = "<array><widget>center</widget></array>";
		
	try {
		
		assertEquals(expected, myHTTPGetConnector.parseJSON(myJson));
		
		} catch (JSONException je) {
		
		}

	}

	
	@Test
	public void testNoJSON() throws IOException {
		String requestUrl  = "HTTPGET.NOJSON.GET";	
		
		HTTPGetConnector myHTTPGetConnector = new HTTPGetConnector(requestUrl);
		
	try {
			
			myHTTPGetConnector.parseJSON(requestUrl);
	
		} catch (JSONException je) {
			assertTrue(true);
		}
	}
}
