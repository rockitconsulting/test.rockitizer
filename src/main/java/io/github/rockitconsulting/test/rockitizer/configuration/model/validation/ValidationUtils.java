package io.github.rockitconsulting.test.rockitizer.configuration.model.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ValidationUtils {

	 private static void checkValid(String paramName, String paramValue,  List<String> messages) {
		if(paramValue==null) {
			messages.add(" mandatory param [" + paramName + "] cannot be 'null'");
		} else if (paramValue.startsWith("@")&&paramValue.endsWith("@")) {
			messages.add(" mandatory param [" + paramName + "] with value '" + paramValue + "' must exist and cannot contain @placeholders@");
		}
	}

	/**
	 *  @usage ImmutableMap.<String, String>builder().
				put(k, v).
				put(k, v).
				build()
	 * 
	 * @param context
	 * @param fields
	 * @return
	 */
	public static Map<String, List<String>> checkValid(String context, Map<String, String> fields) {
		
		List<String> messages = new ArrayList<>();

		fields.forEach((f,v) -> {
			ValidationUtils.checkValid( f,v , messages);
		});
        
		Map<String, List<String>> res = new LinkedHashMap<String, List<String>>();
        if( !messages.isEmpty() ) {
        	res.put( context, messages);
        }
        return res;

	}

	
	
	

}
