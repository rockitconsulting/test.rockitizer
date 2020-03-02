package io.github.rockitconsulting.test.rockitizer.validation;

import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

public class ValidationHolder extends HashMap<Context, List<Message>> {

	/**
	 * SINGLETON Holder of Validation Messages with {@link #reset()} function
	 * 
	 */
	public static final Logger log = Logger.getLogger(ValidationHolder.class.getName());

	private static final long serialVersionUID = 1L;

	private static ValidationHolder validationHolder = new ValidationHolder();

	private ValidationHolder() {
	}

	public static ValidationHolder validationHolder() {
		return validationHolder;
	}

	public static ValidationHolder reset() {
		validationHolder.clear();
		return validationHolder();
	}

	public void add(Context context, Message pMessage) {
		add(context, ImmutableList.<Message> builder().add(pMessage).build());
	}

	public void add(Context context, List<Message> pMessages) {
		List<Message> messages = new ArrayList<>(pMessages);

		if (validationHolder.containsKey(context)) {
			messages.addAll(validationHolder.get(context));
		}
		validationHolder.put(context, messages);

	}

	public void addAll(Map<Context, List<Message>> pMessages) {
		pMessages.forEach((k, v) -> add(k, v));

	}

	public void logValidationErrors() {
		validationHolder().forEach((k, v) -> log.info("Validation erros: " + k + " - " + Joiner.on(";").join(v)));
	}

}
