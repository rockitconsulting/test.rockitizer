package io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors;

import io.github.rockitconsulting.test.rockitizer.configuration.model.validation.ValidationUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

public class SCPConnector extends BaseConnector  {

	public enum Types {
		SCPPUT
	}
	
	private String host 	= "@host@";
	private String path 	= "@path@";
	private String user 	= "@usr@";
	private String password = "@pwd";
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
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return type + ":{" +  (Strings.isNullOrEmpty(getId())?"": "id=" + getId() + ", ") 
				+  (Strings.isNullOrEmpty(path)?"": "path=" + path ) 
			+ "}";
	}
	@Override
	public Map<String, List<String>> validate() {
		return ValidationUtils.checkValid (  getValidationContext(), 
				ImmutableMap.<String, String>builder()
				.put("host", host)
				.put("path", path)
				.put("user", user)
				.put("password", password)
				.build()
	
		);
	}

}
