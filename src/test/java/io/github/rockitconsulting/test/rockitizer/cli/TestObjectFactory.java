package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.Configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;



public class TestObjectFactory {
	
	
	final static String rootPath = ConfigUtils.getAbsoluteRootPath();
	final static String relPath = "full.demo.project.cli.tests/src/test/resources/";


	public static void resetConfigurationToDefault() {
		ResourcesHolderCLI rhCLI = new ResourcesHolderCLI();
		TestCasesHolderCLI tcCLI = new TestCasesHolderCLI();
		Configuration.reset(rhCLI, tcCLI);
	}

	
	public static void resetConfigurationToContextDemoPrj(String filenamePrefix) {
		ResourcesHolderCLI rhCLI = TestObjectFactory.createResourcesHolderCLI(rootPath, relPath, filenamePrefix + "-resources.yaml");
		TestCasesHolderCLI tcCLI = TestObjectFactory.createTestCasesHolderCLI(rootPath, relPath, filenamePrefix + "-testcases.yaml");
		Configuration.reset(rhCLI, tcCLI);
	}


	public static void resetConfigurationToContext(String pRootPath, String pRelPath, String filenamePrefix) {
		ResourcesHolderCLI rhCLI = TestObjectFactory.createResourcesHolderCLI(pRootPath, pRelPath, filenamePrefix + "-resources.yaml");
		TestCasesHolderCLI tcCLI = TestObjectFactory.createTestCasesHolderCLI(pRootPath, pRelPath, filenamePrefix + "-testcases.yaml");
		Configuration.reset(rhCLI, tcCLI);

	}

	

	public static ResourcesHolderCLI createResourcesHolderCLI(String rootPath, String relPath, String filename) {
		ResourcesHolderCLI rhCLI = new ResourcesHolderCLI();
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

	public static TestCasesHolderCLI createTestCasesHolderCLI(String rootPath, String relPath, String filename) {
		TestCasesHolderCLI tchCLI = new TestCasesHolderCLI();
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
