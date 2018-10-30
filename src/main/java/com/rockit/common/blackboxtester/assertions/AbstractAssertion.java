package com.rockit.common.blackboxtester.assertions;

public abstract class AbstractAssertion implements Assertions{
	protected String recordPath;
	protected String replayPath;

	
	public void setRecordPath(String path) {
		recordPath = path;
		
	}

	public void setReplayPath(String path) {
		replayPath = path;
		
	}

}
