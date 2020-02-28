package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import org.junit.Test;

import picocli.CommandLine;

public class RockitizerCLITest {

	@Test
	public void testHelp() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		System.out.println( new CommandLine( new RockitizerCLI() ).getUsageMessage() );
		
	
	}

}
