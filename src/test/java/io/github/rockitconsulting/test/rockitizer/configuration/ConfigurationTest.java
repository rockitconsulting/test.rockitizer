package io.github.rockitconsulting.test.rockitizer.configuration;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.cli.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.MQConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;
import io.github.rockitconsulting.test.rockitizer.exceptions.UnknownConnectorException;
import io.github.rockitconsulting.test.rockitizer.exceptions.ValidationException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ConfigurationTest {

	public static Logger log = Logger.getLogger(ConfigurationTest.class.getName());


	@Before
	public void before() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName());
		Assert.assertNotNull(configuration());
		Assert.assertEquals(configuration().getTchCLI().getTestcasesFileName(), "ConfigurationTest-testcases.yaml");
		Assert.assertEquals(configuration().getRhCLI().getResourcesFileName(), "ConfigurationTest-resources.yaml");
		
		System.out.println(" --> "+ configuration().getRhCLI().getFullPath() );
		System.out.println(" --> "+ configuration().getRhCLI().getResourcesFileName() );
		
		configuration().getResourcesHolder().getHttpConnectors().forEach( c -> System.out.println(c.toString())  );
		configuration().getResourcesHolder().getKeyStores().forEach( c -> System.out.println(c.toString())  );

		
	}
	
	@Test
	public void testDBConfigWithDataSourceOK() {
		DBConnector dbConCfg = (DBConnector) configuration().getConnectorById("DBPUT.CLAEN");
		DBDataSource ds = configuration().getDBDataSourceByConnector(dbConCfg);
		Assert.assertNotNull(ds);
	}
	
	@Test
	public void testMQConfigWithMQDataSourceOK() {
		MQConnector cfg = (MQConnector) configuration().getConnectorById("MQGET.OUT.MQ2MQ");
		MQDataSource ds = configuration().getMQDataSourceByConnector(cfg);
		Assert.assertNotNull(ds);
	}
	
	@Test
	public void testHTTPConfigWithKeyStoreOK() {
	
		HTTPConnector cfg = (HTTPConnector) configuration().getConnectorById("HTTP.CONFIGTEST.WITH.KEYSTORE");
		KeyStore ds = configuration().getKeyStoreByConnector(cfg);
		Assert.assertNotNull(ds);
	}
	

	@Test
	public void testHTTPConfigWithoutKeyStoreOK() {
		HTTPConnector cfg = (HTTPConnector) configuration().getConnectorById("HTTP.CONFIGTEST.NO.KEYSTORE");
		KeyStore ds = configuration().getKeyStoreByConnector(cfg);
		Assert.assertNull(ds);
	}

	@Test(expected=ValidationException.class)
	public void testHTTPConfigNoContentType() {
		HTTPConnector cfg = (HTTPConnector) configuration().getConnectorById("HTTP.CONFIGTEST.NO.CONTENTTYPE");
		KeyStore ds = configuration().getKeyStoreByConnector(cfg);
		Assert.assertNotNull(ds);
	}
	
	
	@Test(expected=ValidationException.class)
	public void getConnectorByIdWithValidationException() {
		configuration().getConnectorById("FILEDEL.INVALID.NO.PATH");
	}


	@Test(expected=UnknownConnectorException.class)
	public void testConfigNOK() {
		configuration().getConnectorById("NOT.FOUND");
	}

	
}
