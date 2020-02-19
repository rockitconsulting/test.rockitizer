package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.ConnectorRef;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;
import io.github.rockitconsulting.test.rockitizer.validation.model.ValidationHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;

public class ValidationCLITest {
	public static Logger log = Logger.getLogger(ValidationCLITest.class.getName());

	final String rootPath = ConfigUtils.getAbsoluteRootPath();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";

	String rootPathTestSrc = rootPath + relPath;
	ResourcesHolderCLI rhCLI = new ResourcesHolderCLI();
	TestCasesHolderCLI tcCLI = new TestCasesHolderCLI();

	@Before
	public void before() {
		tcCLI.setAbsolutePath(rootPath);
		tcCLI.setRelativePath(relPath);
		// tcCLI.setTestcasesFileName("testcases-generated.yaml");
		rhCLI.setAbsolutePath(rootPath);
		rhCLI.setRelativePath(relPath);
		// rhCLI.setResourcesFileName("resources-generated.yaml");

	}

	@Test
	public void testSyncResourceConnectorsRefsExistingDataSourcesValid() throws IOException {
		TestCasesHolder tch = tcCLI.readResources();
		ResourcesHolder rh = rhCLI.readResources();
		
		List<ConnectorRef> result = new ArrayList<>();
		//TODO global ValidationHanlder with context tc/ts/conId
		tch.getTestCases().forEach(tc -> tc.getTestSteps().forEach(ts -> ts.getConnectorRefs().forEach(conRef -> {
			if (rh.findResourceByRef(conRef) == null) {
				log.info(" no resource found for conRef " + conRef.getConRefId());
				result.add(conRef);
			}
		})));
		Assert.assertTrue(result.size()==0);
		
	}

	@Test
	public void testSyncResourceConnectorsRefsExistingDataSourcesNotValid() throws IOException {
		tcCLI.setTestcasesFileName("testcases-missed-refs.yaml");
		TestCasesHolder tch = tcCLI.readResources();
		ResourcesHolder rh = rhCLI.readResources();

		List<ConnectorRef> result = new ArrayList<>();
		
		tch.getTestCases().forEach(tc -> tc.getTestSteps().forEach(ts -> ts.getConnectorRefs().forEach(conRef -> {
			if (rh.findResourceByRef(conRef) == null) {
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
