package com.rockit.common.blackboxtester.suite.configuration;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.exceptions.GenericException;

public class ConfigurationHolder extends PropertiesConfiguration {

	public static final Logger LOGGER = Logger.getLogger(ConfigurationHolder.class.getName());
 	
	private static ConfigurationHolder configuration; 
	
	private ConfigurationHolder(String confName) throws ConfigurationException {
		super(confName);
	}
	
	public static ConfigurationHolder configuration() {
		return configuration;
	}

	static {

		try {
			configuration =  new ConfigurationHolder("config.properties");
			configuration.setThrowExceptionOnMissing(true);
			configuration.setIncludesAllowed(false);
			configuration.setListDelimiter(';');

		} catch (final ConfigurationException e) {

			LOGGER.error("can not initialize configuration ", e);
			throw new GenericException(e);

		}
	}

	@Override
	public int getInt(String key) {
		return super.getInt(key.replace('@', '.'));
		
	}


	
	@Override
	public String getString(String key, String defValue) {
		return super.getString(key.replace('@', '.'), defValue);
		
	}
	
	
	@Override
	public String getString(String key) {
		return super.getString(key);
		
	}

	
	/**
	 * connector with fallback to default configuration.
	 * uses the connector name to lookup for the connector specific configuration
	 * if it not exists, it takes the default one for the connector of exists
	 * if no configuration available the NoSuchElementException is thrown if enabled
	 * 
	 * @param name
	 * @param key
	 * @return
	 */
	public String getPrefixedString(String name, String key) {
		String custom = null;
		try {
			custom= super.getString(name + '.'+ key);
		} catch (NoSuchElementException nse) {
			LOGGER.trace("getPrefixedString(" + name + '.'+ key + ") goes to fallback configuration ", nse  );
			custom = super.getString(key);
		}
		return custom;
		
	}

	/**
	 * connector with fallback to default configuration.
	 * uses the connector name to lookup for the connector specific configuration
	 * if it not exists, it takes the default one for the connector of exists
	 * if no configuration available the NoSuchElementException is thrown if enabled
	 * 
	 * @param name
	 * @param key
	 * @return
	 */
	public int getPrefixedInt(String name, String key) {
		int custom = 0;
		try {
			custom = super.getInt(name + '.'+ key);
		} catch (NoSuchElementException nse) {
			LOGGER.trace("getPrefixedInt(" + name + '.'+ key + ") goes to fallback configuration ", nse  );
			custom = super.getInt(key);
		}
		return custom;
		
	}
	
	
	
	@Override
	public List<Object> getList(String key) {
		super.getString(key);//to get Exception if not exists
		return super.getList(key);
	}
}
