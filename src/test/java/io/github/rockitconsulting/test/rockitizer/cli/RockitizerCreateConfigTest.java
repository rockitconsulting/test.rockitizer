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

public class RockitizerCreateConfigTest {

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
