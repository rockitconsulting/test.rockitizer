package io.github.rockitconsulting.test.rockitizer.exceptions;

import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;

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

public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3197006470405928014L;

	public ValidationException(Map<Context, List<Message>> map) {
		if(map != null && map.size()>0) {
			Context context = map.keySet().iterator().next();
			throw new ValidationException("Validation errors for " + context + ":  " + Joiner.on(';').join(map.get(context) ) ) ;
		} else {
			throw new ValidationException("Wrong usage. Validation messages cannot be null or empty" );
		}
		
		
	}

	public ValidationException(String message) {
		super(message);
	}

	
}
