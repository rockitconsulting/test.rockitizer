package com.rockit.common.blackboxtester.suite.structures;

import static com.rockit.common.blackboxtester.suite.configuration.SettingsHolder.cache;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.rockit.common.blackboxtester.connector.settings.Settings;
import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;


/**
*  Test.Rockitizer - API regression testing framework 
*   Copyright (C) 2020  rockit.consulting GmbH
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see http://www.gnu.org/licenses/.
*
*/

public class TestStepBuilder extends AbstractTestFolder {
	private Iterable<ConnectorFolder> connectorFolders;

	public TestStepBuilder(final String testName, String testStepName) {
		super(testName, testStepName);
		TestProtocol.writeStep(testStepName);
	}

	public TestStepBuilder execute() {
		if(this.isAssertMode()) {
			return this;
		}
		
		try {
			for (final ConnectorFolder connectorFolder : getConnectorFolders()) {
				connectorFolder.execute();
			}
		} catch (final Exception ex) {
			TestProtocol.writeFatal(ex);
			throw ex; //re-throw
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
		if(this.isAssertMode()) {
			return this;
		}
		
		
		TestProtocol.write(" Waiting " + ms + "ms" + " for results");
		try {
			Thread.sleep(ms);
		} catch (final InterruptedException e) {
			TestProtocol.writeError("thread is interrupted " + Thread.currentThread().getName(), e);
			Thread.currentThread().interrupt();
		}
		return this;
	}
}
