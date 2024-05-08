package com.rockit.common.blackboxtester.connector.impl;

import com.rockit.common.blackboxtester.connector.ConnectorResponse;
import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

/**
 * Test.Rockitizer - API regression testing framework Copyright (C) 2020
 * rockit.consulting GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 *
 */

public class MQGetConnector extends MQAccessor implements ReadConnector {

	private Object message;
	private String id;

	public MQGetConnector(String id) {
		super(id);

		this.id = id;
	}

	@Override
	public void proceed() {
		message = get();

		if (null != message) {
			LOGGER.debug("MQConnectorOut [" + this.getMqConnConfig().getQueue() + "] get  message size " + ((String) message).length());
		}

	}

	@Override
	public ConnectorResponse getResponse() {
		ConnectorResponse response = null;
		String message =  (String) this.message;
		if(message != null) {
			response =  new ConnectorResponse("message",  (String) message );
		}
		
		return response;
		
		
	}

	@Override
	public void setReponse(ConnectorResponse response) {
		throw new ConnectorException("[Connector:" + getId() + "] \t Set method is not allowed");
	}

	@Override
	public String getId() {
		return id;
	}

	public String getType() {
		return Constants.Connectors.MQGET.toString();
	}

}
