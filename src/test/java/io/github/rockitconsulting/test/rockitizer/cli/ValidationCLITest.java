package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationHolder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class ValidationCLITest {

	
	
	@Before
	public void before() {
		ValidationHolder.reset();
	}
	
	@Test
	public void validateConnectorConfiiguration() {
		
	}

	@After
	public void after() {
		System.clearProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY);
	}
	
}
