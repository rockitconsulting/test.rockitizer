package io.github.rockitconsulting.test.rockitizer.cli;


import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

import picocli.CommandLine;

public class RockitizerListResourcesTest {

	@Before
	public void before() {
		System.clearProperty(Constants.ENV_KEY);	
	}

	@Test
	public void testHelp() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		System.out.println( new CommandLine( new RockitizerListResources() ).getUsageMessage() );
		
	
	}
	
	
	@Test
	public void testYaml() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals(new CommandLine( new RockitizerListResources() ).execute(), 0);
	
	}

	@Test
	public void testTree() {
		System.setProperty(Constants.ENV_KEY, "devp");
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerListResources()).execute("devp", "-v","tree" ), 0);

	}

}
