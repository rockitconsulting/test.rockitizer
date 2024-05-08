//package com.rockit.common.blackboxtester.connector;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.io.IOException;
//
//import org.apache.log4j.Logger;
//import org.json.JSONException;
//import org.junit.Test;
//
//import com.rockit.common.blackboxtester.connector.impl.HTTPConnector;
//
///**
//*  Test.Rockitizer - API regression testing framework 
//*   Copyright (C) 2020  rockit.consulting GmbH
//*
//*   This program is free software: you can redistribute it and/or modify
//*   it under the terms of the GNU General Public License as published by
//*   the Free Software Foundation, either version 3 of the License, or
//*   (at your option) any later version.
//*
//*   This program is distributed in the hope that it will be useful,
//*   but WITHOUT ANY WARRANTY; without even the implied warranty of
//*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//*   GNU General Public License for more details.
//*
//*   You should have received a copy of the GNU General Public License
//*   along with this program.  If not, see http://www.gnu.org/licenses/.
//*
//*/

//public class HTTPConnectorJSONTest {
//
//	public static final Logger LOGGER = Logger.getLogger(HTTPConnectorJSONTest.class.getName());
//
//
//	@Test
//	public void testJSONObject() throws IOException {
//		String requestUrl  = "HTTPGET.JSON.GET";	
//		String myJson =  "{\"widget\": \"center\"}";  
//		String expected = "<widget>center</widget>";
//			
//			HTTPConnector myHTTPConnector = new HTTPConnector(requestUrl);
//			
//		try {
//		
////			assertEquals(expected, myHTTPConnector.parseJSON(myJson));
//			
//		} catch (JSONException je) {
//			
//		}
//
//	}
//	
//
//	@Test
//	public void testJSONArray() throws IOException {
//		String requestUrl  = "HTTPGET.JSONARRAY.GET";	
//		
//		HTTPConnector myHTTPGetConnector = new HTTPConnector(requestUrl);
//		String myJson =  "[{\"widget\": \"center\"}]";  
//		String expected = "<array><widget>center</widget></array>";
//		
//	try {
//		
////		assertEquals(expected, myHTTPGetConnector.parseJSON(myJson));
//		
//		} catch (JSONException je) {
//		
//		}
//
//	}
//
//	
//	@Test
//	public void testNoJSON() throws IOException {
//		String requestUrl  = "HTTPGET.NOJSON.GET";	
//		
//		HTTPConnector myHTTPGetConnector = new HTTPConnector(requestUrl);
//		
//	try {
//			
//			myHTTPGetConnector.parseJSON(requestUrl);
//	
//		} catch (JSONException je) {
//			assertTrue(true);
//		}
//	}
//}
