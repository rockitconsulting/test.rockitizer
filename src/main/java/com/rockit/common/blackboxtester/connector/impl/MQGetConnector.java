package com.rockit.common.blackboxtester.connector.impl;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;
import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class MQGetConnector extends MQAccessor implements ReadConnector {

	private Object message;
	private String name;

	public MQGetConnector(String qManager, String qName, int port, String channelname, String hostname, String name) {
		super(qManager, qName, port, channelname, hostname);
		this.name = name;
	}

	
	public MQGetConnector(String qName, String name) {
		super(configuration().getString(Constants.MQMANAGER_NAME_KEY), qName,
				configuration().getInt(Constants.MQMANAGER_PORT_KEY),
				configuration().getString(Constants.MQMANAGER_CHANNEL_KEY),
				configuration().getString(Constants.MQMANAGER_HOST_KEY));
		this.name = name;
	}

	@Override
	public void proceed() {
		message = get();

		if (null != message) {
			LOGGER.info("MQConnectorOut [" + this.getQName() + "] get  message size " + ((String) message).length());
		}

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getResponse() {
		return (String) message;
	}

	@Override
	public void setReponse(String response) {
		LOGGER.error("set method is not allowed");
		throw new ConnectorException(new RuntimeException("set method is not allowed"));
	}

	public String getId() {
		return  getType()+getQName() ;
	}
	
	public String getType() {
		return Constants.Connectors.MQGET.toString();
	}
	
}
