package io.github.rockitconsulting.test.rockitizer.exceptions;

import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;

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
