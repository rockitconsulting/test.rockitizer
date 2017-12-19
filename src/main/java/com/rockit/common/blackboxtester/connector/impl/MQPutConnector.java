package com.rockit.common.blackboxtester.connector.impl;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;

import java.io.File;

import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;
import com.rockit.common.blackboxtester.util.FileUtils;

public class MQPutConnector extends MQAccessor implements WriteConnector {

	private final String name;

//	public MQConnectorIn(String qManager, String qName, int port, String channelname, String hostname, String name) {
//		super(qManager, qName, port, channelname, hostname);
//		this.name = name;
//	}
	
	public MQPutConnector(String qName, String name) {
		
		super(  
				configuration().getString(Constants.MQMANAGER_NAME_KEY),
				qName, 
				configuration().getInt(Constants.MQMANAGER_PORT_KEY),
				configuration().getString(Constants.MQMANAGER_CHANNEL_KEY),
				configuration().getString(Constants.MQMANAGER_HOST_KEY), 
				configuration().getString(Constants.MQMANAGER_USR_KEY),
				configuration().getString(Constants.MQMANAGER_PWD_KEY)
				);

		this.name = name;
	}

	@Override
	public void proceed() {
//		LOGGER.info("Proceed MQPutConnector [" + this.getQName() + "] message size " + message.length() + " / id: " + messageId);
		
		if (null != message) {
			putMessage(message, messageId);
		}
	}

	public String getName() {
		return name;
	}
	


	@Override
	public void setRequest(File file) {
		PayloadReplacer.interpolate(file);
		this.message = FileUtils.getContents(file);

	}
	public String getId() {
		return  getType() + getQName() ;
	}
	
	public String getType() {
		return Constants.Connectors.MQPUT.toString();
	}


	@Override
	public void setRequest(String request) {

		LOGGER.error("set method is not allowed");
		throw new ConnectorException(new RuntimeException("set method is not allowed"));

	}

}
