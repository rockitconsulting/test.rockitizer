package com.rockit.common.blackboxtester.suite.configuration;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.rockit.common.blackboxtester.exceptions.FatalConfigurationException;
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
		
		try {

			PropertiesConfiguration configmq = new PropertiesConfiguration("config.mq.properties");
			
			for (String key : genericMqConfiguration () ) {
				if (configuration.containsKey(key)) {
					LOGGER.fatal(" Please remove all generic mq configuration " + Joiner.on(',').join(genericMqConfiguration()) + 
							" from the \"config.properties\" in order to get the generated \"mq.properties\" working ");
					throw new FatalConfigurationException("Please remove all generic mq configuration " + Joiner.on(',').join(genericMqConfiguration()) + 
							" from the \"config.properties\" in order to get the generated \"mq.properties\" working"); //NOSONAR
				}
			}
						
			configuration.append(configmq);
						

		} catch (final ConfigurationException e) {		//NOSONAR
			LOGGER.info("No generated mq.properties found ");
		}
	}
	
	
	public static List<String> genericMqConfiguration () {
		return ImmutableList.of(Constants.ENV_IDX_KEY, Constants.MQMANAGER_CHANNEL_KEY , Constants.MQOUT_KEY , Constants.MQMANAGER_HOST_KEY, Constants.MQMANAGER_PORT_KEY, Constants.MQMANAGER_NAME_KEY);
	}


	
	@Override
	public List<Object> getList(String key) {
		super.getString(key);//to get Exception if not exists
		return super.getList(key);
	}
}
