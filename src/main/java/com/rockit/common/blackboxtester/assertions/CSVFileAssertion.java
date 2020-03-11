package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.rockit.common.blackboxtester.exceptions.AssertionException;

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

public class CSVFileAssertion extends AbstractAssertion {

	public static final Logger LOGGER = Logger.getLogger(CSVFileAssertion.class.getName());

	private String separator;

	private List<Integer> ignoreIndexes;

	

	@SuppressWarnings("unused")
	private CSVFileAssertion() {
	}

	/**
	 * CSV assertion, including an optional separator and ignoreTokens
	 * functionality
	 * 
	 * @param step
	 */
	public CSVFileAssertion(String step) {
		this.relPath = File.separator + step;
	}

	/**
	 * CSV assertion, including an optional separator and ignoreTokens
	 * functionality
	 * 
	 * @param step
	 * @param connector
	 */
	public CSVFileAssertion(String step, String connector) {
		this.relPath = File.separator + step + File.separator + connector;
	}

	@Override
	public void proceed() {
		File recordFolder = new File(recordPath + getRelPath());
		File replayFolder = new File(replayPath + getRelPath());

		for (File recordFile : Files.fileTraverser().depthFirstPreOrder(recordFolder)) {

			String relativePath = recordFolder.toURI().relativize(recordFile.toURI()).getPath();
			File replayFile = new File(replayFolder + "/" + relativePath);

			LOGGER.debug("asserting: source " + recordFile.getAbsolutePath() + " with target " + replayFile.getAbsolutePath());

			try {
				if (!recordFile.isDirectory() && !replayFile.isDirectory()) {
					csvAssert(recordFile, replayFile);
				}
			} catch (AssertionError e) {
				throw new AssertionException(" csv files doesn't match each other " + replayFile.getAbsolutePath() + " / " + recordFile.getAbsolutePath(), e);
			}

		}
	}

	void csvAssert(File f1, File f2) {

		assertTrue(f1.getAbsolutePath() + " not exists", f1.exists());
		assertTrue(f2.getAbsolutePath() + " not exists", f2.exists());

		try {
			Set<String> f1Lines = new HashSet<String>(FileUtils.readLines(f1));
			Set<String> ff1Lines = new HashSet<String>(f1Lines);
			Set<String> f2Lines = new HashSet<String>(FileUtils.readLines(f2));
			Set<String> ff2Lines = new HashSet<String>(f2Lines);

			if (separator != null && ignoreIndexes != null && !ignoreIndexes.isEmpty()) {
				f1Lines = removeIgnored(f1Lines);
				ff1Lines = removeIgnored(ff1Lines);
				f2Lines = removeIgnored(f2Lines);
				ff2Lines = removeIgnored(ff2Lines);
			}

			f1Lines.removeAll(ff2Lines);
			f2Lines.removeAll(ff1Lines);
			assertTrue("\n " + f1.getAbsolutePath() + " diff: \n " + Joiner.on("\n").join(f1Lines) + "\n-----------------\n" + f2.getAbsolutePath()
					+ " diff: \n " + Joiner.on("\n").join(f2Lines), f2Lines.isEmpty() && f1Lines.isEmpty());
		} catch (IOException e) {
			LOGGER.error("asserting: " + f1.getAbsolutePath() + " with target " + f2.getAbsolutePath(), e);
			throw new AssertionError("asserting: " + f1.getAbsolutePath() + " with target " + f2.getAbsolutePath(), e);
		}
	}

	/**
	 * Remove the ignored tokens
	 * 
	 * @param lines
	 */
	Set<String> removeIgnored(Set<String> lines) {
		Set<String> rs = new HashSet<>();
		lines.forEach(l -> {
			String res = "";
			int i = 0;
			for (String t : l.split(separator)) {
				if (!ignoreIndexes.contains(i)) {
					res += t + separator;
				}
				i++;
			}
			rs.add(res);
		});

		return rs;
	}

	public CSVFileAssertion setSeparator(String separator) {
		this.separator = separator;
		return this;
	}

	public CSVFileAssertion setIgnoreIndexes(List<Integer> ignoreIndexes) {
		this.ignoreIndexes = ignoreIndexes;
		return this;

	}
	
	public String toString() {
		return this.getClass().getSimpleName() + "( path:\"" + (relPath.equalsIgnoreCase("") ? "\\" : relPath) + "\"" +
				(separator!=null?", separator:\""+separator+"\"":"" ) +
				(!ignoreIndexes.isEmpty()?", ignoreIndexes:\""+Joiner.on(",").join(ignoreIndexes)+"\"":"" ) +
				" )";
	}


	
}
