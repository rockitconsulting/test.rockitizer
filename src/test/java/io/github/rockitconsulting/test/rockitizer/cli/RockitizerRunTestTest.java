package io.github.rockitconsulting.test.rockitizer.cli;

import static org.junit.Assert.*;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import picocli.CommandLine;

public class RockitizerRunTestTest {


	@Test
	public void testHelp() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		System.out.println( new CommandLine( new RockitizerRunTest() ).getUsageMessage() );
		
	
	}
	
	

	@Test
	public void testCreateTestCase() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerRunTest()).execute("all"), 0);
		
		

	}

}
