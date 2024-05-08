package io.github.rockitconsulting.test.rockitizer.payload.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "header", "body", "body64enc" })
public class MqPayload {

	MqHeader header = new MqHeader();

	String body;
	
	String body64enc;

	public MqHeader getHeader() {
		return header;
	}

	@XmlElement
	public void setHeader(MqHeader header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	@XmlElement
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getBody64enc() {
		return body64enc;
	}

	@XmlElement
	public void setBody64enc(String body64enc) {
		this.body64enc = body64enc;
	}


}
