package com.rockit.common.blackboxtester.connector;

import java.io.File;

public interface WriteConnector extends Connector {

	void setRequest(File file);

	void setRequest(String request);

}
