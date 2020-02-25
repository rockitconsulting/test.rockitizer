package com.rockit.common.blackboxtester.util;

import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

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
	}
	
	@Test
	public void testListFiles(){
		String path = ConfigUtils.getAbsolutePathToRoot() + Constants.RECORD_PATH + "/FileUtilsTest/a001TestMQ/MQ@TEST.TRIGGER.@ENV@";
		Iterable<File> listFiles = FileUtils.listFiles(new File(path));
		
		for (File file : listFiles) {
			assertTrue(file.getName(), connectorFiles.contains(file.getName()));
		}
	}
}
