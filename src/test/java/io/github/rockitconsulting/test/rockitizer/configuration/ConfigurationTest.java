package io.github.rockitconsulting.test.rockitizer.configuration;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.MQConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.ConnectorRef;
import io.github.rockitconsulting.test.rockitizer.exceptions.InvalidConnectorFormatException;
import io.github.rockitconsulting.test.rockitizer.exceptions.ResourceNotFoundException;
import io.github.rockitconsulting.test.rockitizer.exceptions.ValidationException;

import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

public class ConfigurationTest {

	public static Logger log = Logger.getLogger(ConfigurationTest.class.getName());

	@Before
	public void before() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName());
		Assert.assertNotNull(configuration());
		Assert.assertEquals(configuration().getTchApi().getTestcasesFileName(), "ConfigurationTest-testcases.yaml");
		Assert.assertEquals(configuration().getRhApi().getResourcesFileName(), "ConfigurationTest-resources.yaml");

		System.out.println(" --> " + configuration().getFullPath());
		System.out.println(" --> " + configuration().getRhApi().getResourcesFileName());

		configuration().getRhApi().getResourcesHolder().getHttpConnectors().forEach(c -> System.out.println(c.toString()));
		configuration().getRhApi().getResourcesHolder().getKeyStores().forEach(c -> System.out.println(c.toString()));
		System.clearProperty(Constants.MODE_KEY);
		System.clearProperty(Constants.ENV_KEY);

	}

	@Test
	public void testRuntimeParamsConfig() {
		System.setProperty(Constants.MODE_KEY, "record");
		System.setProperty(Constants.ENV_KEY, "devp");
		configuration().reinit();
		Assert.assertEquals(configuration().getEnvironment(), "devp");
		Assert.assertEquals(configuration().getRunMode(), Configuration.RunModeTypes.RECORD);
		Assert.assertEquals(configuration().getRhApi().getResourcesFileName() + " equals ConfigurationTest-resources.yaml",  "ConfigurationTest-resources.yaml", configuration().getRhApi()
				.getResourcesFileName());
		
		//by providing environment the interpolation in tmp processed
		Assert.assertTrue(configuration().getRhApi().getEnvResourcesFile()!=null);
		
		System.clearProperty(Constants.MODE_KEY);
		System.clearProperty(Constants.ENV_KEY);

	}

	@Test
	public void testSetEnvironmentAndMakeReplacements() {
		System.setProperty(Constants.MODE_KEY, "record");
		System.setProperty(Constants.ENV_KEY, "ConfigurationTest");
		configuration().reinit();
		
		Assert.assertEquals(configuration().getEnvironment(), "ConfigurationTest");
		Assert.assertEquals(configuration().getRunMode(), Configuration.RunModeTypes.RECORD);
		
		Assert.assertTrue(configuration().getRhApi().getEnvResourcesFile()!=null);
		Assert.assertTrue(Paths.get(configuration().getRhApi().getFullPath() + configuration().getRhApi().getResourcesFileName()).toFile().exists());
		
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().findMQDataSourceById("defaultMQ").getPort().equals("1515"));
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().findKeyStoreById("notDefaultKS").getPath().equalsIgnoreCase("c:\\temp\\ConfigurationTest.jks"));
		 //getHttpConnectors().stream().filter(httpConRef -> "HTTP.ADDBOOK".equals(httpConRef.getId())).findFirst().get().getTimeout().equals("100000")
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().findResourceByRef(new ConnectorRef("HTTP.ADDBOOK"))!=null);
		
		HTTPConnectorCfg httpCfg = (HTTPConnectorCfg)configuration().getRhApi().getResourcesHolder().findResourceByRef(new ConnectorRef("HTTP.ADDBOOK")); 
		
		Assert.assertTrue( httpCfg.getDsRefId()!=null   );
		Assert.assertTrue( httpCfg.getUrl()!=null   );

		System.clearProperty(Constants.MODE_KEY);
		System.clearProperty(Constants.ENV_KEY);
	}
	
	@Test
	public void testSetEnvironmentAndMakeReplacementsWhenEnvPropsNotExist() {
		System.setProperty(Constants.MODE_KEY, "record");
		System.setProperty(Constants.ENV_KEY, "devp2");
		configuration().reinit();
		
		Assert.assertEquals(configuration().getEnvironment(), "devp2");
		Assert.assertEquals(configuration().getRunMode(), Configuration.RunModeTypes.RECORD);
		
		Assert.assertTrue(configuration().getRhApi().getEnvResourcesFile()!=null);
		Assert.assertTrue(Paths.get(configuration().getRhApi().getFullPath() + configuration().getRhApi().getResourcesFileName()).toFile().exists());
		
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().findMQDataSourceById("defaultMQ").getPort().equals("1414"));
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().findKeyStoreById("notDefaultKS").getPath().equalsIgnoreCase("c:\\temp\\jks.jks"));
		Assert.assertTrue(configuration().getRhApi().getResourcesHolder().getHttpConnectors().stream().filter(httpConRef -> "HTTP.ADDBOOK".equals(httpConRef.getId())).findFirst().get().getTimeout().equals("500000"));

		System.clearProperty(Constants.MODE_KEY);
		System.clearProperty(Constants.ENV_KEY);
	}

	@Test
	public void testDBConfigWithDataSourceOK() {
		DBConnectorCfg dbConCfg = (DBConnectorCfg) configuration().getConnectorById("DBPUT.CLAEN");
		DBDataSource ds = configuration().getDBDataSourceByConnector(dbConCfg);
		Assert.assertNotNull(ds);
	}

	@Test
	public void testMQConfigWithMQDataSourceOK() {
		configuration().reinit();
		MQConnectorCfg cfg = (MQConnectorCfg) configuration().getConnectorById("MQGET.OUT.MQ2MQ");
		MQDataSource ds = configuration().getMQDataSourceByConnector(cfg);
		Assert.assertNotNull(ds);
	}
	
	@Test
	public void testMQGetConfigWithCorrelationIdMQDataSourceOK() {
		configuration().reinit();
		MQConnectorCfg cfg = (MQConnectorCfg) configuration().getConnectorById("MQGET.IN.MQ2MQ.WITH.CORRID");
		MQDataSource ds = configuration().getMQDataSourceByConnector(cfg);
		Assert.assertNotNull(ds);
		Assert.assertNotNull(cfg.getCorrelationId());
	}
	
	
	

	@Test
	public void testHTTPConfigWithKeyStoreOK() {
		HTTPConnectorCfg cfg = (HTTPConnectorCfg) configuration().getConnectorById("HTTP.CONFIGTEST.WITH.KEYSTORE");
		KeyStore ds = configuration().getKeyStoreByConnector(cfg);
		Assert.assertNotNull(ds);
	}

	@Test
	public void testHTTPConfigWithoutKeyStoreOK() {
		HTTPConnectorCfg cfg = (HTTPConnectorCfg) configuration().getConnectorById("HTTP.CONFIGTEST.NO.KEYSTORE");
		KeyStore ds = configuration().getKeyStoreByConnector(cfg);
		Assert.assertNull(ds);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testHTTPConfigNoContentType() {
		HTTPConnectorCfg cfg = (HTTPConnectorCfg) configuration().getConnectorById("HTTP.CONFIGTEST.NO.CONTENTTYPE");
		KeyStore ds = configuration().getKeyStoreByConnector(cfg);
		Assert.assertNotNull(ds);
	}

	@Test(expected = ValidationException.class)
	public void getConnectorByIdWithValidationException() {
		configuration().getConnectorById("FILEDEL.INVALID.NO.PATH");
	}

	@Test(expected = InvalidConnectorFormatException.class)
	public void testConfigNOK() {
		configuration().getConnectorById("NOT.FOUND");
	}
}
