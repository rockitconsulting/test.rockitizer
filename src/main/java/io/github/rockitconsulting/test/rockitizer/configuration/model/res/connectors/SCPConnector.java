package io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

public class SCPConnector extends BaseConnector {

	public enum Types {
		SCPPUT
	}

	private String host = "@host@";
	private String path = "@path@";
	private String user = "@usr@";
	private String parol = "@pwd";//NOSONAR
	private Types type;

	public SCPConnector() {
		super();
	}

	public SCPConnector(File location) {
		super(location);
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return parol;
	}

	public void setPassword(String password) {
		this.parol = password;
	}

	@Override
	public String toString() {
		return type + ":{" + (Strings.isNullOrEmpty(getId()) ? "" : "id=" + getId() + ", ") + (Strings.isNullOrEmpty(path) ? "" : "path=" + path) + "}";
	}

	@Override
	public Map<Context, List<Message>> validate() {
		return ValidationUtils.checkFieldsValid(getContext(), getFieldsAsOrderedMap()

		);
	}

	@Override
	public Map<String, String> getFieldsAsOrderedMap() {
		return ImmutableMap.<String, String> builder().put("id", getId()).put("type", getType().toString()).put("host", host).put("path", path)
				.put("user", user).put("password", parol).build();

	}

}
