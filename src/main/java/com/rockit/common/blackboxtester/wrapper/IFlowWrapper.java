package com.rockit.common.blackboxtester.wrapper;

import com.rockit.common.blackboxtester.connector.Connector;
import com.rockit.common.blackboxtester.connector.impl.mq.MQManager;


public interface IFlowWrapper {
	
	abstract public Connector[] getInConnectors();
	
	abstract public Connector[] getOutConnectors();

	abstract public MQManager[] getMQManagers();
}
