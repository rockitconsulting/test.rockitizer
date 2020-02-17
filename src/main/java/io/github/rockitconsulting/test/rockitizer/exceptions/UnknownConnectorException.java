package io.github.rockitconsulting.test.rockitizer.exceptions;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;


public class UnknownConnectorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 984539678680338855L;

	final static String allowedConnectorsMessage = "HTTP, FILEDEL, FILEPUT, FILEGET, MQGET, MQPUT , DBPUT , DBGET , SCPPUT "; 
	
	public UnknownConnectorException (String connectorId) {
		super("Invalid connector : " + ConfigUtils.connectorTypeFromConnectorId(connectorId) + " in conRefId " + connectorId +
					" Following connector folders are allowed:  "+ allowedConnectorsMessage + " ");
		}
}
