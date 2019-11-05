package com.rockit.common.blackboxtester.connector;

public interface Connector {
	
	public static final String CONTENT_TYPE_XML="application/xml";
	public static final String CONTENT_TYPE_JSON="application/json";
	public static final String CONTENT_TYPE_CVS="application/csv";
	
	
	
	void proceed();

	String getId();

	String getType();
	
	String getName();
}
