package com.rockit.common.blackboxtester.exceptions;

public class FatalConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 1L;	

	public FatalConfigurationException(Exception e) {
		super(e);
		throw this;
	}

	public FatalConfigurationException(String msg) {
		super(msg);
		throw this;
	}
	
	
	
}
