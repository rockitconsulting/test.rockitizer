package com.rockit.common.blackboxtester.exceptions;

public class ConnectorException extends RuntimeException {

	

	public ConnectorException(Exception e) {
		super(e);

		throw this;
	}

	public ConnectorException(String msg) {
		super(msg);
		throw this;
	}

	private static final long serialVersionUID = 1L;

}
