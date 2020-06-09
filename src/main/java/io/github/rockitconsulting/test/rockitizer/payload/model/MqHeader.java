package io.github.rockitconsulting.test.rockitizer.payload.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class MqHeader {

	private String correlId = new String(new byte[24]);

	private int expiry = -1;

	private String msgId = new String(new byte[24]);

	private int msgType = 8;

	private int codedCharSetId = 1208;

	private String replyToQ = "";

	private String replyToQMgr = "";

	private String msgFormat = "MQSTR";

	private List<String> rfh2Header;

	public int getExpiry() {
		return expiry;
	}

	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}

	public int getMsgType() {
		return msgType;
	}

	@XmlElement
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getCodedCharSetId() {
		return codedCharSetId;
	}

	@XmlElement
	public void setCodedCharSetId(int codedCharSetId) {
		this.codedCharSetId = codedCharSetId;
	}

	public String getReplyToQ() {
		return replyToQ;
	}

	@XmlElement
	public void setReplyToQ(String replyToQ) {
		this.replyToQ = replyToQ;
	}

	public String getReplyToQMgr() {
		return replyToQMgr;
	}

	public void setReplyToQMgr(String replyToQMgr) {
		this.replyToQMgr = replyToQMgr;
	}

	public String getMsgFormat() {
		return msgFormat;
	}

	@XmlElement
	public void setMsgFormat(String msgFormat) {
		this.msgFormat = msgFormat;
	}

	public String getCorrelId() {
		return correlId;
	}

	@XmlElement
	public void setCorrelId(String correlId) {
		this.correlId = correlId;
	}

	public String getMsgId() {
		return msgId;
	}

	@XmlElement
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public List<String> getRfh2Header() {
		return rfh2Header;
	}

	@XmlElementWrapper(name = "rfh2Headers")
	@XmlElement(name = "rfh2Header")
	public void setRfh2Header(List<String> rfh2Header) {
		this.rfh2Header = rfh2Header;
	}

}
