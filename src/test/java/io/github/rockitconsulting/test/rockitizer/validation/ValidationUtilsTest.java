package io.github.rockitconsulting.test.rockitizer.validation;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.ConnectorRef;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestCase;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestStep;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		int s1 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s1, 20);

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
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 10);

	}


	@Test
	public void validateTestCasesInSyncWithFileSystem() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		ValidationUtils.validateTestCasesAndFileSystemInSync();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 0);

	}


}
