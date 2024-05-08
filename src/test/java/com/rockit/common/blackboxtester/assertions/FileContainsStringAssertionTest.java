package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class FileContainsStringAssertionTest {

	
	
	@Test
	public void testPositive() throws URISyntaxException {
		

		Path repSrc = Paths.get(ClassLoader.getSystemResource("assertions/FileContainsStringAssertion/replay").toURI());
		File rep = new File(repSrc.toString());
		assertTrue(rep.exists());
		
		FileContainsStringAssertion fcsa = new FileContainsStringAssertion("", "", "0.txt");
		fcsa.setReplayPath(repSrc.toString());
		fcsa.containsStrings(ImmutableList.of("ABRAKADABRA", "ACSINFO")).proceed();
		
	}

	
	@Test(expected = AssertionError.class)
	public void testNegative() throws URISyntaxException {
		

		Path repSrc = Paths.get(ClassLoader.getSystemResource("assertions/FileContainsStringAssertion/replay").toURI());
		File rep = new File(repSrc.toString());
		assertTrue(rep.exists());

		FileContainsStringAssertion fcsa = new FileContainsStringAssertion("", "", "0.txt");
		fcsa.setReplayPath(repSrc.toString());
		fcsa.containsStrings(ImmutableList.of("not included", "ignore")).proceed();
		
	}

}
