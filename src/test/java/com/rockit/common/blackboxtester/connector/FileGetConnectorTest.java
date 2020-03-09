package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

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
import com.rockit.common.blackboxtester.suite.configuration.Constants;

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

		String path = ConfigUtils.getAbsolutePathToRoot() + Constants.RECORD_PATH + "connectors/file/FileGetConnector/checkGetLast";
		fileGetConnector.setSrcPath(path);
		fileGetConnector.proceed();
		assertEquals("my last modified file content", fileGetConnector.getResponse());
	}
	
	@Test
	public void testGetFirstByDirNullpointerfixOnNotExistingId() throws URISyntaxException, IOException  {

		fileGetConnector = new FileGetConnector("FILEGET.NOT.EXISITNG.PATH");
		tesfilePath = fileGetConnector.getSrcPath();

		String path = ConfigUtils.getAbsolutePathToRoot() + Constants.RECORD_PATH + "connectors/file/FileGetConnector/checkGetLast";
		fileGetConnector.setSrcPath(path);
		fileGetConnector.proceed();
		assertEquals("my last modified file content", fileGetConnector.getResponse());
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
