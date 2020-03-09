package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

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

public class CSVFileAssertionTest {
	public static Logger LOGGER = Logger.getLogger(CSVFileAssertionTest.class.getName());

	CSVFileAssertion csvFileAssertion;

	@Before
	public void setUp() {

	}

	@Test
	public void testRecRepFolders() throws URISyntaxException, IOException {

		Path recSrc = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/positive/record/").toURI());
		File rec = new File(recSrc.toString());
		assertTrue(rec.exists());

		Path repSrc = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/positive/replay/").toURI());
		File rep = new File(repSrc.toString());
		assertTrue(rep.exists());

		csvFileAssertion = new CSVFileAssertion("");
		csvFileAssertion.setReplayPath(repSrc.toString());
		csvFileAssertion.setRecordPath(recSrc.toString());
		csvFileAssertion.proceed();

	}

	@Test(expected = AssertionError.class)
	public void testRecRepFoldersNegative() throws URISyntaxException, IOException {

		Path recSrc = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/negative/record/").toURI());
		File rec = new File(recSrc.toString());
		assertTrue(rec.exists());

		Path repSrc = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/negative/replay/").toURI());
		File rep = new File(repSrc.toString());
		assertTrue(rep.exists());

		csvFileAssertion = new CSVFileAssertion("");
		csvFileAssertion.setReplayPath(repSrc.toString());
		csvFileAssertion.setRecordPath(recSrc.toString());
		csvFileAssertion.proceed();

	}

	@Test
	public void testPositive() throws URISyntaxException, IOException {
		CSVFileAssertion csvAssertion = new CSVFileAssertion(null, null);
		Path p1 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/positive/record/0.csv").toURI());
		Path p2 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/positive/replay/0.csv").toURI());
		csvAssertion.csvAssert(p1.toFile(), p2.toFile());

	}

	@Test(expected = AssertionError.class)
	public void testNegative() throws URISyntaxException, IOException {
		CSVFileAssertion csvAssertion = new CSVFileAssertion(null, null);

		Path p1 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/negative/record/0.csv").toURI());
		Path p2 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/negative/replay/0.csv").toURI());
		csvAssertion.csvAssert(p1.toFile(), p2.toFile());

	}

	@Test
	public void testTokensRemoveIgnored() throws URISyntaxException, IOException {
		CSVFileAssertion csvAssertion = new CSVFileAssertion(null, null).setSeparator(";").setIgnoreIndexes(ImmutableList.of(0, 1));
		Set<String> lines = new HashSet<>();
		lines.add("aaa;bbb;ccc;dddd");
		lines = csvAssertion.removeIgnored(lines);
		lines.forEach(l -> {
			System.out.println(l);
			Assert.assertEquals("ccc;dddd;",l);

		});

	}

	
	
	@Test(expected = AssertionError.class)
	public void testCsvAssertWithIgnoreTokensRemoveIgnoredNOK() throws URISyntaxException, IOException {
		CSVFileAssertion csvAssertion = new CSVFileAssertion(null, null).setSeparator(";").setIgnoreIndexes(ImmutableList.of(0, 1));

		Path p1 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/ignoreTokens/0.csv").toURI());
		Path p2 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/ignoreTokens/1.csv").toURI());

		csvAssertion.csvAssert(p1.toFile(), p2.toFile());


	}
	
	@Test
	public void testCsvAssertWithIgnoreTokensRemoveIgnoredOK() throws URISyntaxException, IOException {
		CSVFileAssertion csvAssertion = new CSVFileAssertion(null, null).setSeparator(";").setIgnoreIndexes(ImmutableList.of(0, 3));

		Path p1 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/ignoreTokens/0.csv").toURI());
		Path p2 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/ignoreTokens/1.csv").toURI());

		csvAssertion.csvAssert(p1.toFile(), p2.toFile());


	}

	
}
