package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.Configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import org.junit.Assert;
import org.junit.Test;

import picocli.CommandLine;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class RockitizerRunTestTest  extends CommonCLITest {

	@Test
	public void testHelp() {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		System.out.println(new CommandLine(new RockitizerRunTest()).getUsageMessage());

	}

	@Test
	public void testRunTests() {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		Assert.assertEquals(new CommandLine(new RockitizerRunTest()).execute("all"), 0);

	}

	@Test
	public void testRunTestsParams() {
		System.setProperty(Constants.ENV_KEY, "devp");
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerRunTest()).execute("all","replay","devp"), 0);
		
		

	}
	
	@Test
	public void testRunAllWithTestContext() {
		Configuration.configuration().reinit();
		Assert.assertEquals(new CommandLine(new RockitizerRunTest()).execute("com.rockit.common.blackboxtester.util.FileUtilsTest"), 0);
	}

	
	@Test
	public void testRunNotFoundJUnit() {
		Configuration.configuration().reinit();
		Assert.assertEquals(new CommandLine(new RockitizerRunTest()).execute("FileUtilsTest"), 0);
	}
	
	
}
