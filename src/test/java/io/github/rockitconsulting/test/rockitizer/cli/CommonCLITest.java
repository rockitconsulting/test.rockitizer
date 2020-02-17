package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class CommonCLITest {
	public static Logger log = Logger.getLogger(CommonCLITest.class.getName());

	final String rootPath  = ConfigUtils.getAbsoluteRootPath();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";
	String rootPathTestSrc = rootPath + relPath;

	CommonCLI commonCLI = new CommonCLI();
	
	@Before
	public void before() {
		commonCLI.setAbsolutePath(rootPath);
		commonCLI.setRelativePath(relPath);
	}


	@Test
	public void testPrintTestSuite() {
		commonCLI.printAllTests();
	}


}
