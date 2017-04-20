package com.rockit.common.blackboxtester.connector.impl;


public class MQManager {
	
	private String mgr;
	private String host;
	private String port;
	private String channel;



	public MQManager(String mgr, String host, String port, String channel) {
		this.mgr = mgr;
		this.host=host;
		this.port=port;
		this.channel=channel;
	}
	
	
	public String getMgr() {
		return mgr;
	}


	public String getHost() {
		return host;
	}


	public String getPort() {
		return port;
	}


	public String getChannel() {
		return channel;
	}

	

}
