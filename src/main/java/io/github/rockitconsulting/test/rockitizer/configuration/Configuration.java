package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.model.EnvironmentsHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.MQConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;
import io.github.rockitconsulting.test.rockitizer.exceptions.UnknownEnvironmentException;
import io.github.rockitconsulting.test.rockitizer.validation.Validatable;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.rockit.common.blackboxtester.exceptions.FatalConfigurationException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;
import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;

public class Configuration {

	public enum RunModeTypes {
		REPLAY, RECORD, ASSERT
	}

	private static EnvironmentsHolderAccessor ehaApi;
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

			handleInitialization();

		} catch (Throwable thr) {
			TestProtocol.writeError("configuration initialization exception", thr);
			throw new FatalConfigurationException("configuration initialization exception", thr);

		}

	}

	private void handleInitialization() throws IOException {
		initEnvironmentFromSystemProperty();

		if (initFromYaml) {
			initAndLogRunModeFromSystemProperty();
			rhApi.initFromYaml();
			tchApi.initFromYaml();
		} else {
			// TestProtocol.writeWarn(" running in CLI configuration generation mode"
			// );
			// TODO this part of code is only cli relevant. evaluate either we
			// need to create the empty env.yaml with the cli
			rhApi.initFromFileSystem();
			tchApi.initFromFileSystem();
		}
	}

	private void initAndLogRunModeFromSystemProperty() {
		if (System.getProperty(Constants.MODE_KEY) != null) {
			if (System.getProperty(Constants.MODE_KEY).equalsIgnoreCase("replay")) {
				setRunMode(RunModeTypes.REPLAY);
			} else if (System.getProperty(Constants.MODE_KEY).equalsIgnoreCase("assert")) {
				setRunMode(RunModeTypes.ASSERT);
			} else {
				setRunMode(RunModeTypes.RECORD);
			}
		}
	}

	private void initEnvironmentFromSystemProperty() {
		if (System.getProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY) != null) {
			initFromYaml = false;
		}

		if (System.getProperty(Constants.ENV_KEY) != null) {
			setEnvironment(System.getProperty(Constants.ENV_KEY));

		} else {
			setEnvironment(null);

		}
	}

	public static synchronized Configuration configuration() {
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
	public static synchronized void reset(ResourcesHolderAccessor rhcli, TestCasesHolderAccessor tchcli) {
		INSTANCE = new Configuration(rhcli, tchcli);
	}

	/**
	 * Re-initialize config from yaml keeping context
	 */
	public void reinit() {
		try {

			handleInitialization();

		} catch (Throwable thr) {
			TestProtocol.writeError("configuration initialization exception", thr);
			throw new FatalConfigurationException("configuration initialization exception", thr);

		}

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
		return getTchApi().getFullPath();
	}

	public String getEnvironment() {
		return environment;
	}

	/**
	 * Please use the System.setProperty(Constants.ENV_KEY, env ) for proper
	 * initialisation
	 * 
	 * @param environment
	 * @throws Exception 
	 */
	private void setEnvironment(String environment) {
		this.environment = environment;
		ehaApi = new EnvironmentsHolderAccessor();
		EnvironmentsHolder eh = ehaApi.getEnvsHolder();
		if (eh != null) {
			if (eh.isValid()) {
				handleEnvironmentVariables(environment);
			} else {
				TestProtocol.writeWarn("env.yaml file for environment variables exists, but not valid replacements found. Please proof validity of env.yaml. Fallback to using default resources.yaml." + System.lineSeparator());
			}
		} else {
			TestProtocol.writeWarn("env.yaml file for environment variables not found. Fallback to using default resources.yaml" + System.lineSeparator());
		}
	}

	void handleEnvironmentVariables(String environment) {
		Map<String, String> props = ehaApi.getEnvsHolder().getProps(environment);
		if (props != null) {
			interpolate(props);
		} else {
			throw new UnknownEnvironmentException(environment);
		}
	}

	private void interpolate(Map<String, String> props) {
		File resultResourcesEnv = PayloadReplacer.interpolate(new File(rhApi.getFullPath() + rhApi.getResourcesFileName()), props);
		rhApi.setEnvResourcesFile(resultResourcesEnv);
	}

	public Validatable getConnectorById(String id) {
		return getRhApi().getConnectorById(id);
	}

	public DBDataSource getDBDataSourceByConnector(DBConnectorCfg cfg) {
		return getRhApi().getDBDataSourceByConnector(cfg);
	}

	public MQDataSource getMQDataSourceByConnector(MQConnectorCfg cfg) {
		return getRhApi().getMQDataSourceByConnector(cfg);

	}

	public KeyStore getKeyStoreByConnector(HTTPConnectorCfg cfg) {
		return getRhApi().getKeyStoreByConnector(cfg);

	}

	public Map<String, String> getPayloadReplacements() {
		return getRhApi().getResourcesHolder().getPayloadReplacer();

	}

}
