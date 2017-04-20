package com.rockit.common.blackboxtester.suite.structures;

import static com.rockit.common.blackboxtester.suite.configuration.SettingsHolder.cache;

import java.io.File;

import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.rockit.common.blackboxtester.connector.settings.Settings;
import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;
import com.rockit.common.blackboxtester.util.FileUtils;

/**
 * @author rockit3lp
 *
 */
public class TestStepBuilder extends AbstractTestFolder {
	public static final Logger LOGGER = Logger.getLogger(TestStepBuilder.class.getName());
	private Iterable<ConnectorFolder> connectorFolders;

	public TestStepBuilder(final String testName, String testStepName) {
		super(testName, testStepName);
		TestProtocol.writeStep(testStepName);
	}

	public TestStepBuilder execute() {
		try {
			for (final ConnectorFolder connectorFolder : getConnectorFolders()) {
				connectorFolder.execute();
			}
		} catch (final Exception ex) {
			TestProtocol.writeFatal(ex);
			System.exit(1);
		}
		return this;
	}

	protected Iterable<ConnectorFolder> getConnectorFolders() {
		if (null == connectorFolders) {
			connectorFolders = Iterables.transform(FileUtils.listFolders(getInFolder()),
					new Function<File, ConnectorFolder>() {
						@Override
						public ConnectorFolder apply(final File subfolder) {
							return new ConnectorFolder(getTestName(), getTestStepName(), subfolder.getName() );
						}
					});
		}
		return connectorFolders;
	}

	protected String relativize(final String path) {
		return new File(getBasePath()).toURI().relativize(new File(path).toURI()).getPath();
	}

	public TestStepBuilder addSettings(final String connectorName, final Settings settings) {
		cache().put(connectorName, settings);
		return this;
	}

	public TestStepBuilder sleep(final int ms) {
		LOGGER.info(" Waiting " + ms + "ms" + " for results");
		try {
			Thread.sleep(ms);
		} catch (final InterruptedException e) {
			LOGGER.error("thread is interrupted " + Thread.currentThread().getName(), e);
			Thread.currentThread().interrupt();
		}
		return this;
	}
}
