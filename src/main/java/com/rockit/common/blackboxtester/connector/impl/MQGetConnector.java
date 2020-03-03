package com.rockit.common.blackboxtester.connector.impl;



import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class MQGetConnector extends MQAccessor implements ReadConnector {

	private Object message;
	private String id;

		
	public MQGetConnector( String id) {
		super(id);
		
		this.id = id;
	}

	@Override
	public void proceed() {
		message = get();

		if (null != message) {
			LOGGER.info("MQConnectorOut [" + this.getQName() + "] get  message size " + ((String) message).length());
		}

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

	@Override
	public String getId() {
		return  id ;
	}
	
	public String getType() {
		return Constants.Connectors.MQGET.toString();
	}
	
}
