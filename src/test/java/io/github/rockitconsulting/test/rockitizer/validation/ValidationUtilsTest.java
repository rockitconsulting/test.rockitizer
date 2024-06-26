package io.github.rockitconsulting.test.rockitizer.validation;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import static io.github.rockitconsulting.test.rockitizer.validation.ValidationHolder.validationHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
import io.github.rockitconsulting.test.rockitizer.exceptions.InvalidConnectorFormatException;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

/**
 * Test.Rockitizer - API regression testing framework Copyright (C) 2020
 * rockit.consulting GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 *
 */

public class ValidationUtilsTest {
	public static Logger log = Logger.getLogger(ValidationUtilsTest.class.getName());

	@Before
	public void before() {
		ValidationHolder.reset();
	}
	
	@Test(expected=InvalidConnectorFormatException.class)
	public void validateConnectorNOK_No_Separator() throws IOException {
		ValidationUtils.validateConnector("WRONG_NO_POINT_SEPARATOR");
	}
	
	@Test(expected=InvalidConnectorFormatException.class)
	public void validateConnectorNOK_Unknown() throws IOException {
		ValidationUtils.validateConnector("WRONG.UNKNOWN");
	}
	
	@Test
	public void validateConnectorOK_DBGET() throws IOException {
		ValidationUtils.validateConnector("DBGET.OK");
	}
	
	@Test
	public void validateConnectorDoubleDotOK_DBGET() throws IOException {
		ValidationUtils.validateConnector("DBGET.MY.OK");
	}

	@Test
	public void validateConnectorSCPGET_OK() throws IOException {
		ValidationUtils.validateConnector("SCPGET.FILEACTIVE");
	}

	
	@Test
	public void validateConnectorOK_HTTP() throws IOException {
		ValidationUtils.validateConnector("HTTP.OK");
	}
	
	@Test
	public void cleanGitIgnore() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		ValidationUtils.fixGitEmptyFoldersProblem();
		ValidationUtils.cleanGitIgnore();
		ValidationUtils.fixGitEmptyFoldersProblem();
		int s1 = validationHolder().size();
		validationHolder().logValidationErrors();
		Assert.assertEquals(24, s1);

	}

	@Test
	public void checkTesCaseStructureIsValid() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		ValidationUtils.cleanGitIgnore();
		ValidationUtils.fixGitEmptyFoldersProblem();
		int s1 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s1, 24);
		ValidationUtils.fixGitEmptyFoldersProblem();
		int s2 = ValidationHolder.validationHolder().size();
		Assert.assertEquals(s2, 24);
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
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 18);

	}

	@Test
	public void validateCompleteNotSyncWithFileSystem() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-NotSyncWithFS");
		ValidationUtils.validateConnectorRefExists();
		ValidationUtils.validateResources();
		ValidationUtils.validateTestCasesAndFileSystemInSync();
		ValidationUtils.validateNotAllowedEmptyStructures();
		ValidationUtils.validateSyncJavaAndTestCases();
		ValidationUtils.validateResourcesAndFileSystemInSync();
		ValidationUtils.validateReferencedDataSources();

		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue("Error number awaited 23 but was " + ValidationHolder.validationHolder().size(), ValidationHolder.validationHolder().size() == 30);

	}

	@Test
	public void validateTestCasesInSyncWithFileSystem() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj();
		ValidationUtils.validateTestCasesAndFileSystemInSync();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 0);

	}
	
	@Test
	public void validateResourcesInSyncWithFileSystem() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-NotSyncWithFS");
		ValidationUtils.validateResourcesAndFileSystemInSync();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 0);
	}

	@Test
	public void validateReferencedDataSources() throws IOException {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-NotSyncWithFS");
		ValidationUtils.validateReferencedDataSources();
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
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 4);
	}

	@Test
	public void testSyncConfig() throws IOException {
		// init
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-sync-res");
		// prepare for test
		ResourcesHolder rhyaml = configuration().getRhApi().resourcesHolderFromYaml();
		// clean db
		rhyaml.getDbConnectors().clear();
		// write new config
		configuration().getRhApi().resourcesHolderToYaml(rhyaml);
		// re-init
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-sync-res");
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().getDbConnectors().size() == 0);
		List<String> syncConfig = ValidationUtils.syncConfig();
		Assert.assertTrue(syncConfig.size() == 2);
		syncConfig.forEach(m -> System.out.println(m));

		// re-init
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-sync-res");
		Assert.assertTrue(configuration().getRhApi().resourcesHolderFromYaml().getDbConnectors().size() == 2);

	}

	@Test
	public void testCleanConfig() throws IOException {

		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-NotSyncWithFS");
		ResourcesHolder rhyaml = configuration().getRhApi().resourcesHolderFromYaml();
		rhyaml.getDbConnectors().clear();
		configuration().getRhApi().resourcesHolderToYaml(rhyaml);
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-NotSyncWithFS");
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().getDbDataSources().size() == 0);
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().getMqDataSources().size() == 1);
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().getKeyStores().size() == 0);
		List<String> cleanConfig = ValidationUtils.cleanConfig();
		System.out.println(cleanConfig.size());
		Assert.assertTrue(cleanConfig.size() == 0);
		cleanConfig.forEach(m -> System.out.println(m));

		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-NotSyncWithFS");
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().getDbDataSources().size() == 0);
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().getMqDataSources().size() == 1);
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().getKeyStores().size() == 0);
	}

	@Test
	public void validateResourcePlaceholdersWithDefinedPlaceholdersInEnvsYaml() {
		System.setProperty(Constants.ENV_KEY, this.getClass().getSimpleName() + "-with-defined-placeholders");

		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-with-defined-placeholders");
		ValidationUtils.validateResources();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 0);

		System.clearProperty(Constants.ENV_KEY);
	}

	@Test
	public void validateResourcePlaceholdersWithUndefinedPlaceholdersInEnvsYaml() {
		System.setProperty(Constants.ENV_KEY, this.getClass().getSimpleName() + "-with-undefined-placeholders");
		
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName() + "-with-undefined-placeholders");
		ValidationUtils.validateResources();
		ValidationHolder.validationHolder().logValidationErrors();
		Assert.assertTrue(ValidationHolder.validationHolder().size() == 2);
		
		System.clearProperty(Constants.ENV_KEY);
	}

}
