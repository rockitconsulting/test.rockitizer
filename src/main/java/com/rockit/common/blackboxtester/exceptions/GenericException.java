package com.rockit.common.blackboxtester.exceptions;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;	

	public GenericException(Exception e) {
		throw this;
	}

	public GenericException(String msg) {
		throw this;
	}
	
	
	
}
