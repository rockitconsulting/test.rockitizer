package com.rockit.common.blackboxtester.exceptions;

import static org.junit.Assert.*;

import org.junit.Test;

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

public class ConnectorExceptionTest {

	@Test
	public void testConnectorExceptionException() {
		ConnectorException ce = new ConnectorException( new Exception("test"));
		assertEquals("test", ce.getCause().getMessage());
		
	}

	@Test
	public void testConnectorExceptionString() {
		ConnectorException ce = new ConnectorException( "test");
		assertEquals("test", ce.getMessage());

	}

	@Test
	public void testConnectorExceptionMsgThr() {
		ConnectorException ce = new ConnectorException( "test", new Throwable("thrTest"));
		assertEquals("test", ce.getMessage());
		assertEquals("thrTest", ce.getCause().getMessage());

	}
	
	
}
