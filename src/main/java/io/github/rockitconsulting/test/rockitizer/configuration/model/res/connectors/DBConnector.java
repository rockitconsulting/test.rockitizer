package io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors;

import io.github.rockitconsulting.test.rockitizer.configuration.model.validation.ValidationUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

public class DBConnector extends BaseConnector {

	public enum Types {
		DBGET, DBPUT
	}

	private String query = null;

	private Types type;

	String dsRefId = "defaultDB";

	public DBConnector() {
		super();
	}

	public DBConnector(File location) {
		super(location);
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
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
	public Map<String, List<String>> validate() {
		
		if(type == Types.DBGET) {
			return ValidationUtils.checkValid(getValidationContext(), (Map<String, String>) ImmutableMap.of(
					"query", query, 
					"dsRefId", dsRefId
					));
		} else {
			return ValidationUtils.checkValid(getValidationContext(), (Map<String, String>) ImmutableMap.of(
					"dsRefId", dsRefId
					));
			
		}
	}

}
