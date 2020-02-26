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
package com.rockit.common.blackboxtester.assertions.fixedlength;

import static com.rockit.common.blackboxtester.assertions.FileAssertion.LOGGER;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.assertions.AbstractAssertion;

/**
 *
 * @author muellerw
 */
public class FixedLengthFileAssertion extends AbstractAssertion {

	private String step = "";

	protected List<String> recorded = new ArrayList<>();
	protected List<String> replayed = new ArrayList<>();

	private RecordConfig recordConfig = null;
	private final DifferenceBuilder db = new DifferenceBuilder(this);

	public FixedLengthFileAssertion(String recordPath, String replayPath, String step, String recordConfigString) {
		setRecordPath(recordPath);
		setReplayPath(replayPath);
		recordConfig = RecordsConfig.getInstance().getRecordConfig(recordConfigString);
		this.step = step;
	}

	@Override
	public void proceed() {
		File recordFolder = new File(recordPath + File.separator + step);
		File replayFolder = new File(replayPath + File.separator + step);

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

	public String getStep() {
		return step;
	}

	public List<String> getRecorded() {
		return recorded;
	}

	public List<String> getReplayed() {
		return replayed;
	}

	public RecordConfig getRecordConfig() {
		return recordConfig;
	}

}
