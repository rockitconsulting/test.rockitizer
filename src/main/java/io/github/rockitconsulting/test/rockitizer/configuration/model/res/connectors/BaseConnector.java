package io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors;

import io.github.rockitconsulting.test.rockitizer.validation.Validatable;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.beans.Transient;
import java.io.File;
import java.util.List;
import java.util.Map;

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
		context =  new Context.Builder().withConnector(location);
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
		if (context == null ) {
			context =  new Context.Builder().withId(getId());;
		}
		return context;
	}

	@Transient
	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public abstract String toString();
	
	

	
	
}
