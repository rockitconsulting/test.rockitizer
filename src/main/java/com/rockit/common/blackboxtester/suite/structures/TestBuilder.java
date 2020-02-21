package com.rockit.common.blackboxtester.suite.structures;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.rockit.common.blackboxtester.assertions.Assertions;
import com.rockit.common.blackboxtester.assertions.FileAssertion;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;

public class TestBuilder extends AbstractTestFolder {
	public static final Logger LOGGER = Logger.getLogger(TestBuilder.class.getName());
	private List<Assertions> assertions = Lists.newArrayList();

	public TestBuilder(Class<?> clazz) {
		super(clazz.getSimpleName());
	}

	public TestStepBuilder addStep(String stepName) {
		super.setTestStepName(stepName);

		if (isReplayMode()) {
			copyRecordToReplayTarget(stepName);
		}
		return new TestStepBuilder(getTestName(), stepName);
	}

	public TestBuilder addAssertion(Assertions assertion) {
		assertion.setRecordPath(getRecordOutputFolder());
		assertion.setReplayPath(getReplayOutputFolder());
		assertions.add(assertion);
		return this;
	}

	public void proceedAssertions() {

		// mandatory volume assertion
		assertions.add(new FileAssertion(getRecordOutputFolder(), getReplayOutputFolder()));
		assertions.add(new FileAssertion(getReplayOutputFolder(), getRecordOutputFolder()));

		for (Assertions assertion : assertions) {
			assertion.proceed();
		}

		LOGGER.info(" Number of assertions processed successfully: " + assertions.size() + " [\n\t" + Joiner.on(",\n\t").join(assertions) + "\n      ]");
	}

	public void deleteOutputFolder() throws IOException {
		if (!isReplayMode()) {
			TestProtocol.write(getTestStepName() + "\t Deleting record output folder " + getRecordOutputFolder());
			FileUtils.deleteDirectory(new File(getRecordOutputFolder()));
		} else {
			TestProtocol.write(getTestStepName() + "\t Deleting replay  folder " + getReplayFolder());
			FileUtils.deleteDirectory(new File(getReplayFolder()));
		}
	}

	/**
	 * @return /src/test/TestName/output
	 */
	protected String getRecordOutputFolder() {
		return getRecordFolder() + Constants.OUTPUT_FOLDER;
	}

	/**
	 * @return /target/replay/TestName/output
	 */
	protected String getReplayOutputFolder() {
		return getReplayFolder() + Constants.OUTPUT_FOLDER;
	}

	/**
	 * @return /src/test/TestName
	 */
	public String getRecordFolder() {
		return getRecordRootPath() + getInFolderName();
	}

	/**
	 * @return /target/replay/TestName
	 */
	public String getReplayFolder() {
		return getReplayRootPath() + getInFolderName();
	}

	private void copyRecordToReplayTarget(String stepName) {

		if (new File(getRecordFolder() + "/" + stepName).exists() == false) {
			new File(getRecordFolder() + "/" + stepName).mkdirs();
		}
		new File(getReplayFolder() + "/" + stepName).mkdirs();

		try {

			FileUtils.copyDirectory(new File(getRecordFolder() + "/" + stepName), new File(getReplayFolder() + "/" + "/" + stepName));

		} catch (IOException e) {

			LOGGER.error("Can not make copy of " + getRecordFolder() + "/" + stepName + " to replay target " + getReplayFolder() + "/" + stepName);
			throw new GenericException(e);
		}

		LOGGER.info(getTestStepName() + "\t Copying " + Constants.RECORD_PATH + getTestName() + "/" + getTestStepName() + " to " + Constants.REPLAY_PATH
				+ getTestName() + "/" + getTestStepName());
	}

	public boolean isRecordFolderExists() {
		return new File(this.getRecordFolder()).exists();
	}

}
