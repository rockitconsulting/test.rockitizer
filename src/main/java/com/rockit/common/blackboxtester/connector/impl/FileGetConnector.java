package com.rockit.common.blackboxtester.connector.impl;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;
import com.rockit.common.blackboxtester.util.FileUtils;

/**
 * @author rockit2lp
 *
 */
public class FileGetConnector implements ReadConnector {
	public static final Logger LOGGER = Logger.getLogger(FileGetConnector.class.getName());

	private String name;


	private String srcPath;

	private String response;




	/**
	 * @param name  - FILEPut and key (connectorFolder)
	 */
	public FileGetConnector(String name){
		this.name = name;
		this.srcPath = configuration().getString(this.name + Constants.FILE_PATH_KEY);
	}
	



	@Override
	public void proceed() {
		try {
			File f = new File(srcPath);
			if(f.isDirectory()) {
				if( FileUtils.listFiles(f).iterator().hasNext() ) {
					f = FileUtils.listFiles(f).iterator().next();
				}
			}
			response = Files.toString(f, Charset.defaultCharset());
			
		} catch (Exception e) {
			LOGGER.error("[Connector:"+getName()+"] \t Connector error: " + getType(), e);
		} finally {
		}

	}

	/**
	 * @param srcPath - enabling junit
	 */
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getType() {
		return  Connectors.FILEGET.toString();
	}

	@Override
	public String getId() {
		return getName();
	}

	public String getName() {
		return name;
	}


	@Override
	public String getResponse() {
		return response;
	}


	@Override
	public void setReponse(String response) {
		LOGGER.error("set method is not allowed");
		throw new ConnectorException(new RuntimeException("set method is not allowed"));
	}

}
