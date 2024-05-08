package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.impl.FileDelConnector;


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
