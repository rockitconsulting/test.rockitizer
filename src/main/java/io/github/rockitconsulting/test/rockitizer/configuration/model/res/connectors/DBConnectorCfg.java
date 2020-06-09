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

public class DBConnectorCfg extends BaseConnector {

	public enum Types {
		DBGET, DBPUT
	}

	private String query = null;

	private Types type;

	String dsRefId = "defaultDB";

	public DBConnectorCfg() {
		super();
	}

	public DBConnectorCfg(File location) {
		super(location);
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
		if (Types.DBGET == type && query == null) {
			query = "@query@";
		}

	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getDsRefId() {
		return dsRefId;
	}

	public void setDsRefId(String dsRefId) {
		this.dsRefId = dsRefId;
	}

	@Override
	public String toString() {
		return type + ":{" + (Strings.isNullOrEmpty(getId()) ? "" : "id=" + getId() + ", ") + (Strings.isNullOrEmpty(query) ? "" : "query=" + query + ", ")
				+ (Strings.isNullOrEmpty(dsRefId) ? "" : "dsRefId=" + dsRefId + ", ") + "}";
	}

	@Override
	public Map<Context, List<Message>> validate() {

		return ValidationUtils.checkFieldsValid(getContext(), getFieldsAsOrderedMap());

	}

	@Override
	public Map<String, String> getFieldsAsOrderedMap() {
		if (type == Types.DBGET) {
			return (Map<String, String>) ImmutableMap.of("id", getId(), "type", getType().toString(), "query", query == null ? "@query@" : query, "dsRefId", dsRefId);
		} else {
			return (Map<String, String>) ImmutableMap.of("id", getId(), "type", getType().toString(), "dsRefId", dsRefId);

		}
	}

}
