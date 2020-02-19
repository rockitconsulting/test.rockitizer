package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.connector.impl.FileGetConnector;

public class FileGetConnectorTest {

	private String tesfilePath;

	public static final Logger LOGGER = Logger.getLogger(FileGetConnectorTest.class.getName());

	private FileGetConnector fileGetConnector;

	@Before
	public void before() {
		TestObjectFactory.resetConfigurationToDefault();
		fileGetConnector = new FileGetConnector("FILEGET.FileGetConnectorTest");
		tesfilePath = fileGetConnector.getSrcPath();

	}
		
	

	@Test
	public void testConfig() {
		assertTrue(tesfilePath, !tesfilePath.isEmpty());
	}

	@Test
	public void testGetByName() throws URISyntaxException, IOException  {

		Path SRC = Paths.get(ClassLoader.getSystemResource(tesfilePath).toURI());
		File srcFile = new File(SRC.toString());
		assertTrue(srcFile.exists());
//		String remote = Files.toString(srcFile, Charset.defaultCharset());
		String remote = Files.asCharSource(srcFile, Charset.defaultCharset()).read();

		fileGetConnector.setSrcPath(SRC.toString());
		fileGetConnector.proceed();

		assertTrue(remote, remote.equals(fileGetConnector.getResponse()));

	}
	
	@Test
	public void testGetFirstByDir() throws URISyntaxException, IOException  {

		Path SRC = Paths.get(ClassLoader.getSystemResource("TestFileConnectors").toURI());
		File srcFile = new File(SRC.toString());
		assertTrue(srcFile.exists());
		
		if(srcFile.isDirectory()) {
			srcFile = srcFile.listFiles()[0];
		}

		
//		String remote = Files.toString(srcFile, Charset.defaultCharset());
		String remote = Files.asCharSource(srcFile, Charset.defaultCharset()).read();

		fileGetConnector.setSrcPath(SRC.toString());
		fileGetConnector.proceed();
		assertTrue(remote, remote.equals(fileGetConnector.getResponse()));
	}
	

	@Test
	public void testType() {
		assertTrue(fileGetConnector.getType(), fileGetConnector.getType().equals("FILEGET."));
	}

	@Test
	public void testName() {
		assertTrue(fileGetConnector.getId(), fileGetConnector.getId().equals("FILEGET.FileGetConnectorTest"));
	}

	@Test
	public void testId() {
		assertTrue(fileGetConnector.getId(), fileGetConnector.getId().equals("FILEGET.FileGetConnectorTest"));
	}

}
