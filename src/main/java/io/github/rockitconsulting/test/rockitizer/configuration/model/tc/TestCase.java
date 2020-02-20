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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((testCaseName == null) ? 0 : testCaseName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCase other = (TestCase) obj;
		if (testCaseName == null) {
			if (other.testCaseName != null)
				return false;
		} else if (!testCaseName.equals(other.testCaseName))
			return false;
		return true;
	}


}
