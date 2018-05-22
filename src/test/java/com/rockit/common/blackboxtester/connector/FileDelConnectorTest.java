package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.impl.FileDelConnector;
import com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.util.FileUtils;

public class FileDelConnectorTest {

	public static final Logger LOGGER = Logger.getLogger(FileDelConnectorTest.class.getName());

	private FileDelConnector fileDelConnector;

	@Before
	public void setUp() {
		fileDelConnector = new FileDelConnector("FILEDEL.FileDelConnectorTest");

	}

	
	@Test
	public void testConfig() {
		assertTrue(ConfigurationHolder.configuration().getString(fileDelConnector.getName() + Constants.FILE_PATH_KEY),
				!ConfigurationHolder.configuration().getString(fileDelConnector.getName() + Constants.FILE_PATH_KEY)
						.isEmpty());
	}	
	
		
	@Test
	public void testDel() throws URISyntaxException, IOException  {

		Path SRC = Paths.get(ClassLoader.getSystemResource("TestFileConnectors").toURI());
		File from = new File(SRC.toString());
		assertTrue(from.exists());
		
		String copyFolder=System.getProperty("java.io.tmpdir")+File.separator;
		File to = new File(copyFolder);
		assertTrue(to.exists());
		FileUtils.copy(from, to);
		
		File delete =new File(System.getProperty("java.io.tmpdir")+File.separator+"TestFileConnectors");
		
		fileDelConnector.setSrcPath(delete.getAbsolutePath());
		fileDelConnector.proceed();
		assertFalse(delete.exists());

		

	}

	@Test
	public void testType() {
		assertTrue(fileDelConnector.getType(), fileDelConnector.getType().equals("FILEDEL."));
	}

	@Test
	public void testName() {
		assertTrue(fileDelConnector.getName(), fileDelConnector.getName().equals("FILEDEL.FileDelConnectorTest"));
	}

	@Test
	public void testId() {
		assertTrue(fileDelConnector.getId(), fileDelConnector.getId().equals("FILEDEL.FileDelConnectorTest"));
	}

}
