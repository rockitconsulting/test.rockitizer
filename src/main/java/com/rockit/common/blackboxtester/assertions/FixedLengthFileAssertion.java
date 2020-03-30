/**
 * AS-PLM
 *
 * $Id:  $
 * $HeadURL:  $
 * $LastChangedRevision:  $
 * $LastChangedDate:  $
 * $LastChangedBy:  $
 *
 * Asserts two files with fixed length lines
 * Configuration Example "12;2;5;-3;6;-2;1"
 * Meins
 *   the first  Record has length of 12 characters compared,
 *   the fourth Record has length of 3  characters don not compare
 */
package com.rockit.common.blackboxtester.assertions;

import static com.rockit.common.blackboxtester.assertions.FileAssertion.LOGGER;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.assertions.fixedlength.DifferenceBuilder;
import com.rockit.common.blackboxtester.assertions.fixedlength.RecordConfig;
import com.rockit.common.blackboxtester.assertions.fixedlength.RecordsConfig;

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

public class FixedLengthFileAssertion extends AbstractAssertion {

	protected List<String> recorded = new ArrayList<>();
	protected List<String> replayed = new ArrayList<>();

	private RecordConfig recordConfig = null;
	private final DifferenceBuilder db = new DifferenceBuilder(this);

	/**
	 * Reads record config and assigns value to relative path attribute as a test step and a connector.
	 * @param step
	 * @param connector
	 * @param config
	 */
	public FixedLengthFileAssertion(String step, String connector, String config) {
		recordConfig = RecordsConfig.getInstance().getRecordConfig(config);
		this.relPath = File.separator + step + File.separator + connector;
	}
	
	/**
	 * Reads record config and assigns value to relative path attribute as a test step.
	 * @param step
	 * @param config
	 */
	public FixedLengthFileAssertion(String step, String config) {
		recordConfig = RecordsConfig.getInstance().getRecordConfig(config);
		this.relPath = File.separator + step;
	}

	/**
	 * @see com.rockit.common.blackboxtester.assertions.Assertions#proceed()
	 */
	@Override
	public void proceed() {
		File recordFolder = new File(recordPath + getRelPath());
		File replayFolder = new File(replayPath + getRelPath());

		for (File recordFile : Files.fileTraverser().depthFirstPreOrder(recordFolder)) {
			if (recordFile.isFile()) {
				String relativePath = recordFolder.toURI().relativize(recordFile.toURI()).getPath();
				String replayFilePath = replayFolder + File.separator + relativePath;
				recorded = fileToLineList(recordFile.getAbsolutePath());
				replayed = fileToLineList(replayFilePath);
				db.build(recordFile.getName());
				LOGGER.debug("fixedlength recorded content " + recordFile.getPath() + " with replayed " + replayFilePath);
			}
		}
		db.doAssert();
	}

	/**
	 * Writes file contents to a string.
	 * @param filePath
	 * @return
	 */
	protected static List<String> fileToLineList(String filePath) {
		File fileToExtract = new File(filePath);
		List<String> lineList = new ArrayList<>();
		if (fileToExtract.isFile()) {

			try (Scanner sc = new Scanner(fileToExtract)) {

				while (sc.hasNextLine()) {
					lineList.add(sc.nextLine());
				}
			} catch (Exception e) {
				LOGGER.error("Error occured: ", e);
			}
		}

		return lineList;
	}

	/**
	 * Returns value of record status.
	 * @return
	 */
	public List<String> getRecorded() {
		return recorded;
	}

	/**
	 * Returns value of replay status.
	 * @return
	 */
	public List<String> getReplayed() {
		return replayed;
	}

	/**
	 * Returns value of recordConfig status.
	 * @return
	 */
	public RecordConfig getRecordConfig() {
		return recordConfig;
	}


}
