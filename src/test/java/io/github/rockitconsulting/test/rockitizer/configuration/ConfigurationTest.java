package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.cli.CLIObjectFactory;
import io.github.rockitconsulting.test.rockitizer.cli.ResourcesHolderCLI;
import io.github.rockitconsulting.test.rockitizer.cli.TestCasesHolderCLI;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.MQConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.exceptions.UnknownConnectorException;
import io.github.rockitconsulting.test.rockitizer.exceptions.ValidationException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ConfigurationTest {

	public static Logger log = Logger.getLogger(ConfigurationTest.class.getName());

	final String rootPath = ConfigUtils.getAbsoluteRootPath();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";
	String rootPathTestSrc = rootPath + relPath;

	ResourcesHolderCLI rhCLI ;
	TestCasesHolderCLI tcCLI; 

	@Before
	public void before() {
		rhCLI = CLIObjectFactory.createResourcesHolderCLI(rootPath, relPath, "ConfigurationTest-resources.yaml");
		tcCLI = CLIObjectFactory.createTestCasesHolderCLI(rootPath, relPath, "ConfigurationTest-testcases.yaml");
	}
	
	@Test
	public void testDBConfigWithDataSourceOK() {
		Configuration configuration = Configuration.configuration(rhCLI, tcCLI);
		Assert.assertNotNull(configuration);
		DBConnector dbConCfg = (DBConnector) configuration.getConnectorById("DBPUT.CLAEN");
		DBDataSource ds = configuration.getDBDataSourceByConnector(dbConCfg);
		Assert.assertNotNull(ds);
	}
	
	@Test
	public void testMQConfigWithMQDataSourceOK() {
		Configuration configuration = Configuration.configuration(rhCLI, tcCLI);
		Assert.assertNotNull(configuration);
		MQConnector cfg = (MQConnector) configuration.getConnectorById("MQGET.OUT.MQ2MQ");
		MQDataSource ds = configuration.getMQDataSourceByConnector(cfg);
		Assert.assertNotNull(ds);
	}
	
	@Test
	public void testHTTPConfigWithKeyStoreOK() {
		Configuration configuration = Configuration.configuration(rhCLI, tcCLI);
		Assert.assertNotNull(configuration);
		HTTPConnector cfg = (HTTPConnector) configuration.getConnectorById("HTTP.CONFIGTEST.WITH.KEYSTORE");
		KeyStore ds = configuration.getKeyStoreByConnector(cfg);
		Assert.assertNotNull(ds);
	}
	

	@Test
	public void testHTTPConfigWithoutKeyStoreOK() {
		Configuration configuration = Configuration.configuration(rhCLI, tcCLI);
		Assert.assertNotNull(configuration);
		HTTPConnector cfg = (HTTPConnector) configuration.getConnectorById("HTTP.CONFIGTEST.NO.KEYSTORE");
		KeyStore ds = configuration.getKeyStoreByConnector(cfg);
		Assert.assertNull(ds);
	}

	@Test(expected=ValidationException.class)
	public void testHTTPConfigNoContentType() {
		Configuration configuration = Configuration.configuration(rhCLI, tcCLI);
		Assert.assertNotNull(configuration);
		HTTPConnector cfg = (HTTPConnector) configuration.getConnectorById("HTTP.CONFIGTEST.NO.CONTENTTYPE");
		KeyStore ds = configuration.getKeyStoreByConnector(cfg);
		Assert.assertNotNull(ds);
	}
	
	
	@Test(expected=ValidationException.class)
	public void getConnectorByIdWithValidationException() {
		Configuration configuration = Configuration.configuration(rhCLI, tcCLI);
		Assert.assertNotNull(configuration);
		configuration.getConnectorById("FILEDEL.INVALID.NO.PATH");
	}


	@Test(expected=UnknownConnectorException.class)
	public void testConfigNOK() {
		Configuration configuration = Configuration.configuration(rhCLI, tcCLI);
		Assert.assertNotNull(configuration);
		configuration.getConnectorById("NOT.FOUND");
	}

	
}
