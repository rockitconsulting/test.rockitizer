package io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources;

import io.github.rockitconsulting.test.rockitizer.configuration.model.validation.Validatable;
import io.github.rockitconsulting.test.rockitizer.configuration.model.validation.ValidationUtils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;



public class DBDataSource implements Validatable {
	
	String type = "DB";
	String user = "@usr@";
	String password = "@pwd@";
	String url = "@url@";
	String id = "defaultDB";

	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "DBDataSource [type=" + type + ", username=" + user
				+ ", password=" + password + ", url=" + url + ", id=" + id
				+ "]";
	}
	
	@Override
	public boolean isValid() {
		return validate().size()==0;
	}

	@Override
	public Map<String, List<String>> validate() {
		return ValidationUtils.checkValid(getValidationContext(), (Map<String, String>) ImmutableMap.of(
				"user", user, 
				"password", password,
				"url", url,
				"id", id
				));
	}

	
	
	@Override
	public String getValidationContext() {
		return "DBDataSource with ID:" + id;
	}
	



	
	
	
}
