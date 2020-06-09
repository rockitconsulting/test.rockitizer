package io.github.rockitconsulting.test.rockitizer.payload.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "header", "body" })
public class MqPayload {

	MqHeader header = new MqHeader();

	String body;

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

}
