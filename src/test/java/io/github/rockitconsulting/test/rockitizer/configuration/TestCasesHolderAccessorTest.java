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

public class TestCasesHolderAccessorTest {

	public static Logger log = Logger.getLogger(TestCasesHolderAccessorTest.class.getName());

	final String rootPath = ConfigUtils.getAbsoluteRootPath();
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
