package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.impl.FileDelConnector;
import com.rockit.common.blackboxtester.util.FileUtils;

public class FileDelConnectorTest {

	public static final Logger LOGGER = Logger.getLogger(FileDelConnectorTest.class.getName());

	private FileDelConnector fileDelConnector;

	
	@Before
	public void before() {
		TestObjectFactory.resetConfigurationToDefault();
		fileDelConnector = new FileDelConnector("FILEDEL.FileDelConnectorTest");

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
	public void testId() {
		assertTrue(fileDelConnector.getId(), fileDelConnector.getId().equals("FILEDEL.FileDelConnectorTest"));
	}

}
