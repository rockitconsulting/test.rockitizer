package io.github.rockitconsulting.test.rockitizer.validation;

import java.util.List;
import java.util.Map;

public interface Validatable {
	
	public String getValidationContext();
	
	public boolean isValid();
	
	public Map<String, List<String>> validate();

}
