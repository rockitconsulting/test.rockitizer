package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.MQConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import io.github.rockitconsulting.test.rockitizer.validation.Validatable;

import java.util.Map;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.exceptions.FatalConfigurationException;
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

	private static Configuration INSTANCE = null;

	private RunModeTypes runMode = RunModeTypes.REPLAY;

	private String environment;
	
	private boolean initFromYaml = true;

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
			LogUtils.LogInfo("#######################################################################################################################");

			initEnvironmentFromSystemProperty();
			
			if(initFromYaml) {
				initAndLogRunModeFromSystemProperty();
				rhApi.initFromYaml();
				tchApi.initFromYaml();
			} else { 
				LogUtils.LogWarn(" running in CLI configuration generation mode" );
				rhApi.initFromFileSystem();
				tchApi.initFromFileSystem();
			}
			


			LogUtils.LogInfo("initializing of configuration for the context: " + System.lineSeparator() + "\t -testcases : " + tchApi.contextAsString()
					+ System.lineSeparator() + "\t -resources : " + rhApi.contextAsString());


			// TODO add complete validation here
			LogUtils.LogInfo("#######################################################################################################################");

		} catch (Throwable thr) {
			log.error("configuration initialization exception", thr);
			throw new FatalConfigurationException(thr.getMessage());

		}

	}

	private void initAndLogRunModeFromSystemProperty() {
		if (System.getProperty(Constants.MODE_KEY) != null
				&& (System.getProperty(Constants.MODE_KEY).equalsIgnoreCase(RunModeTypes.REPLAY.name()) || System.getProperty(Constants.MODE_KEY)
						.equalsIgnoreCase(RunModeTypes.RECORD.name()))) {

			if (System.getProperty(Constants.MODE_KEY).equalsIgnoreCase("replay")) {
				setRunMode(RunModeTypes.REPLAY);
			} else {
				setRunMode(RunModeTypes.RECORD);
			}
			LogUtils.LogInfo("initializing mode from command line: " + System.getProperty(Constants.MODE_KEY));

		} else {
			LogUtils.LogWarn("running in default " + runMode + " mode, to override use the cmd: -D" + Constants.MODE_KEY + "=record ");
		}
	}

	private void initEnvironmentFromSystemProperty() {
		if( System.getProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY) !=null ) {
			initFromYaml = false;
		}
		
		
		if (System.getProperty(Constants.ENV_KEY) != null) {
			setEnvironment(System.getProperty(Constants.ENV_KEY));
			LogUtils.LogInfo("initializing environment from command line: " + System.getProperty(Constants.ENV_KEY));

		} else {
			setEnvironment(null);
			LogUtils.LogWarn("running with no environment, to override use the cmd: -D" + Constants.ENV_KEY + "=<Env>");

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

	public RunModeTypes getRunMode() {
		return runMode;
	}

	public void setRunMode(RunModeTypes runMode) {
		this.runMode = runMode;
	}

	public ResourcesHolderAccessor getRhApi() {
		return rhApi;
	}

	public TestCasesHolderAccessor getTchApi() {
		return tchApi;
	}

	public String getFullPath() {
		return getRhApi().getFullPath();
	}

	public String getEnvironment() {
		return environment;
	}

	/**
	 * Please use the System.setProperty(Constants.ENV_KEY, env ) for proper
	 * initialisation
	 * 
	 * @param environment
	 */
	private void setEnvironment(String environment) {
		this.environment = environment;
		if (environment != null) {
			rhApi.setResourcesFileName("resources-" + environment + ".yaml");
		}
	}

	public Validatable getConnectorById(String id) {
		return getRhApi().getConnectorById(id);
	}

	public DBDataSource getDBDataSourceByConnector(DBConnector cfg) {
		return getRhApi().getDBDataSourceByConnector(cfg);
	}

	public MQDataSource getMQDataSourceByConnector(MQConnector cfg) {
		return getRhApi().getMQDataSourceByConnector(cfg);

	}

	public KeyStore getKeyStoreByConnector(HTTPConnector cfg) {
		return getRhApi().getKeyStoreByConnector(cfg);

	}

	public Map<String, String> getPayloadReplacements() {
		return getRhApi().getResourcesHolder().getPayloadReplacer();

	}

}
