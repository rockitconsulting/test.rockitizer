package com.rockit.common.blackboxtester.connector;

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

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.connector.impl.FilePutConnector;
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
