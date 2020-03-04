package com.rockit.common.blackboxtester.connector.impl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.impl.http.ResponseHeader;

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
