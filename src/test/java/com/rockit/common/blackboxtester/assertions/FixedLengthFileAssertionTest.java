package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

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

		FixedLengthFileAssertion fixLengthFileAssertion = new FixedLengthFileAssertion(recSrc.toString(), repSrc.toString(), "stepname", "test1#1;2;3;4;5;6");
		fixLengthFileAssertion.proceed();
	}

}
