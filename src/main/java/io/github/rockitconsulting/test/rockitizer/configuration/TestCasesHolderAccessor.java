package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.ConnectorRef;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.Payload;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestCase;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestStep;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

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

public class TestCasesHolderAccessor extends RuntimeContext {

	public static final Logger log = Logger.getLogger(TestCasesHolderAccessor.class.getName());

	private String testcasesFileName = "testcases.yaml";

	private TestCasesHolder testCasesHolder;

	void initFromYaml() throws IOException {
		testCasesHolder = testCasesHolderFromYaml();
	}

	void initFromFileSystem() throws IOException {
		testCasesHolder = testCasesHolderFromFileSystemToYaml();
	}

	/**
	 * Reads yam configuration into the holder object CLI relevant: instantiate
	 * Resources from yaml
	 * 
	 * @return
	 * @throws IOException
	 */
	public TestCasesHolder testCasesHolderFromYaml() throws IOException {
		return ConfigUtils.testCasesHolderFromYaml(getFullPath() + getTestcasesFileName());
	}

	/**
	 * Generates the testcases structure from filesystem
	 * 
	 * @return
	 */
	public TestCasesHolder testCasesHolderFromFileSystem() {
		TestCasesHolder holder = new TestCasesHolder();

		FileUtils.listFolders(new File(getFullPath())).forEach(tcFolder -> {
			TestCase testCase = new TestCase(tcFolder);
			holder.getTestCases().add(testCase);

			FileUtils.listFolders(tcFolder).forEach(tsFolder -> {
				TestStep testStep = new TestStep(tsFolder);
				if (testStep.getTestStepName().equalsIgnoreCase(Constants.OUTPUT_FOLDER)) {
					return;
				}
				testCase.getTestSteps().add(testStep);

				FileUtils.listFolders(tsFolder).forEach(connFolder -> {
					ConnectorRef connectorRef = new ConnectorRef(connFolder);
					FileUtils.listFiles(connFolder).forEach(payload -> {
						if (!payload.getName().equals(Constants.GITIGNORE)) {
							connectorRef.getPayloads().add(new Payload(payload));
						}
					});
					testStep.getConnectorRefs().add(connectorRef);
				});
			});
		});

		return holder;
	}

	public void testCasesHolderToYaml(TestCasesHolder holder) throws IOException {
		ConfigUtils.writeModelObjToYaml(holder, getFullPath() + getTestcasesFileName());
	}

	/**
	 * Creates config file &lt;testcase.yaml&gt; from TestCaseHolder
	 * 
	 * @return
	 * @throws IOException
	 */
	public TestCasesHolder testCasesHolderFromFileSystemToYaml() throws IOException {
		TestCasesHolder holder = testCasesHolderFromFileSystem();
		testCasesHolderToYaml(holder);
		return holder;
	}

	public String getTestcasesFileName() {
		return testcasesFileName;
	}

	public void setTestcasesFileName(String testcasesFileName) {
		this.testcasesFileName = testcasesFileName;
	}

	public String contextAsString() {
		return "[ filename: " + getTestcasesFileName() + ", absPath: " + getAbsolutePath() + ", relPath: " + getRelativePath() + "]";

	}

	public TestCasesHolder getTestCasesHolder() {
		return testCasesHolder;
	}

	public void setTestCasesHolder(TestCasesHolder testCasesHolder) {
		this.testCasesHolder = testCasesHolder;
	}
}
