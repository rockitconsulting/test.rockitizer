package io.github.rockitconsulting.test.rockitizer.cli;


import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

import picocli.CommandLine;

public class RockitizerListTestcasesTest  extends CommonCLITest {

	@Before
	public void before() {
	}

	@Test
	public void testHelp() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		System.out.println( new CommandLine( new RockitizerListTestCases() ).getUsageMessage() );
		
	
	}
	
	
	@Test
	public void testYamlDefault() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals(new CommandLine( new RockitizerListTestCases() ).execute("all"), 0);
	
	}

	@Test
	public void testAllTestcasesTree() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerListTestCases()).execute("all","-v","tree" ), 0);

	}

	@Test
	public void testTestCaseTree() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerListTestCases()).execute("FILEinFILEOutTest","-v","tree" ), 0);

	}
	
	@Test
	public void testTestCaseTreeRecursive() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerListTestCases()).execute("FILEinFILEOutTest","-v","tree","-r" ), 0);

	}
	
	
	
}
