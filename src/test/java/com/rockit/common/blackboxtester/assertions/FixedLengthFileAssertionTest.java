package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

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

public class FixedLengthFileAssertionTest {

	@Test
	public void testFixLengthAssertPositive() throws URISyntaxException, IOException {

		assertFixLength("assertions/FixLengthFileAssertion/positive/record/", "assertions/FixLengthFileAssertion/positive/replay/");

	}

	@Test(expected=AssertionError.class)
	public void testFixLengthAssertNegative() throws URISyntaxException, IOException {

		assertFixLength("assertions/FixLengthFileAssertion/negative/record/", "assertions/FixLengthFileAssertion/negative/replay/");

	}

	
	private void assertFixLength(String path1, String path2) throws URISyntaxException {
		Path recSrc = Paths.get(ClassLoader.getSystemResource(path1).toURI());
		File rec = new File(recSrc.toString());
		assertTrue(rec.exists());

		Path repSrc = Paths.get(ClassLoader.getSystemResource(path2).toURI());
		File rep = new File(repSrc.toString());
		assertTrue(rep.exists());
        FixedLengthFileAssertion fixedLengthFileAssertion = new FixedLengthFileAssertion("stepname", "test1#1;2;3;4;5;6");
        fixedLengthFileAssertion.setRecordPath(recSrc.toString());
        fixedLengthFileAssertion.setReplayPath(repSrc.toString());
        fixedLengthFileAssertion.proceed();
	}

}
