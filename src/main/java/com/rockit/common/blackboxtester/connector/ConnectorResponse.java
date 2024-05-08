package com.rockit.common.blackboxtester.connector;


public class ConnectorResponse {

	String payload;
	String filePrefix;
	String fileExt;
	

	public ConnectorResponse(String prefix,  String payload) {
	
		this.payload = payload;
		this.filePrefix = prefix;
		if( !prefix.contains(".") ) {
			discoverFileExt(payload);
		} else if( prefix.endsWith(".") ) {
			filePrefix = prefix.substring(0,prefix.length()-1);
			discoverFileExt(payload);
		} else if(prefix.contains(".")) {
			this.filePrefix = prefix.split("\\.")[0];
			this.fileExt =  "." + prefix.split("\\.")[1];
		}
	}


	private void discoverFileExt(String payload) {
		if( payload.contains("</") && payload.contains(">") ) {
			this.fileExt=".xml";
		} else if (  payload.contains("{")&&  payload.contains("}") ) {
			this.fileExt=".json";
		} else {
			this.fileExt=".txt";
		}
	}		


	public String getPayload() {
		return payload;
	}


	public void setPayload(String payload) {
		this.payload = payload;
	}


	public String getFilePrefix() {
		return filePrefix;
	}


	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}


	public String getFileExt() {
		return fileExt;
	}


	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
}