package com.rockit.common.blackboxtester.connector.impl;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;
import static com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;
import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;

public class SCPPutConnector implements WriteConnector {
	public static final Logger LOGGER = Logger.getLogger(SCPPutConnector.class.getName());

	private String name;

	private Session session;
	private ChannelSftp channel;

	private String destPath;

	private File contentFile;

	private String host;

	private String user;

	private String password;


	/**
	 * @param name "SCPPUT@CONN1" - connector and key 
	 */
	public SCPPutConnector(String name){
		this.name = name;
		this.host = configuration().getString(this.name + Constants.SCP_HOST_KEY);
		this.user = configuration().getString(this.name + Constants.SCP_USR_KEY);
		this.password = configuration().getString(this.name + Constants.SCP_PWD_KEY); 
		this.destPath = configuration().getString(this.name + Constants.SCP_DEST_PATH_KEY);
	}
	
	public void connect(final String host, final String user, final String password) {

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");

		JSch jsch = new JSch();

		try {
			
			session = jsch.getSession(user, host, Constants.SCP_PORT);
			
			session.setConfig(config);
			session.setPassword(password);

			session.connect();
			channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			
		} catch (JSchException e) {
			
			LOGGER.error("Can not establish connection to " + host , e);
			throw new ConnectorException(e);

		}
				
		
	}

	public boolean copy() {
		proceed();
		return true;
	}

	@Override
	public void proceed() {
		try {
			connect(host, user, password);

			String targetFilename = contentFile.getName();
			channel.cd(destPath);
			TestProtocol.write("[Connector:"+getName()+"] \t Copying " + contentFile.getName() + " to " + destPath);
			channel.put(new FileInputStream(contentFile), targetFilename, ChannelSftp.OVERWRITE);

			if (targetFilename.endsWith(".trans")) {
				String targetFilenameRenamed = targetFilename.replaceAll(".trans", ".csv");
				TestProtocol.write("[Connector:"+getName()+"] \t Renaming File  " + targetFilename + " to " + targetFilenameRenamed +" in " + destPath);
				channel.rename(targetFilename, destPath + "/" + targetFilenameRenamed);
			}


		} catch (Exception e) {
			LOGGER.error("[Connector:"+getName()+"] \t Connector error: " + getType(), e);
		} finally {
			channel.exit();
			session.disconnect();
		}

	}

	public String getType() {
		return  Connectors.SCPPUT.toString();
	}

	@Override
	public String getId() {
		return getName();
	}

	public String getName() {
		return name;
	}

	@Override
	public void setRequest(File requestFile)  {
		contentFile = requestFile;

	}

	@Override
	public void setRequest(String request)  {
		LOGGER.error("set method is not allowed");
		throw new ConnectorException(new RuntimeException("[Connector:"+getName()+"] \t Set method is not allowed"));

	}

}
