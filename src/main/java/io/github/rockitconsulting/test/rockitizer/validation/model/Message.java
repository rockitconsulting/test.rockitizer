package io.github.rockitconsulting.test.rockitizer.validation.model;

public class Message {
	
	public enum LEVEL {INFO, WARN, ERROR};
	
	private String message;
	
	private LEVEL level;
	
	
	public Message (LEVEL l, String m) {
		message = m;
		level = l;
		
	}


	public String getMessage() {
		return message;
	}


	public LEVEL getLevel() {
		return level;
	}


	@Override
	public String toString() {
		return "Message [message=" + message + ", level=" + level + "]";
	}
	

}
