package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.Configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.ResourcesHolderAccessor;
import io.github.rockitconsulting.test.rockitizer.configuration.TestCasesHolderAccessor;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

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

public class TestObjectFactory {
	
	
	final static String rootPath = ConfigUtils.getAbsolutePathToResources();
	final static String relPath = "full.demo.project.cli.tests/src/test/resources/";


	public static void resetConfigurationToDefault() {
		ResourcesHolderAccessor rhCLI = new ResourcesHolderAccessor();
		TestCasesHolderAccessor tcCLI = new TestCasesHolderAccessor();
		Configuration.reset(rhCLI, tcCLI);
	}

	public static void resetEmptyConfigurationToContextDemoPrj() {
		ResourcesHolderAccessor rhCLI = TestObjectFactory.createResourcesHolderCLI(rootPath, relPath, null);
		TestCasesHolderAccessor tcCLI = TestObjectFactory.createTestCasesHolderCLI(rootPath, relPath, null);
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

	

	private static ResourcesHolderAccessor createResourcesHolderCLI(String rootPath, String relPath, String filename) {
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

	private static TestCasesHolderAccessor createTestCasesHolderCLI(String rootPath, String relPath, String filename) {
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
