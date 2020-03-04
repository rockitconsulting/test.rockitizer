package com.rockit.common.blackboxtester.suite.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

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
import com.rockit.common.blackboxtester.connector.impl.MQGetConnector;
import com.rockit.common.blackboxtester.connector.impl.MQPutConnector;
import com.rockit.common.blackboxtester.connector.impl.SCPPutConnector;
import com.rockit.common.blackboxtester.exceptions.GenericException;

/**
*  Test.Rockitizer - API regression testing framework 
*   Copyright (C) 2020  rockit.consulting GmbH
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see http://www.gnu.org/licenses/.
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
		
		String type =  ConfigUtils.connectorTypeFromConnectorId(name);
		
		
		switch (type) {

			case "MQPUT":
				return  ImmutableList.of( (Connector)new MQPutConnector(name)) ;
				
			case "MQGET":
				return  ImmutableList.of( (Connector)new MQGetConnector( name)) ;
	
			case "DBPUT":
				return  ImmutableList.of( (Connector)new DBPutConnector(name)) ;
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
				throw new GenericException("Unsuppored connector for [" + name + "]");
		}
	}
}
