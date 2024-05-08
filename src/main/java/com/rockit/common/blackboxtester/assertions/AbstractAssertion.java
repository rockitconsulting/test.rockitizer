package com.rockit.common.blackboxtester.assertions;

/**
 * Test.Rockitizer - API regression testing framework Copyright (C) 2020
 * rockit.consulting GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 *
 */

public abstract class AbstractAssertion implements Assertions {
	protected String recordPath;
	protected String replayPath;
	protected String relPath = "";

	/**
	 * Sets record path
	 * @see com.rockit.common.blackboxtester.assertions.Assertions#setRecordPath(java.lang.String)
	 */
	public void setRecordPath(String path) {
		recordPath = path;
	}

	/**
	 * Sets replay path
	 * @see com.rockit.common.blackboxtester.assertions.Assertions#setReplayPath(java.lang.String)
	 */
	public void setReplayPath(String path) {
		replayPath = path;
	}

	/**
	 * Returns relative path
	 * @return
	 */
	public String getRelPath() {
		return relPath;
	}

	/**
	 * Assigns relative path as the object attribute.
	 * @param relPath
	 */
	public void setRelPath(String relPath) {
		this.relPath = relPath;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName() + "(\"" + (relPath.equalsIgnoreCase("") ? "\\" : relPath) + "\")";
	}
}
