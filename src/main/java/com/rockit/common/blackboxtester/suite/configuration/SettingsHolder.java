package com.rockit.common.blackboxtester.suite.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rockit.common.blackboxtester.connector.settings.MQHeader;
import com.rockit.common.blackboxtester.connector.settings.Settings;

/**
 * keeps settings in cache for the runtime specific connectors like queue
 * @author rockit2lp
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
