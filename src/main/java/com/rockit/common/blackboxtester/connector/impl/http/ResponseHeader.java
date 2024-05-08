package com.rockit.common.blackboxtester.connector.impl.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

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
 * this program. If not, see http://www.gnu.org/licenses/.
 *
 */

public class ResponseHeader {

	private Header[] headers;

	public ResponseHeader(Header[] allHeaders) {
		this.headers = allHeaders;
	}

	public JSONObject getResponseHeader() {

		Map<String, String> map = headersArrayToMap(headers);
		return new JSONObject(map);

	}

	private static Map<String, String> headersArrayToMap(Header[] headers) {
		Map<String, String> headerMap = new HashMap<>();

		for (Header header : headers) {
			headerMap.put(header.getName(), header.getValue());

		}
		return headerMap;
	}

}