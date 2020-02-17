package io.github.rockitconsulting.test.rockitizer.configuration.model.tc;

import java.io.File;

public class Payload {
	
	private String fileName;
	
	public Payload() {}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Payload (File location) {
		this.fileName = location.getName();
	}

	
}
