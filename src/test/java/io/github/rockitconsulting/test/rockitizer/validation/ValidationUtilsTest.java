package io.github.rockitconsulting.test.rockitizer.validation;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import static io.github.rockitconsulting.test.rockitizer.validation.ValidationHolder.validationHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;

public class ValidationUtilsTest {
	public static Logger log = Logger.getLogger(ValidationUtilsTest.class.getName());

	@Before
	public void before() {
		ValidationHolder.reset();
	}

	@Test
	public void cleanGitIgnore() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		ValidationUtils.fixGitEmptyFoldersProblem();
		ValidationUtils.cleanGitIgnore();
		ValidationUtils.fixGitEmptyFoldersProblem();
		int s1 = validationHolder().size();
		validationHolder().logValidationErrors();
		Assert.assertEquals(20, s1 );

	}

	@Test
	public void checkTesCaseStructureIsValid() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		ValidationUtils.cleanGitIgnore();
		ValidationUtils.fixGitEmptyFoldersProblem();
		int s1 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s1, 20);
		ValidationUtils.fixGitEmptyFoldersProblem();
		int s2 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s2, 20);
		Assert.assertTrue("size stays the same, messages  should be added while context exists" + s1 + "==" + s2, s1 == s2);
		ValidationHolder.validationHolder().forEach((k, v) -> log.info("Validation erros: " + k + " - " + Joiner.on(";").join(v)));

	}

	@Test
	public void testSyncResourceConnectorsRefsExistingDataSourcesValid() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName());
		ValidationUtils.validateConnectorRefExists();
		ValidationHolder.validationHolder().forEach((k, v) -> log.info("Validation erros: " + k + " - " + Joiner.on(";").join(v)));
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 0);

	}

	@Test
	public void testSyncResourceConnectorsRefsExistingDataSourcesNotValid() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-missed-refs");
		ValidationUtils.validateConnectorRefExists();
		ValidationHolder.validationHolder().forEach((k, v) -> log.info("Validation erros: " + k + " - " + Joiner.on(";").join(v)));
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 3);
	}

	@Test
	public void validateResourceConfiigurationIsValid() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-Valid");
		ValidationUtils.validateResources();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 0);

	}

	@Test
	public void validateResourceConfiigurationIsNotValid() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-Invalid");
		ValidationUtils.validateResources();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 3);

	}


	@Test
	public void validateTestCasesNotSyncWithFileSystem() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-NotSyncWithFS");
		ValidationUtils.validateTestCasesAndFileSystemInSync();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 12);

	}


	@Test
	public void validateTestCasesInSyncWithFileSystem() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		ValidationUtils.validateTestCasesAndFileSystemInSync();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 0);

	}
	
	
	@Test
	public void validateNotAllowedEmptyStructuresOK() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		ValidationUtils.validateNotAllowedEmptyStructures();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 0);
	}
		
	@Test
	public void validateNotAllowedEmptyStructuresNOK() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-NotSyncWithFS");
		ValidationUtils.validateNotAllowedEmptyStructures();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 3);
	}

	
	@Test
	public void testSyncResources() throws IOException {
		//init
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-sync-res");
		//prepare for test
		ResourcesHolder rhyaml = configuration().getRhApi().resourcesHolderFromYaml();
		//clean db
		rhyaml.getDbConnectors().clear();
		//write new config
		configuration().getRhApi().resourcesHolderToYaml(rhyaml);
		//re-init
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-sync-res");
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().getDbConnectors().size()==0);
		ValidationUtils.syncResources();
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().getDbConnectors().size()==2);
		
	}
	

}

