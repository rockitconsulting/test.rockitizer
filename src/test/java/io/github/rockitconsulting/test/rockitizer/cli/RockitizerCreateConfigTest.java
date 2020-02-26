package io.github.rockitconsulting.test.rockitizer.cli;

import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.Configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import java.io.File;

import org.junit.Test;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class RockitizerCreateConfigTest {

	@Test
	public void test() {
		String environment = "alex";
		System.out.println(" setting  environment " + environment + " and forcing config generation from filesystem");
		System.setProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY, "CLI");
		System.setProperty(Constants.ENV_KEY, environment);

		TestObjectFactory.resetEmptyConfigurationToContextDemoPrj();

		RockitizerCreateConfig cc = new RockitizerCreateConfig();
		cc.environment = "alex";
		cc.run();

		assertTrue(new File(Configuration.configuration().getFullPath() + Configuration.configuration().getRhApi().getResourcesFileName()).exists());

	}

}
