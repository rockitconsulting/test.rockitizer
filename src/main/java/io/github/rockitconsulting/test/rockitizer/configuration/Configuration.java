package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.MQConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.ConnectorRef;
import io.github.rockitconsulting.test.rockitizer.exceptions.ResourceNotFoundException;
import io.github.rockitconsulting.test.rockitizer.exceptions.ValidationException;
import io.github.rockitconsulting.test.rockitizer.validation.Validatable;

import java.util.Map;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

/**
 * Configuration singleton accessable over primary
 * {@link Configuration#configuration()} and junit helper
 * {@link Configuration#reset(ResourcesHolderAccessor, TestCasesHolderAccessor)}
 *
 */
public class Configuration {

	public enum RunModeTypes {
		REPLAY, RECORD
	}

	public static Logger log = Logger.getLogger(Configuration.class.getName());

	private static ResourcesHolderAccessor rhApi;
	private static TestCasesHolderAccessor tchApi;

	private static ResourcesHolder rh;
	private static TestCasesHolder tch;

	private static Configuration INSTANCE = null;

	private RunModeTypes runMode = RunModeTypes.REPLAY;

	private String environment;

	private Configuration() {
		this(new ResourcesHolderAccessor(), new TestCasesHolderAccessor());
	}

	/**
	 * 
	 * @param rhcli
	 * @param tchcli
	 */
	private Configuration(ResourcesHolderAccessor rhcli, TestCasesHolderAccessor tchcli) {
		rhApi = rhcli;
		tchApi = tchcli;
		try {
			log.info("#######################################################################################################################");
			
			initEnvironmentFromSystemProperty();
			initRunModeFromSystemProperty();

			
			log.info("initializing of configuration for the context: " + System.lineSeparator() + "\t -testcases : " + tchApi.contextAsString()
					+ System.lineSeparator() + "\t -resources : " + rhApi.contextAsString());
			rh = rhApi.resourcesHolderFromYaml();
			tch = tchApi.testCasesHolderFromYaml();


			// TODO add complete validation here
			log.info("#######################################################################################################################");

		} catch (Throwable thr) {
			log.fatal("configuration initialization exception", thr);

		}

	}

	private void initRunModeFromSystemProperty() {
		if (System.getProperty(Constants.MODE_KEY) != null
				&& (System.getProperty(Constants.MODE_KEY).equalsIgnoreCase(RunModeTypes.REPLAY.name()) || System.getProperty(Constants.MODE_KEY)
						.equalsIgnoreCase(RunModeTypes.RECORD.name()))) {

			if (System.getProperty(Constants.MODE_KEY).equalsIgnoreCase("replay")) {
				setRunMode(RunModeTypes.REPLAY);
			} else {
				setRunMode(RunModeTypes.RECORD);
			}
			log.info("initializing mode from command line: " + System.getProperty(Constants.MODE_KEY));

		} else {
			log.warn("running in default " + runMode + " mode, to override use the cmd: -D" + Constants.MODE_KEY + "=record ");
		}
	}

	
	private void initEnvironmentFromSystemProperty() {
		if (System.getProperty(Constants.ENV_KEY)!=null) {
			setEnvironment(System.getProperty(Constants.ENV_KEY));
			log.info("initializing environment from command line: " + System.getProperty(Constants.ENV_KEY));

		} else {
			setEnvironment(null);
			log.warn("running with no environment, to override use the cmd: -D" + Constants.ENV_KEY + "=<Env>");

		}
	}


	public static Configuration configuration() {
		if (INSTANCE == null) {
			INSTANCE = new Configuration();
		}
		return INSTANCE;
	}

	/**
	 * For test support generate always new instance, no caching
	 * 
	 * @param rhcli
	 * @param tchcli
	 * 
	 */
	public static void reset(ResourcesHolderAccessor rhcli, TestCasesHolderAccessor tchcli) {
		INSTANCE = new Configuration(rhcli, tchcli);
	}

	public static void reset() {
		INSTANCE = new Configuration();
	}

	
	public Validatable getConnectorById(String id) {
		Validatable c = (Validatable) rh.findResourceByRef(new ConnectorRef(id));
		if (c == null) {
			throw new ResourceNotFoundException(id);
		}
		if (!c.isValid()) {
			throw new ValidationException(c.validate());
		}
		return c;
	}

	public DBDataSource getDBDataSourceByConnector(DBConnector connector) {
		DBDataSource ds = rh.findDBDataSourceById(connector.getDsRefId());
		if (ds == null) {
			throw new ResourceNotFoundException(connector.getDsRefId());
		}

		if (!ds.isValid()) {
			throw new ValidationException(ds.validate());
		}
		return ds;
	}

	public MQDataSource getMQDataSourceByConnector(MQConnector connector) {
		MQDataSource ds = rh.findMQDataSourceById(connector.getDsRefId());
		if (!ds.isValid()) {
			throw new ValidationException(ds.validate());
		}
		return ds;
	}

	public KeyStore getKeyStoreByConnector(HTTPConnector connector) {
		KeyStore ds = rh.findKeyStoreById(connector.getDsRefId());
		if (ds != null && !ds.isValid()) {
			throw new ValidationException(ds.validate());
		}
		return ds;
	}

	public RunModeTypes getRunMode() {
		return runMode;
	}

	public void setRunMode(RunModeTypes runMode) {
		this.runMode = runMode;
	}

	public ResourcesHolder getResourcesHolder() {
		return rh;
	}

	public TestCasesHolder getTestCasesHolder() {
		return tch;
	}

	public ResourcesHolderAccessor getRhApi() {
		return rhApi;
	}

	public TestCasesHolderAccessor getTchApi() {
		return tchApi;
	}

	public Map<String, String> getPayloadReplacements() {
		return rh.getPayloadReplacer();

	}

	public String getFullPath() {
		return getRhApi().getFullPath();
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
		if(environment!=null) {
			rhApi.setResourcesFileName("resources-"+environment+".yaml");
		}
	}

}
