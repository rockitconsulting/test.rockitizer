package com.rockit.common.blackboxtester.connector;

public interface ReadConnector extends Connector {

	String getResponse();

	void setReponse(String response);
}
