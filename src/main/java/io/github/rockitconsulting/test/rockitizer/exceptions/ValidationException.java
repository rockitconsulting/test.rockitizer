package io.github.rockitconsulting.test.rockitizer.exceptions;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3197006470405928014L;

	public ValidationException(Map<String, List<String>> errors) {
		if(errors != null && errors.size()>0) {
			String context = errors.keySet().iterator().next();
			throw new ValidationException("Validation errors for context " + context + ":  " + String.join(",", errors.get(context) ) ) ;
		} else {
			throw new ValidationException("Wrong usage. Validation messages cannot be null or empty" );
		}
		
		
	}

	public ValidationException(String message) {
		super(message);
	}

	
}
