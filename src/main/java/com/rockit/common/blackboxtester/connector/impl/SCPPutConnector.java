package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.SCPConnector;

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
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;
import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;

public class SCPPutConnector implements WriteConnector {
	public static final Logger LOGGER = Logger.getLogger(SCPPutConnector.class.getName());

	private String id;

	private Session session;
	private ChannelSftp channel;

	private String destPath;

	private File contentFile;

	private String host;

	private String user;

	private String password;


	/**
	 * @param id "SCPPUT.CONN1" - connector and key 
	 */
	public SCPPutConnector(String id){
		this.id = id;
		SCPConnector cfg = (SCPConnector) configuration().getConnectorById(id);
		this.host = cfg.getHost();
		this.user = cfg.getUser();
		this.password = cfg.getPassword(); 
		this.destPath = cfg.getPath();
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
			TestProtocol.write("[Connector:"+getId()+"] \t Copying " + contentFile.getName() + " to " + destPath);
			channel.put(new FileInputStream(contentFile), targetFilename, ChannelSftp.OVERWRITE);

			if (targetFilename.endsWith(".trans")) {
				String targetFilenameRenamed = targetFilename.replaceAll(".trans", ".csv");
				TestProtocol.write("[Connector:"+getId()+"] \t Renaming File  " + targetFilename + " to " + targetFilenameRenamed +" in " + destPath);
				channel.rename(targetFilename, destPath + "/" + targetFilenameRenamed);
			}


		} catch (Exception e) {
			LOGGER.error("[Connector:"+getId()+"] \t Connector error: " + getType(), e);
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
		return id;
	}

	@Override
	public void setRequest(File requestFile)  {
		contentFile = PayloadReplacer.interpolate(requestFile);
	}

	@Override
	public void setRequest(String request)  {
		LOGGER.error("set method is not allowed");
		throw new ConnectorException(new RuntimeException("[Connector:"+getId()+"] \t Set method is not allowed"));

	}

}
