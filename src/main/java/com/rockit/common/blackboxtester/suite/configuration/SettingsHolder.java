package com.rockit.common.blackboxtester.suite.configuration;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.rockit.common.blackboxtester.connector.settings.MQHeader;
import com.rockit.common.blackboxtester.connector.settings.Settings;

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

	public static Settings cacheByConnector(String type, String connector) {
		String connectorName = connector;
		String env = configuration().getString(Constants.ENV_IDX_KEY);

		if (null != connectorName) {
			connectorName = connectorName.replaceAll(env, Constants.ENV);
		}
		return cache.get(type + connectorName) !=null ? cache.get(type + connectorName): cache.get(Constants.DEFAULT_CONNECTOR_NAME + type);
	}
}
