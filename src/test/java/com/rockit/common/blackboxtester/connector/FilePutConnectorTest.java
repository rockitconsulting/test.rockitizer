package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.cli.TestObjectFactory;

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
import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class FilePutConnectorTest {

	public static final Logger LOGGER = Logger.getLogger(FilePutConnectorTest.class.getName());
	private FilePutConnector filePutConnector;

	@Before
	public void before() {
		TestObjectFactory.resetConfigurationToDefault();
		filePutConnector = new FilePutConnector("FILEPUT.FilePutConnectorTest");

	}

	@Test
	public void testPut() throws URISyntaxException, IOException {

		Path SRC = Paths.get(ClassLoader.getSystemResource("TestFileConnectors/test1.xml").toURI());
		File srcFile = new File(SRC.toString());
		assertTrue(srcFile.exists());
		filePutConnector.setRequest(srcFile);

		String destPath = System.getProperty("java.io.tmpdir") + File.separator + filePutConnector.getDestPath();
		filePutConnector.setDestPath(destPath);
		filePutConnector.proceed();
		File resFile = new File(destPath);
		assertTrue(resFile.exists());
		assertTrue(Files.equal(srcFile, resFile));

	}

	@Test
	public void testType() {
		assertTrue(filePutConnector.getType(), filePutConnector.getType().equals(Constants.Connectors.FILEPUT.toString()));
	}

	@Test
	public void testName() {
		assertTrue(filePutConnector.getId(), filePutConnector.getId().equals("FILEPUT.FilePutConnectorTest"));
	}

	@Test
	public void testId() {
		assertTrue(filePutConnector.getId(), filePutConnector.getId().equals("FILEPUT.FilePutConnectorTest"));
	}
}
