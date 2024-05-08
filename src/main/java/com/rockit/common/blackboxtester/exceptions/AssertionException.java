package com.rockit.common.blackboxtester.exceptions;

import org.apache.log4j.Logger;

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
