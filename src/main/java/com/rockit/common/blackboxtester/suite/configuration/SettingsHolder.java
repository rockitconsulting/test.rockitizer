package com.rockit.common.blackboxtester.suite.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rockit.common.blackboxtester.connector.settings.MQHeader;
import com.rockit.common.blackboxtester.connector.settings.Settings;

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

public class SettingsHolder {

	private SettingsHolder() {
	}

	private static Map<String, Settings> cache = new ConcurrentHashMap<>();

	static {
		cache.put(Constants.DEFAULT_MQGET, new MQHeader());
		cache.put(Constants.DEFAULT_MQPUT, new MQHeader());
	}

	public static Map<String, Settings> cache() {
		return cache;
	}

	public static Settings cacheByConnector(String type, String name) {
		return cache.get(type + name) !=null ? cache.get(type + name): cache.get(Constants.DEFAULT_CONNECTOR_NAME + type);
	}
}
