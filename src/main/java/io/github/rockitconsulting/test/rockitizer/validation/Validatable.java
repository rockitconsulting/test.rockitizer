package io.github.rockitconsulting.test.rockitizer.validation;

import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.util.List;
import java.util.Map;

public interface Validatable {
	
	public Context getContext();
	
	public boolean isValid();
	
	public Map<Context, List<Message>> validate();
	
	public Map<String, String> getFieldsAsOrderedMap();

}
