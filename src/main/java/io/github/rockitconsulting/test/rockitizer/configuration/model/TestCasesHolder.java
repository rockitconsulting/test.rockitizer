package io.github.rockitconsulting.test.rockitizer.configuration.model;

import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
* 
*	testCases:
*	- testCaseName: FILEinFILEOutTest
*	  testSteps:
*	  - testStepName: 0BEFORE
*	    connectors:
*	    - conRefId: FILEDEL.IN.FILE2FILE
*	    - conRefId: FILEDEL.OUT.FILE2FILE
*	  - testStepName: a001FILEPutMessage
*	    connectors:
*	    - conRefId: FILEPUT.IN.FILE2FILE
*	      payloads:
*	      - fileName: testinput.xml
*	  - testStepName: a002FILEGetMessage
*	    connectors:
*	    - conRefId: FILEGET.OUT.FILE2FILE
*
**/

public class TestCasesHolder  {
	
	
	List<TestCase> testCases =  new ArrayList<>();

	public List<TestCase> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	
	
	
	
}
