package io.github.rockitconsulting.test.rockitizer.cli;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

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

public class CommonCLITest {
	public static Logger log = Logger.getLogger(CommonCLITest.class.getName());

	

	@Before
	public void before() {

		
	}

	

	@After
	public void after() {
		System.clearProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY);
		System.clearProperty(Constants.ENV_KEY);

	}


}
