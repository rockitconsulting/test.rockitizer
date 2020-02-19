package io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources;

import io.github.rockitconsulting.test.rockitizer.validation.Validatable;
import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

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
	public Map<Context, List<Message>> validate() {
		return ValidationUtils.checkValid(getContext(), (Map<String, String>) ImmutableMap.of(
				"user", user, 
				"password", password,
				"url", url,
				"id", id
				));
	}

	
	
	@Override
	public Context getContext() {
		return new Context(getId());
	}
	



	
	
	
}
