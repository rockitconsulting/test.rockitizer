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

public class RockitizerDeleteConfigTest extends CommonCLITest {

	@Before
	public void before() {
		System.clearProperty(Constants.ENV_KEY);
	}

	@Test
	public void testHelp() {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		System.out.println(new CommandLine(new RockitizerDeleteConfig()).getUsageMessage());

	}

	@Test
	public void testDeleteteConfig() {
		System.setProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY, "CLI");
		System.setProperty(Constants.ENV_KEY, "alex1");
		TestObjectFactory.resetEmptyConfigurationToContextDemoPrj();
		Assert.assertEquals(new CommandLine(new RockitizerCreateConfig()).execute("alex1"), 0);
		assertTrue(new File(configuration().getFullPath() + "resources-alex1.yaml").exists());
		Assert.assertEquals(new CommandLine(new RockitizerDeleteConfig()).execute("alex1"), 0);
		Assert.assertFalse(new File(configuration().getFullPath() + "resources-alex1.yaml").exists());

	}

	@After
	public void cleanEnv() {
		System.clearProperty(Constants.ENV_KEY);

	}

}
