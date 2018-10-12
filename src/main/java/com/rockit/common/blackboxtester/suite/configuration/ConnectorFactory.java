package com.rockit.common.blackboxtester.suite.configuration;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.rockit.common.blackboxtester.connector.Connector;
import com.rockit.common.blackboxtester.connector.impl.DBGetConnector;
import com.rockit.common.blackboxtester.connector.impl.DBPutConnector;
import com.rockit.common.blackboxtester.connector.impl.FileDelConnector;
import com.rockit.common.blackboxtester.connector.impl.FileGetConnector;
import com.rockit.common.blackboxtester.connector.impl.FilePutConnector;
import com.rockit.common.blackboxtester.connector.impl.HTTPConnector;
import com.rockit.common.blackboxtester.connector.impl.HTTPGetConnector;
import com.rockit.common.blackboxtester.connector.impl.MQGetConnector;
import com.rockit.common.blackboxtester.connector.impl.MQPutConnector;
import com.rockit.common.blackboxtester.connector.impl.SCPPutConnector;
import com.rockit.common.blackboxtester.exceptions.GenericException;

/**
 * Mapping for the folder structure to the connector implementation
 *
 */
public class ConnectorFactory {
	public static final Logger LOGGER = Logger.getLogger(ConnectorFactory.class.getName());

	private ConnectorFactory() {
		throw new IllegalAccessError("ConnectorFactory class");
	}

	public static List<Connector> connectorByFolder(String name) {

		if(name.indexOf('@')!=-1) {
			throw new RuntimeException("@ are no longer supported since version 0.6. Please use the pattern {Connector}.{Variable}");
		}
		

		String type = name.split("\\.")[0];
		
		
		switch (type) {

			case "MQPUT":
				return  ImmutableList.of( (Connector)new MQPutConnector(name)) ;
				
			case "MQGET":
				return  ImmutableList.of( (Connector)new MQGetConnector( name)) ;
	
			case "DBPUT":
				return  ImmutableList.of( (Connector)new DBPutConnector(name)) ;
	
			case "HTTPGET":
				return  ImmutableList.of( (Connector) new HTTPGetConnector(name) ) ;
	
			case "HTTP":
				return  ImmutableList.of( (Connector) new HTTPConnector(name) ) ;
	
			case "DBGET":
				return  ImmutableList.of( (Connector)  new DBGetConnector(name) ) ;
	
			case "SCPPUT":
				return  ImmutableList.of( (Connector) new SCPPutConnector(name) ) ;
				
			case "FILEGET":
				return  ImmutableList.of( (Connector) new FileGetConnector(name) ) ;

			case "FILEPUT":
				return  ImmutableList.of( (Connector) new FilePutConnector(name) ) ;

			case "FILEDEL":
				return  ImmutableList.of( (Connector) new FileDelConnector(name) ) ;
				
	
			default:
				LOGGER.error("No Connector found for [" + name + "]");
				throw new GenericException("No Connector found for [" + name + "]");
		}
	}
}
