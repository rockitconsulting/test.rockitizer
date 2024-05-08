package com.rockit.common.blackboxtester.connector.impl;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;

import com.rockit.common.blackboxtester.connector.WriteConnector;
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

public class MQPutConnector extends MQAccessor implements WriteConnector {

	private final String id;
	private byte[] message;

	public MQPutConnector(String id) {
		super(id);
		this.id = id;

	}

	@Override
	public void proceed() {
		if (null != message) {
			putMessage(message);
		}
	}

	@Override
	public void setRequest(File file) {
		this.message = FileUtils.getContents(file);
		//this.message = FileUtils.getContents(PayloadReplacer.interpolate(file, configuration().getPayloadReplacements()));

	}

	public String getId() {
		return id;
	}

	public String getType() {
		return Constants.Connectors.MQPUT.toString();
	}

	@Override
	public void setRequest(String request) {
		throw new ConnectorException("[Connector:" + getId() + "] \t Set method is not allowed");

	}

}
