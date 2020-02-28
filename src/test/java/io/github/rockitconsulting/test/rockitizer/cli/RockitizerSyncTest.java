package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import org.junit.Assert;
import org.junit.Test;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

import picocli.CommandLine;

public class RockitizerSyncTest extends CommonCLITest {

	@Test
	public void testHelp() {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		System.out.println(new CommandLine(new RockitizerSync()).getUsageMessage());

	}

	@Test
	public void testSync() {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		Assert.assertEquals(new CommandLine(new RockitizerSync()).execute(), 0);

	}

	@Test
	public void testSyncWithParams() {
		System.setProperty(Constants.ENV_KEY, "devp");
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		Assert.assertEquals(new CommandLine(new RockitizerSync()).execute("devp"), 0);

	}

}
