package io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors;

import io.github.rockitconsulting.test.rockitizer.validation.Validatable;

import java.beans.Transient;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Properties with '@' are mandatory and filled with defaults
 * Properties with 'null' Values are optional
 * //TODO Alex documentation and cli 
 *
 */
public abstract class BaseConnector implements Validatable {
	
	
	public enum Types {
		DBGET, DBPUT,
		FILEDEL,FILEPUT,FILEGET,
		HTTP, 
		SCPPUT,
		MQGET,MQPUT
	}

	
	private String id;
	
	private String context;
	
	public BaseConnector () {
	}
	
	public BaseConnector (File location) {
		this.id = location.getName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isValid() {
		return validate().size()==0;
	}
	
	/**
	 * Custom validation logic
	 * @return
	 */
	public abstract Map<String, List<String>> validate();
	

	public String getValidationContext() {
		return getContext()!=null? getContext() + getId() : getId();
	}

	@Transient
	public String getContext() {
		return context;
	}

	@Transient
	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public abstract String toString();
	
	

	
	
}
