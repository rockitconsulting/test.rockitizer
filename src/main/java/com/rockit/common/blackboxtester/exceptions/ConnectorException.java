package com.rockit.common.blackboxtester.exceptions;

public class ConnectorException extends RuntimeException {

	

	public ConnectorException(Exception e) {
		throw new RuntimeException(e);
	}

	public ConnectorException(String msg) {
		throw new RuntimeException(msg);
	}

	private static final long serialVersionUID = 1L;

}
