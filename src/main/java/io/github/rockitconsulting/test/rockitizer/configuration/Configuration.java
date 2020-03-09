package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.MQConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;
import io.github.rockitconsulting.test.rockitizer.validation.Validatable;

import java.io.IOException;
import java.util.Map;

import com.rockit.common.blackboxtester.exceptions.FatalConfigurationException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;

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

public class Configuration {

	public enum RunModeTypes {
		REPLAY, RECORD, ASSERT
	}

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
			// TestProtocol.write( TestProtocol.getHashSeperator() );

			handleInitialization();

			// TestProtocol.write("initializing of configuration for the context: "
			// + System.lineSeparator() + "\t -testcases : " +
			// tchApi.contextAsString()
			// + System.lineSeparator() + "\t -resources : " +
			// rhApi.contextAsString());

			// TestProtocol.write( TestProtocol.getHashSeperator() );

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
			// TestProtocol.write("initializing mode from command line: " +
			// System.getProperty(Constants.MODE_KEY));

		} else {
			// TestProtocol.writeWarn("running in default " + runMode +
			// " mode, to override use the cmd: -D" + Constants.MODE_KEY +
			// "=record ");
		}
	}

	private void initEnvironmentFromSystemProperty() {
		if (System.getProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY) != null) {
			initFromYaml = false;
		}

		if (System.getProperty(Constants.ENV_KEY) != null) {
			setEnvironment(System.getProperty(Constants.ENV_KEY));
			// TestProtocol.write("initializing environment from command line: "
			// + System.getProperty(Constants.ENV_KEY));

		} else {
			setEnvironment(null);
			// TestProtocol.writeWarn("running with no environment, to override use the cmd: -D"
			// + Constants.ENV_KEY + "=<Env>");

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

			// TestProtocol.writeWarn("re-initializing of configuration for the context: "
			// + System.lineSeparator() + "\t -testcases : " +
			// tchApi.contextAsString()
			// + System.lineSeparator() + "\t -resources : " +
			// rhApi.contextAsString());

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
