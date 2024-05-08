package com.rockit.common.blackboxtester.wrapper;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;
import com.rockit.common.blackboxtester.suite.structures.TestBuilder;

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

public abstract class AbstractTestWrapper {

	private TestBuilder testBuilder;
	public static final Logger LOGGER = Logger.getLogger(AbstractTestWrapper.class.getName());

	@Before
	public void preProcess() throws IOException {

		TestProtocol.writeHeading(testBuilder.getTestName(), "Configuration");
		TestProtocol.write(testBuilder);

		TestProtocol.writeHeading(testBuilder.getTestName(), "Executing  [" + configuration().getRunMode() + " for Env: " + ( configuration().getEnvironment()!=null?configuration().getEnvironment():"default" )  +  "]" + testBuilder.printDescription() );

		if (!testBuilder.isRecordFolderExists()) {
			TestProtocol.writeError("Record folder doesn't exist: " + testBuilder.getRecordFolder());
			System.exit(1);
		}

		if (!testBuilder.isAssertMode()) {
			testBuilder.addStep(Constants.BEFORE_FOLDER).execute();
			testBuilder.deleteOutputFolder();
		}

	}

	@After
	public void postProcess() {

		if (!testBuilder.isAssertMode()) {
			testBuilder.addStep(Constants.AFTER_FOLDER).execute();
		}

		
		if (testBuilder.isRecordMode()) { 
			TestProtocol.write("You are running in 'record' mode. Assertions are only possible in 'replay' or 'assert' modes. Kindly use cli run or set the 'mode' parameter for JUnit directly.");
			return;
		} else {
			TestProtocol.writeHeading(testBuilder.getTestName(), "Assertion");
			testBuilder.proceedAssertions();
		}
	}

	public TestBuilder newTestBuilderFor(Class<? extends AbstractTestWrapper> clazz) {
		configuration();
		testBuilder = new TestBuilder(clazz);
		return testBuilder;
	}
}
