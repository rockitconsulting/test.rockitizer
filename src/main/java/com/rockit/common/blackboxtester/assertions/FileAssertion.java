package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.log4j.Logger;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.exceptions.AssertionException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

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

public class FileAssertion extends AbstractAssertion {

	public static final Logger LOGGER = Logger.getLogger(FileAssertion.class.getName());

	/**
	 * always on assertion service class for mirror check of record and replay
	 * path.
	 * 
	 * @param recordPath
	 * @param replayPath
	 */
	public FileAssertion(String recordPath, String replayPath) {
		setRecordPath(recordPath);
		setReplayPath(replayPath);
	}

	/**
	 * 
	 * @see com.rockit.common.blackboxtester.assertions.Assertions#proceed()
	 */
	@Override
	public void proceed() {
		File recordFolder = new File(recordPath);
		File replayFolder = new File(replayPath);

		for (File recordFile : Files.fileTraverser().depthFirstPreOrder(recordFolder)) {

			String relativePath = recordFolder.toURI().relativize(recordFile.toURI()).getPath();
			File replayFile = new File(replayFolder + "/" + relativePath);

			if (replayFile.getName().equalsIgnoreCase(Constants.GITIGNORE) || recordFile.getName().equalsIgnoreCase(Constants.GITIGNORE)) {
				continue;
			}

			LOGGER.debug("asserting: source " + recordFile.getAbsolutePath() + " with target " + replayFile.getAbsolutePath());

			try {
				assertTrue(" matching file " + replayFile.getAbsolutePath() + " must exist", replayFile.exists() == true);
			} catch (AssertionError e) {
				throw new AssertionException(" matching file " + replayFile.getAbsolutePath() + " must exist ", e);
			}

		}
	}
}
