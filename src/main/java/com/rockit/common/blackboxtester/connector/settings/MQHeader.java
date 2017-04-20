package com.rockit.common.blackboxtester.connector.settings;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class MQHeader implements Settings {

	private byte[] correlId = new byte[24]; 
	private int expiry = -1;
	private byte[] msgId = new byte[24];
	private int msgType = 8; 
	private int codedCharSetId = 1208;
	
	private String replyToQ = ""; 
	private String replyToQMgr = "";
	private String msgFormat = "MQSTR ";
	
	
	public String getType() {
		return Constants.Connectors.MQPUT.toString();
	}
		
	
	public MQHeader setMsgFormat(String msgFormat) {
		this.msgFormat = msgFormat;
		return this;
	}

	
	public MQHeader setCorrelId(byte[] correlId) {
		this.correlId = correlId;
		return this;
	}



	public MQHeader setExpiry(int expiry) {
		this.expiry = expiry;
		return this;
	}



	public MQHeader setMsgId(byte[] msgId) {
		this.msgId = msgId;
		return this;
	}



	public MQHeader setMsgType(int msgType) {
		this.msgType = msgType;
		return this;
	}



	public MQHeader setCodedCharSetId(int codedCharSetId) {
		this.codedCharSetId = codedCharSetId;
		return this;
	}



	public MQHeader setReplyToQ(String replyToQ) {
		this.replyToQ = replyToQ;
		return this;
	}



	public MQHeader setReplyToQMgr(String replyToQMgr) {
		this.replyToQMgr = replyToQMgr;
		return this;
	}
	
	
	public byte[] getCorrelId() {
		return correlId;
	}


	public int getExpiry() {
		return expiry;
	}


	public byte[] getMsgId() {
		return msgId;
	}


	public int getMsgType() {
		return msgType;
	}


	public int getCodedCharSetId() {
		return codedCharSetId;
	}


	public String getReplyToQ() {
		return replyToQ;
	}


	public String getReplyToQMgr() {
		return replyToQMgr;
	}


	public String getMsgFormat() {
		return msgFormat;
	}


	@Override
	public String toString() {
		return "MQHeader [correlId=" + new String(correlId).trim() + ", expiry=" + expiry + ", msgId=" + new String(msgId).trim() + ", msgType=" + msgType
				+ ", codedCharSetId=" + codedCharSetId + ", replyToQ=" + replyToQ + ", replyToQMgr=" + replyToQMgr
				+ ", msgFormat=" + msgFormat + "]";
	}


	public String getValues() {
		return  expiry + ", " + msgType
				+ ", " + codedCharSetId + ", " + replyToQ + ", " + replyToQMgr
				+ ", " + msgFormat;
	}
}
