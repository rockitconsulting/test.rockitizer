package io.github.rockitconsulting.test.rockitizer.cli;



public class CLIObjectFactory {

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
