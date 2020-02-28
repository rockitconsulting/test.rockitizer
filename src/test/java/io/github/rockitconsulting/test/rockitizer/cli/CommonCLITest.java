package io.github.rockitconsulting.test.rockitizer.cli;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class CommonCLITest {
	public static Logger log = Logger.getLogger(CommonCLITest.class.getName());

	

	@Before
	public void before() {

		
	}

	

	@After
	public void after() {
		System.clearProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY);
		System.clearProperty(Constants.ENV_KEY);

	}


}
