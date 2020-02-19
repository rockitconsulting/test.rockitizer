package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.cli.ResourcesHolderCLI;
import io.github.rockitconsulting.test.rockitizer.cli.TestCasesHolderCLI;
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
 * {@link Configuration#reset(ResourcesHolderCLI, TestCasesHolderCLI)}
 *
 */
public class Configuration {

	public enum RunModeTypes {
		REPLAY, RECORD
	}

	public static Logger log = Logger.getLogger(Configuration.class.getName());

	private static ResourcesHolderCLI rhCLI = new ResourcesHolderCLI();
	private static TestCasesHolderCLI tchCLI = new TestCasesHolderCLI();

	private RunModeTypes runMode = RunModeTypes.REPLAY;

	private static ResourcesHolder rh;
	private static TestCasesHolder tch;

	private static Configuration INSTANCE = null;

	private Configuration() {
		this(rhCLI, tchCLI);
	}

	/**
	 * 
	 * @param rhcli
	 * @param tchcli
	 */
	private Configuration(ResourcesHolderCLI rhcli, TestCasesHolderCLI tchcli) {
		rhCLI = rhcli;
		tchCLI = tchcli;
		try {
			log.info("#######################################################################################################################");
			log.info("initializing of configuration for the context: " + System.lineSeparator() + "\t -testcases : " + tchCLI.contextAsString()
					+ System.lineSeparator() + "\t -resources : " + rhCLI.contextAsString());
			rh = rhCLI.readResources();
			tch = tchCLI.readResources();

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

			// TODO add complete validation here
			log.info("#######################################################################################################################");

		} catch (Throwable thr) {
			log.fatal("configuration initialization exception", thr);

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
	 * @param rhcli
	 * @param tchcli
	 * @return
	 */
	public static void reset(ResourcesHolderCLI rhcli, TestCasesHolderCLI tchcli) {
		INSTANCE =  new Configuration(rhcli, tchcli);
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

	public Map<String, String> getPayloadReplacements() {
		return rh.getPayloadReplacer();

	}

	public RunModeTypes getRunMode() {
		return runMode;
	}

	public void setRunMode(RunModeTypes runMode) {
		this.runMode = runMode;
	}

	public  ResourcesHolder getResourcesHolder() {
		return rh;
	}

	public  TestCasesHolder getTestCasesHolder() {
		return tch;
	}

	public  ResourcesHolderCLI getRhCLI() {
		return rhCLI;
	}

	public  TestCasesHolderCLI getTchCLI() {
		return tchCLI;
	}

}
