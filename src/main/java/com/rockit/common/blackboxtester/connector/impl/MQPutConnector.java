package com.rockit.common.blackboxtester.connector.impl;

import java.io.File;

import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.connector.impl.mq.MQAccessor;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;
import com.rockit.common.blackboxtester.util.FileUtils;

public class MQPutConnector extends MQAccessor implements WriteConnector {

	private final String name;
	
	public MQPutConnector( String id) {
		super(id);
		this.name = id;

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
		this.message = FileUtils.getContents(PayloadReplacer.interpolate(file));

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
