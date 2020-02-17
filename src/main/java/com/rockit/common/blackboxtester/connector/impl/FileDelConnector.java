package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.FileConnector;

import java.io.File;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.connector.Connector;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;
import com.rockit.common.blackboxtester.util.FileUtils;

/**
 * @author rockit2lp
 *
 */
public class FileDelConnector implements Connector {
	public static final Logger LOGGER = Logger.getLogger(FileDelConnector.class.getName());

	private String name;

	private String srcPath;





	/**
	 * @param name  - FILEPut and key (connectorFolder)
	 */
	public FileDelConnector(String id){
		this.name = id;
		FileConnector cfg = (FileConnector) configuration().getConnectorById(id);
		this.srcPath = cfg.getPath();
	}
	



	@Override
	public void proceed() {
		try {
			File f = new File(srcPath);
			FileUtils.deleteFilesRecursive(f);
			
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
		return  Connectors.FILEDEL.toString();
	}

	@Override
	public String getId() {
		return getName();
	}

	public String getName() {
		return name;
	}


	
}
