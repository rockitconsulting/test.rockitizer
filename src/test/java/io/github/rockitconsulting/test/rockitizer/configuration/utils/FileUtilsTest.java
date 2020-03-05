package io.github.rockitconsulting.test.rockitizer.configuration.utils;

import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
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

public class FileUtilsTest {
	public static final Logger LOGGER = Logger.getLogger(FileUtilsTest.class.getName());
	
	final List<String> connectorFolders = ImmutableList.of("a001TestMQ", "a002ElasticGet");
	final List<String> connectorFiles = ImmutableList.of("CustomerBatch.xml", "CustomerBatch2.xml");
	

	@Test
	public void testListFolders(){
		String path = ConfigUtils.getAbsolutePathToRoot() + Constants.RECORD_PATH + "/FileUtilsTest";
		Iterable<File> listFolders = FileUtils.listFolders(new File(path));
		
		for (File file : listFolders) {
			assertTrue(file.getName(), connectorFolders.contains(file.getName()));
		}
		LOGGER.info("FileUtilsTest.testListFolders- OK");	
	}
	
	@Test
	public void testListFiles(){
		String path = ConfigUtils.getAbsolutePathToRoot() + Constants.RECORD_PATH + "/FileUtilsTest/a001TestMQ/MQ@TEST.TRIGGER.@ENV@";
		Iterable<File> listFiles = FileUtils.listFiles(new File(path));
		
		for (File file : listFiles) {
			assertTrue(file.getName(), connectorFiles.contains(file.getName()));
		}
		LOGGER.info("FileUtilsTest.testListFiles- OK");
	}
}
