package com.rockit.common.blackboxtester.connector;

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

public interface Connector {
	
	public static final String CONTENT_TYPE_APP_XML="application/xml";
	public static final String CONTENT_TYPE_TEXT_XML="text/xml";
	public static final String CONTENT_TYPE_JSON="application/json";
	public static final String CONTENT_TYPE_CVS="application/csv";
	
	
	
	void proceed();

	String getId();

	String getType();
	
}
