package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.Configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.ResourcesHolderAccessor;
import io.github.rockitconsulting.test.rockitizer.configuration.TestCasesHolderAccessor;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;



public class TestObjectFactory {
	
	
	final static String rootPath = ConfigUtils.getAbsoluteRootPath();
	final static String relPath = "full.demo.project.cli.tests/src/test/resources/";


	public static void resetConfigurationToDefault() {
		ResourcesHolderAccessor rhCLI = new ResourcesHolderAccessor();
		TestCasesHolderAccessor tcCLI = new TestCasesHolderAccessor();
		Configuration.reset(rhCLI, tcCLI);
	}


	public static void resetConfigurationToContextDemoPrj() {
		ResourcesHolderAccessor rhCLI = TestObjectFactory.createResourcesHolderCLI(rootPath, relPath, "resources.yaml");
		TestCasesHolderAccessor tcCLI = TestObjectFactory.createTestCasesHolderCLI(rootPath, relPath, "testcases.yaml");
		Configuration.reset(rhCLI, tcCLI);
	}

	
	public static void resetConfigurationToContextDemoPrj(String filenamePrefix) {
		ResourcesHolderAccessor rhCLI = TestObjectFactory.createResourcesHolderCLI(rootPath, relPath, filenamePrefix + "-resources.yaml");
		TestCasesHolderAccessor tcCLI = TestObjectFactory.createTestCasesHolderCLI(rootPath, relPath, filenamePrefix + "-testcases.yaml");
		Configuration.reset(rhCLI, tcCLI);
	}


	public static void resetConfigurationToContext(String pRootPath, String pRelPath, String filenamePrefix) {
		ResourcesHolderAccessor rhCLI = TestObjectFactory.createResourcesHolderCLI(pRootPath, pRelPath, filenamePrefix + "-resources.yaml");
		TestCasesHolderAccessor tcCLI = TestObjectFactory.createTestCasesHolderCLI(pRootPath, pRelPath, filenamePrefix + "-testcases.yaml");
		Configuration.reset(rhCLI, tcCLI);

	}

	

	public static ResourcesHolderAccessor createResourcesHolderCLI(String rootPath, String relPath, String filename) {
		ResourcesHolderAccessor rhCLI = new ResourcesHolderAccessor();
		if(null!=rootPath) {	
			rhCLI.setAbsolutePath(rootPath);
		}
		
		if(null!=relPath) {	
			rhCLI.setRelativePath(relPath);
		}

		if(null!=filename) {	
			rhCLI.setResourcesFileName(filename);
		}
		return rhCLI;
	}

	public static TestCasesHolderAccessor createTestCasesHolderCLI(String rootPath, String relPath, String filename) {
		TestCasesHolderAccessor tchCLI = new TestCasesHolderAccessor();
		if(null!=rootPath) {	
			tchCLI.setAbsolutePath(rootPath);
		}
		
		if(null!=relPath) {	
			tchCLI.setRelativePath(relPath);
		}

		if(null!=filename) {	
			tchCLI.setTestcasesFileName(filename);
		}
		return tchCLI;
	}

	
	
	
}
