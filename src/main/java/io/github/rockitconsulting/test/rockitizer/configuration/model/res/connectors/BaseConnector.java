package io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors;

import io.github.rockitconsulting.test.rockitizer.validation.Validatable;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

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
	
	private Context context;
	
	public BaseConnector () {
	}
	
	public BaseConnector (File location) {
		this.id = location.getName();
		context = new Context(location);
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
	public abstract Map<Context, List<Message>> validate();
	

	@Transient
	public Context getContext() {
		return context;
	}

	@Transient
	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public abstract String toString();
	
	

	
	
}
