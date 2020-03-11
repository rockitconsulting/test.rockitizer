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
	protected  String relPath = "";
	
	public void setRecordPath(String path) {
		recordPath = path;

	}

	public void setReplayPath(String path) {
		replayPath = path;

	}
	
	public String getRelPath() {
		return relPath;
	}

	public void setRelPath(String relPath) {
		this.relPath = relPath;
	}
	

	public String toString() {
		return this.getClass().getSimpleName()+"(\"" + (relPath.equalsIgnoreCase("")?"\\":relPath) + "\")";
	}
}
