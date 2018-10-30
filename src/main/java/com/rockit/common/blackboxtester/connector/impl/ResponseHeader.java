package com.rockit.common.blackboxtester.connector.impl;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class ResponseHeader {

	public Map<String, List<String>> map;

	public ResponseHeader(Map<String, List<String>> map) {

		this.map = map;
	}

	public JSONObject getResponsHeader() {

		JSONObject jsonObject = new JSONObject(this.map);

		return jsonObject;

	}

}