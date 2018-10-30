package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;

public class ResponseHeaderTest {

	@Test
	public void getResponseHeaderTest() {
		
		List<String> myList = new ArrayList<String>();
		
		myList.add("neu");
		
		
		Map<String, List<String>> vehicles = new HashMap<String, List<String>>();
		         
		vehicles.put("BMW", myList);
		vehicles.put("Mercedes", myList);
		vehicles.put("Audi", myList);
	    vehicles.put("Ford", myList);
		
		ResponseHeader newResponseHeader = new ResponseHeader(vehicles);
		JSONObject myResponseHeader = newResponseHeader.getResponsHeader();
		assertNotNull(myResponseHeader);
		System.out.println(myResponseHeader);
	}

}
