package io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

/**
*  Test.Rockitizer - API regression testing framework 
*   Copyright (C) 2020  rockit.consulting GmbH
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see http://www.gnu.org/licenses/.
*
*/

public class MQConnectorCfg extends BaseConnector {

	public enum Types {
		MQGET, MQPUT
	}

	private String queue = "@defaultQueue@";
	private String dsRefId = "defaultMQ";
	private String correlationId = null;
	private String messageId = null;
	private String groupId = null;

	private Types type;

	
	public MQConnectorCfg() {
		super();
	}

	public MQConnectorCfg(File location) {
		super(location);
	}

	public MQConnectorCfg(String queue, Types type) {
		setQueue(queue);
		setType(type);
	}

	public MQConnectorCfg(String queue, String correlationId, Types type) {
		setQueue(queue);
		setCorrelationId(correlationId);
		setType(type);
	}

	
	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public String getDsRefId() {
		return dsRefId;
	}

	public void setDsRefId(String dsRefId) {
		this.dsRefId = dsRefId;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	
	@Override
	public String toString() {
		return type + ":{" + (Strings.isNullOrEmpty(getId()) ? "" : "id=" + getId() + ", ") + (Strings.isNullOrEmpty(queue) ? "" : "queue=" + queue + ", ")
				+ (Strings.isNullOrEmpty(dsRefId) ? "" : "dsRefId=" + dsRefId + ", ") + "}";
	}

	@Override
	public Map<Context, List<Message>> validate() {
		return ValidationUtils.checkFieldsValid(getContext(), getFieldsAsOrderedMap());
	}

	@Override
	public Map<String, String> getFieldsAsOrderedMap() {
		return (Map<String, String>) ImmutableMap.of("id", getId(), "type", getType().toString() , "queue", queue, "dsRefId", dsRefId);
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getGroupId() {
		return groupId;

	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public  boolean isGroupedMode() {
		
		return (null != getGroupId() && getGroupId().length() > 0);//  && !getGroupId().equals("000000000000000000000000000000000000000000000000"));
		
	}

}
