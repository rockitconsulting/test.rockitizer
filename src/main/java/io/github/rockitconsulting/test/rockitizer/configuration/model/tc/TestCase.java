package io.github.rockitconsulting.test.rockitizer.configuration.model.tc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestCase  {
	
	private String testCaseName;
	
	List<TestStep> testSteps = new ArrayList<>();
	
	public TestCase() {}
	
	
	public TestCase (File location) {
		this.testCaseName = location.getName();
	}
	
	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}


	public List<TestStep> getTestSteps() {
		return testSteps;
	}

	public void setTestSteps(List<TestStep> testSteps) {
		this.testSteps = testSteps;
	}



	@Override
	public String toString() {
		return System.lineSeparator() + " 	TestCase [testCaseName=" + testCaseName + ", " + System.lineSeparator() + " 		testSteps="
				+ testSteps + "]";
	}


}
