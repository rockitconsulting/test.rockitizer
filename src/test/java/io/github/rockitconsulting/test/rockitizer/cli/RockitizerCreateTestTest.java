package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;

import org.apache.log4j.Logger;
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

public class RockitizerCreateTestTest  extends CommonCLITest {
	public static Logger log = Logger.getLogger(RockitizerCreateTestTest.class.getName());
	
	private String tc = "TestTestCase";
	private String ts = "TestTestStep";
	private String c = "HTTP.TESTCONNECTOR";

	
	@Before
	public void before() {
		System.clearProperty(Constants.ENV_KEY);	
	}
	
	

	@Test
	public void testHelp() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		System.out.println( new CommandLine( new RockitizerCreateTest() ).getUsageMessage() );
		
	
	}
	
	

	@Test
	public void testCreateTestCase() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerCreateTest()).execute(tc), 0);
		
		deleteTestCaseRecursiveAndCheck();
		
		//check junit java created
		File junit = new File(ConfigUtils.getAbsolutePathToJava()+File.separator+tc+".java");
		Assert.assertTrue(junit.exists());
		Assert.assertTrue(junit.delete());
		Assert.assertFalse(junit.exists());
		

	}

	
	@Test
	public void testCreateTestStep() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerCreateTest()).execute(tc,ts), 0);
		
		File tsf = new File(configuration().getFullPath()+File.separator+tc + File.separator + ts );
		log.info(" checking with context teststep: "+ tsf.getAbsolutePath());
		Assert.assertTrue(tsf.exists());
		FileUtils.deleteDirectory(tsf);
		Assert.assertFalse(tsf.exists());
		
		deleteTestCaseRecursiveAndCheck();
		
		//check junit java created
		File junit = new File(ConfigUtils.getAbsolutePathToJava()+File.separator+tc+".java");
		Assert.assertTrue(junit.exists());
		Assert.assertTrue(junit.delete());
		Assert.assertFalse(junit.exists());
		
		

	}

	@Test
	public void testCreateConnector() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerCreateTest()).execute(tc, ts, c ), 0);
		
		File cf = new File(configuration().getFullPath()+File.separator+tc + File.separator + ts + File.separator + c );
		log.info(" checking with context tc: "+ cf.getAbsolutePath());
		Assert.assertTrue(cf.exists());
		FileUtils.deleteDirectory(cf);
		Assert.assertFalse(cf.exists());
		
		deleteTestCaseRecursiveAndCheck();
		
		//check junit java created
		File junit = new File(ConfigUtils.getAbsolutePathToJava()+File.separator+tc+".java");
		Assert.assertTrue(junit.exists());
		Assert.assertTrue(junit.delete());
		Assert.assertFalse(junit.exists());
		

	}
	
	private void deleteTestCaseRecursiveAndCheck() {
		File tcf = new File(configuration().getFullPath()+File.separator+tc);
		log.info(" delete tc recursive: "+ tcf.getAbsolutePath());
		Assert.assertTrue(tcf.exists());
		FileUtils.deleteDirectory(tcf);
		Assert.assertFalse(tcf.exists());
	}
	

}
