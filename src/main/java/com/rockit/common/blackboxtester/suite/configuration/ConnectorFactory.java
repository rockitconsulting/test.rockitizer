package com.rockit.common.blackboxtester.suite.configuration;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.rockit.common.blackboxtester.connector.Connector;
import com.rockit.common.blackboxtester.connector.impl.DBGetConnector;
import com.rockit.common.blackboxtester.connector.impl.DBPutConnector;
import com.rockit.common.blackboxtester.connector.impl.HTTPGetConnector;
import com.rockit.common.blackboxtester.connector.impl.MQGetConnector;
import com.rockit.common.blackboxtester.connector.impl.MQPutConnector;
import com.rockit.common.blackboxtester.connector.impl.SCPPutConnector;
import com.rockit.common.blackboxtester.exceptions.GenericException;

public class ConnectorFactory {
	public static final Logger LOGGER = Logger.getLogger(ConnectorFactory.class.getName());

	private ConnectorFactory() {
		throw new IllegalAccessError("ConnectorFactory class");
	}

	public static List<Connector> connectorByFolder(String connectorName) {

		String name = connectorNameByFolder(connectorName);

		String type = name.split("@")[0];
		
		
		switch (type) {

			case "MQPUT":
				String putQueue = connectorNameByFolder(name.split("@")[1]);
				return  ImmutableList.of( (Connector)new MQPutConnector(putQueue, name)) ;
				
			case "MQGET":
				List<Connector> mqout = new ArrayList<Connector>();
				if( "NotConfiguredProceedAsSingleQueue".equals( configuration().getString(name, "NotConfiguredProceedAsSingleQueue")  ) ) {
					mqout.add(  new MQGetConnector( connectorNameByFolder(name.split("@")[1]) , name ) );
				} else {
					for ( Object queue : configuration().getList(name) ) {
						if( String.valueOf(queue).trim().isEmpty()) { continue; }
						mqout.add(  new MQGetConnector( connectorNameByFolder( String.valueOf(queue) ) , name ) );
					 }
				}
				 
    			return mqout;
	
			case "DBPUT":
				return  ImmutableList.of( (Connector)new DBPutConnector(name)) ;
	
			case "HTTPGET":
				return  ImmutableList.of( (Connector) new HTTPGetConnector(name) ) ;
	
			case "DBGET":
				return  ImmutableList.of( (Connector)  new DBGetConnector(name) ) ;
	
			case "SCPPUT":
				return  ImmutableList.of( (Connector) new SCPPutConnector(name) ) ;
	
			default:
				LOGGER.error("No Connector found for [" + name + "]");
				throw new GenericException("No Connector found for [" + name + "]");
		}
	}

	public static String connectorNameByFolder(String name) {
		return name.replaceFirst( Constants.ENV, configuration().getString(Constants.ENV_IDX_KEY) );
	}

	public static String folderNameByConnector(String name) {
		return name.replaceFirst( configuration().getString(Constants.ENV_IDX_KEY), Constants.ENV );
	}

}
