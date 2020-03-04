package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.TestCasesHolderAccessor;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

public class TestCasesHolderAccessorTest {

	public static Logger log = Logger.getLogger(TestCasesHolderAccessorTest.class.getName());

	final String rootPath = ConfigUtils.getAbsolutePathToResources();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";
	String rootPathTestSrc = rootPath + relPath;

	TestCasesHolderAccessor tcCLI = new TestCasesHolderAccessor();
	
	@Before
	public void before() {
		tcCLI.setAbsolutePath(rootPath);
		tcCLI.setRelativePath(relPath);
		tcCLI.setTestcasesFileName("testcases-generated.yaml");
		
	}

	
	
	@Test
	public void readResources() throws IOException {
		TestCasesHolder tch1 = tcCLI.testCasesHolderFromFileSystemToYaml();
		TestCasesHolder tch2 = ConfigUtils.testCasesHolderFromYaml(rootPathTestSrc + "testcases-generated.yaml");
		Assert.assertTrue(tch1.getTestCases().size() == tch2.getTestCases().size());
	}

	@Test
	public void generateResources() throws IOException {
		tcCLI.setTestcasesFileName("testcases-generated-01.yaml");
		TestCasesHolder tch1 = tcCLI.testCasesHolderFromFileSystemToYaml();
		Assert.assertNotNull(tch1);
		Assert.assertTrue( new File(tcCLI.getFullPath()+ tcCLI.getTestcasesFileName()).exists());
		
	}


}
