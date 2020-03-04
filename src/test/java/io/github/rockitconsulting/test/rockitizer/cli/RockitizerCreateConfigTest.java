package io.github.rockitconsulting.test.rockitizer.cli;

import static org.junit.Assert.assertTrue;
import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import picocli.CommandLine;

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

public class RockitizerCreateConfigTest extends CommonCLITest {

	@Before
	public void before() {
		System.clearProperty(Constants.ENV_KEY);
	}

	@Test
	public void testHelp() {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		System.out.println(new CommandLine(new RockitizerCreateConfig()).getUsageMessage());

	}

	@Test
	public void testCreateConfig() {
		System.setProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY, "CLI");
		System.setProperty(Constants.ENV_KEY, "alex");
		TestObjectFactory.resetEmptyConfigurationToContextDemoPrj();
		Assert.assertEquals(new CommandLine(new RockitizerCreateConfig()).execute("alex"), 0);
		assertTrue(new File(configuration().getFullPath() + configuration().getRhApi().getResourcesFileName()).exists());

	}

	@After
	public void cleanEnv() {
		System.clearProperty(Constants.ENV_KEY);

	}

}
