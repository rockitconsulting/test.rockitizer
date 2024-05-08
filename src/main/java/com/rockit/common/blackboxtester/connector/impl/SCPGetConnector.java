package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.SCPConnectorCfg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.google.common.io.Files;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.rockit.common.blackboxtester.connector.ConnectorResponse;
import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;

public class SCPGetConnector implements ReadConnector {

    private String id;
    private Session session;
    private ChannelSftp channel;
    private String srcPath;
    private String localPath;
    private File outputFile;
    private String host;
    private String user;
    private String password;
    private ConnectorResponse response; 

    public SCPGetConnector(String id) {
        this.id = id;
        SCPConnectorCfg cfg = (SCPConnectorCfg) configuration().getConnectorById(id);
        this.host = cfg.getHost();
        this.user = cfg.getUser();
        this.password = cfg.getPassword();
        this.srcPath = cfg.getPath();
        // localPath needs to be defined or passed in some way, not covered by SCPConnectorCfg
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
            throw new ConnectorException("Cannot establish connection to " + host, e);
        }
    }


    @Override
    public void proceed() {
    	String filename = srcPath.substring(srcPath.lastIndexOf('/') + 1);
    	outputFile = new File(localPath + File.separator + filename);
        try {
            connect(host, user, password);
            localPath = localPath != null ? localPath : "."; // Default to current directory if localPath not set
            outputFile = new File(localPath + File.separator + filename);
            // Check if the file exists before attempting to get it
            if (isFileExists(channel, srcPath)) {
                try (OutputStream os = new FileOutputStream(outputFile)) {
                    channel.get(srcPath, os);
                }
                channel.rm(srcPath);
                this.response = new ConnectorResponse(outputFile.getName(), Files.asCharSource(outputFile, Charset.defaultCharset()).read());
            } 
        } catch (Exception e) {
            throw new ConnectorException("[Connector:" + getId() + "] \t Connector error: " + getType(), e);
        } finally {
        	if(outputFile.exists()) {
        		//cleanup file, as it will be sent as response string
        		outputFile.delete();
        	}
        	if (channel != null) {
                channel.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    public String getType() {
        return Connectors.SCPGET.toString();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
	public ConnectorResponse getResponse() {
		return response;
	}

	@Override
	public void setReponse(ConnectorResponse response) {
		throw new ConnectorException("[Connector:" + getId() + "] \t Set method is not allowed");
		
	}

	// Method to check if the file exists on the remote server
	private boolean isFileExists(ChannelSftp channel, String filePath) {
	    try {
	        // Try to retrieve attributes of the file, if it exists
	        channel.lstat(filePath);
	        return true;
	    } catch (Exception e) {
	        // File does not exist or error occurred while retrieving attributes
	        return false;
	    }
	}   

}
