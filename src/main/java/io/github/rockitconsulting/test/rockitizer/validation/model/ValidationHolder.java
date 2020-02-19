package io.github.rockitconsulting.test.rockitizer.validation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValidationHolder extends HashMap<Context, List<Message>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static ValidationHolder validationHolder = new ValidationHolder();
	
	
	private ValidationHolder() {} 
	
	
	public static ValidationHolder validationHolder() {
		return validationHolder;
	}
	

	public static ValidationHolder reset() {
		validationHolder = new ValidationHolder();
		return validationHolder();
	}


	public void add(Context context, List<Message> pMessages) {
		List<Message> messages = new ArrayList<>(pMessages);
		
		if(validationHolder.containsKey(context)) {
			messages.addAll( validationHolder.get(context) );
		}	
		validationHolder.put(context, messages);
		
	}

	
}
