package io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

public class FileConnector extends BaseConnector  {

	public enum Types {
		FILEDEL, FILEPUT, FILEGET
	}
	
	private Types type;	
	private String path = "@path@";


	
	public FileConnector() {
		super();
	}
	
	public FileConnector(File location) {
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

	
	@Override
	public Map<Context, List<Message>> validate() {
		return ValidationUtils.checkFieldsValid(getContext(), (Map<String, String>) ImmutableMap.of(
				"path", path 
				));
	}
	
	
	@Override
	public String toString() {
		return type + ":{" +  (Strings.isNullOrEmpty(getId())?"": "id=" + getId() + ", ") +  (Strings.isNullOrEmpty(path)?"": "path=" + path ) 
			+ "}";
	}


	
	

}
