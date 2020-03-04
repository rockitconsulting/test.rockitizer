package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

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

public class RockitizerDeleteTestTest {
	public static Logger log = Logger.getLogger(RockitizerDeleteTestTest.class.getName());
	
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
		System.out.println( new CommandLine( new RockitizerDeleteTest() ).getUsageMessage() );
		
	
	}
	
	

	@Test
	public void testDeleteTestCase() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerCreateTest()).execute(tc), 0);
		
		File junit = new File(ConfigUtils.getAbsolutePathToJava()+File.separator+tc+".java");
		Assert.assertTrue(junit.exists());
		
		Assert.assertEquals( new CommandLine(new RockitizerDeleteTest()).execute(tc), 0);
		
		//check junit java deleted
		Assert.assertFalse(junit.exists());
		

	}

	
	@Test
	public void testDeleteTestStep() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerCreateTest()).execute(tc,ts), 0);
		
		File tsf = new File(configuration().getFullPath()+File.separator+tc + File.separator + ts );
		Assert.assertTrue(tsf.exists());
		
		checkJunitNotCreated();
		
		Assert.assertEquals( new CommandLine(new RockitizerDeleteTest()).execute(tc,ts), 0);
		
		Assert.assertFalse(tsf.exists());
		
	}

	@Test
	public void testDeleteConnector() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerCreateTest()).execute(tc, ts, c ), 0);
		
		File cf = new File(configuration().getFullPath()+File.separator+tc + File.separator + ts + File.separator + c );
		log.info(" checking with context tc: "+ cf.getAbsolutePath());
		Assert.assertTrue(cf.exists());
		
		//check no java created
		checkJunitNotCreated();
		
		Assert.assertEquals( new CommandLine(new RockitizerDeleteTest()).execute(tc,ts,c), 0);
		
		Assert.assertFalse(cf.exists());
		
		File tsf = new File(configuration().getFullPath()+File.separator+tc);
		Assert.assertTrue(tsf.exists());

		
		Assert.assertEquals( new CommandLine(new RockitizerDeleteTest()).execute(tc), 0);
		
	}
	

	private void checkJunitNotCreated() {
		File junit = new File(ConfigUtils.getAbsolutePathToJava()+File.separator+tc+".java");
		Assert.assertFalse(junit.exists());
	}


}
