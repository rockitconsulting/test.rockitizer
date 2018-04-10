package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.connector.impl.FilePutConnector;
import com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class FilePutConnectorTest {

	public static final Logger LOGGER = Logger.getLogger(FilePutConnectorTest.class.getName());

	private FilePutConnector filePutConnector;

	@Before
	public void setUp() {
		filePutConnector = new FilePutConnector("FILEPUT@FilePutConnectorTest");

	}

	
	@Test
	public void testConfig() {
		assertTrue(ConfigurationHolder.configuration().getString(filePutConnector.getName() + Constants.FILE_PATH_KEY),
				!ConfigurationHolder.configuration().getString(filePutConnector.getName() + Constants.FILE_PATH_KEY)
						.isEmpty());
	}	
	
	@Test
	public void testPut() throws URISyntaxException, IOException  {

		Path SRC = Paths.get(ClassLoader.getSystemResource("TestFileConnectors/test1.xml").toURI());
		File srcFile = new File(SRC.toString());
		assertTrue(srcFile.exists());
		filePutConnector.setRequest(srcFile);
		
		String destPath=System.getProperty("java.io.tmpdir")+File.separator+
				ConfigurationHolder.configuration().getString(filePutConnector.getName() + Constants.FILE_PATH_KEY); 
		filePutConnector.setDestPath(destPath);
		filePutConnector.proceed();
		File resFile = new File(destPath);
		assertTrue(resFile.exists());
		assertTrue(Files.equal(srcFile, resFile));
		

	}

	@Test
	public void testType() {
		assertTrue(filePutConnector.getType(), filePutConnector.getType().equals("FILEPUT@"));
	}

	@Test
	public void testName() {
		assertTrue(filePutConnector.getName(), filePutConnector.getName().equals("FILEPUT@FilePutConnectorTest"));
	}

	@Test
	public void testId() {
		assertTrue(filePutConnector.getId(), filePutConnector.getId().equals("FILEPUT@FilePutConnectorTest"));
	}

}
