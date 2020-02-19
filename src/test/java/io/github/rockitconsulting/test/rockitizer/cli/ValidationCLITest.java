package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.ConnectorRef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidationCLITest {
	public static Logger log = Logger.getLogger(ValidationCLITest.class.getName());

	@Before
	public void before() {
	}
	
	


	@Test
	public void testSyncResourceConnectorsRefsExistingDataSourcesValid() throws IOException {
	
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName());
		List<ConnectorRef> result = new ArrayList<>();
		//TODO global ValidationHanlder with context tc/ts/conId
		configuration().getTestCasesHolder().getTestCases().forEach(tc -> tc.getTestSteps().forEach(ts -> ts.getConnectorRefs().forEach(conRef -> {
			if (configuration().getResourcesHolder().findResourceByRef(conRef) == null) {
				log.info(" no resource found for conRef " + conRef.getConRefId());
				result.add(conRef);
			}
		})));
		Assert.assertTrue(result.size()==0);
		
	}
	
	@Test
	public void testSyncResourceConnectorsRefsExistingDataSourcesNotValid() throws IOException {
		
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName()+"-missed-refs");

		List<ConnectorRef> result = new ArrayList<>();
		
		configuration().getTestCasesHolder().getTestCases().forEach(tc -> tc.getTestSteps().forEach(ts -> ts.getConnectorRefs().forEach(conRef -> {
			if (configuration().getResourcesHolder().findResourceByRef(conRef) == null) {
				log.info(" no resource found for conRef " + conRef.getConRefId());
				result.add(conRef);
			}
		})));
		Assert.assertTrue(result.size()==4);
	}

	
	

	/*
	 * @Test public void testSyncBetwenTestCasesAndResources() throws
	 * IOException { TestCasesHolder tch = tcCLI.readResources();
	 * ResourcesHolder rh = rhCLI.readResources();
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * @Test public void testSyncBetwenTestCasesAndFileSystem() throws
	 * IOException { TestCasesHolder tch = tcCLI.readResources();
	 * ResourcesHolder rh = rhCLI.readResources(); }
	 * 
	 * @Test public void testValidateDataSourcesAndConnectors() { //No
	 * placeholders, // Mandatory params are filled }
	 */
}
