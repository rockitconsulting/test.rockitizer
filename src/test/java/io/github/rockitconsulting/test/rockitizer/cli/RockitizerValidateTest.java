package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.validation.ValidationHolder;

import org.junit.Assert;
import org.junit.Test;

import picocli.CommandLine;

public class RockitizerValidateTest extends CommonCLITest {

	@Test
	public void testHelp() {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		System.out.println(new CommandLine(new RockitizerValidate()).getUsageMessage());

	}

	@Test
	public void testValidate() {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		Assert.assertEquals(new CommandLine(new RockitizerValidate()).execute(), 0);
		Assert.assertTrue(ValidationHolder.validationHolder().size()==7);

	}

	@Test
	public void testValidateWithParams() {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		Assert.assertEquals(new CommandLine(new RockitizerValidate()).execute("devp","-gitfix","true"), 0);
		Assert.assertTrue(ValidationHolder.validationHolder().size()==7);

	}	
	
}
