package io.github.rockitconsulting.test.rockitizer.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 984539678680338855L;

	// final static String allowedConnectorsMessage =
	// "HTTP, FILEDEL, FILEPUT, FILEGET, MQGET, MQPUT , DBPUT , DBGET , SCPPUT ";

	public ResourceNotFoundException(String connectorId) {
		super(" Connector/DataSource configuration not found for id : " + connectorId);
	}
}
