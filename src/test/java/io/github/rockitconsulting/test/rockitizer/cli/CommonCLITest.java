package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommonCLITest {
	public static Logger log = Logger.getLogger(CommonCLITest.class.getName());

	CommonCLI commonCLI = new CommonCLI();
	

	@Before
	public void before() {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		Assert.assertNotNull(configuration());
		
		System.out.println(" --> "+ configuration().getFullPath() );
		System.out.println(" --> "+ configuration().getRhApi().getResourcesFileName() );
		
		configuration().getRhApi().getResourcesHolder().getHttpConnectors().forEach( c -> System.out.println(c.toString())  );
		configuration().getRhApi().getResourcesHolder().getKeyStores().forEach( c -> System.out.println(c.toString())  );

		
	}

	@Test
	public void testPrintTestSuite() {
		commonCLI.printAllTests();
	}


}
