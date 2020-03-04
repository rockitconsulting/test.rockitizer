package io.github.rockitconsulting.test.rockitizer.validation.model;

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

public class Message {
	
	public enum LEVEL {INFO, WARN, ERROR};
	
	private String message;
	
	private LEVEL level;
	
	
	public Message (LEVEL l, String m) {
		message = m;
		level = l;
		
	}


	public String getMessage() {
		return message;
	}


	public LEVEL getLevel() {
		return level;
	}


	@Override
	public String toString() {
		return "Message [message=" + message + ", level=" + level + "]";
	}
	

}
