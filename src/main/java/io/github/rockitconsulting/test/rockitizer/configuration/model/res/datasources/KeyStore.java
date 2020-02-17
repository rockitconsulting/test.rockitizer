package io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources;

import io.github.rockitconsulting.test.rockitizer.configuration.model.validation.Validatable;
import io.github.rockitconsulting.test.rockitizer.configuration.model.validation.ValidationUtils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class KeyStore implements Validatable {

	
	public enum Type {
		TrustStore, KeyStore
	}

	
	String id = "defaultKS";
	String path = "@c:\\temp\\myks.jks@";
	Type type = Type.TrustStore;

	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "KeyStore [id=" + id + ", path=" + path + ", type=" + type + "]";
	}
	
	
	@Override
	public String getValidationContext() {
		return "KeyStore with ID:" + id;
	}
	
	@Override
	public boolean isValid() {
		return validate().size()==0;
	}
	
	
	@Override
	public Map<String, List<String>> validate() {
		return ValidationUtils.checkValid(getValidationContext(), (Map<String, String>) ImmutableMap.of(
				"path", path, 
				"id", id
				));
	}
	

}
