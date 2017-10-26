package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

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
		
		
		csvFileAssertion = new CSVFileAssertion(recSrc.toString(), repSrc.toString() );
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
		
		
		csvFileAssertion = new CSVFileAssertion(recSrc.toString(), repSrc.toString() );
		csvFileAssertion.proceed();
		


	}

	
	
	@Test
	public void testPositive() throws URISyntaxException, IOException {

		Path p1 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/positive/record/0.csv").toURI());
		Path p2 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/positive/replay/0.csv").toURI());
		csvAssert(p1.toFile(),p2.toFile());
		


	}


	@Test(expected = AssertionError.class)
	public void testNegative() throws URISyntaxException, IOException {
		
		Path p1 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/negative/record/0.csv").toURI());
		Path p2 = Paths.get(ClassLoader.getSystemResource("assertions/CSVFileAssertion/negative/replay/0.csv").toURI());
		csvAssert(p1.toFile(),p2.toFile());

	}

	private void csvAssert(File f1, File f2) throws IOException  {
		
		assertTrue(f1.exists());
		assertTrue(f2.exists());

		Set<String> f1Lines = new HashSet<String>(FileUtils.readLines(f1));
		Set<String> ff1Lines = new HashSet<String>(f1Lines);
		Set<String> f2Lines = new HashSet<String>(FileUtils.readLines(f2));
		Set<String> ff2Lines = new HashSet<String>(f2Lines);
		f1Lines.removeAll(ff2Lines);
		f2Lines.removeAll(ff1Lines);
		assertTrue(f2Lines.isEmpty() && f1Lines.isEmpty());
	}
	
	

}
