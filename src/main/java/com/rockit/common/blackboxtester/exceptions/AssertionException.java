package com.rockit.common.blackboxtester.exceptions;

import org.apache.log4j.Logger;

/**
 * Used for for assertions in order to get the errors correctly documented
 *
 */
public class AssertionException extends AssertionError {
	public static final Logger LOGGER = Logger.getLogger(AssertionException.class);

	private static final long serialVersionUID = 1L;

	private final String reason;
	private final AssertionError error;

	public AssertionException(final String reason, final AssertionError e) {
		this.reason = reason;
		this.error = e;
		LOGGER.error(getMessage());
		throw error;
	}

	public AssertionException(final AssertionError e) {
		this.error = e;
		LOGGER.error(getMessage());
		throw error;
	}

	public AssertionException(final String msg) {
		throw new AssertionError(msg);
	}

	@Override
	public String getMessage() {
		return reason != null ? reason + " : " + error.getMessage() : error.getMessage();
	}
}
