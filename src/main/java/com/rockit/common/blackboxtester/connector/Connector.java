package com.rockit.common.blackboxtester.connector;

public interface Connector {
	void proceed();

	String getId();

	String getType();
	
	String getName();
}
