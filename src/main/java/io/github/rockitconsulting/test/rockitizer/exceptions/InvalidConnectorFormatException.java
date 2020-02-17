package io.github.rockitconsulting.test.rockitizer.exceptions;


public class InvalidConnectorFormatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 984539678680338855L;

	
	final static String connectorConvention = "HTTP.<id>, FILEDEL.<id>, FILEPUT.<id>, FILEGET.<id>, MQGET.<id>, MQPUT.<id>, DBPUT.<id>, DBGET.<id>, SCPPUT.<id>"; 
	
	public InvalidConnectorFormatException (String connectorId) {
		super("Invalid connector format: " + connectorId + ". Following naming convention for the folders shall be followed:  "+ connectorConvention + " ");

		}
	
}
