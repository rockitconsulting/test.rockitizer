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

public class RockitizerCreateTestTest {
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
		File tcf = new File(configuration().getFullPath()+File.separator+tc);
		log.info(" checking with context tc: "+ tcf.getAbsolutePath());
		Assert.assertTrue(tcf.exists());
		Assert.assertTrue(tcf.delete());
		Assert.assertFalse(tcf.exists());
		
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
		
		checkJunitNotCreated();
		
		
		File tsf = new File(configuration().getFullPath()+File.separator+tc + File.separator + ts );
		log.info(" checking with context tc: "+ tsf.getAbsolutePath());
		Assert.assertTrue(tsf.exists());
		FileUtils.deleteDirectory(tsf);
		Assert.assertFalse(tsf.exists());
		

	}

	@Test
	public void testCreateConnector() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(); 
		Assert.assertEquals( new CommandLine(new RockitizerCreateTest()).execute(tc, ts, c ), 0);
		
		//check no java created
		checkJunitNotCreated();
		
		
		File cf = new File(configuration().getFullPath()+File.separator+tc + File.separator + ts + File.separator + c );
		log.info(" checking with context tc: "+ cf.getAbsolutePath());
		Assert.assertTrue(cf.exists());
		FileUtils.deleteDirectory(cf);
		Assert.assertFalse(cf.exists());
		

	}

	private void checkJunitNotCreated() {
		File junit = new File(ConfigUtils.getAbsolutePathToJava()+File.separator+tc+".java");
		Assert.assertFalse(junit.exists());
	}


}
