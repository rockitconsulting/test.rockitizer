package io.github.rockitconsulting.test.rockitizer.configuration.model.tc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class TestStep  {
	
	private String testStepName;
	
	List<ConnectorRef> connectorRefs = new ArrayList<>();


	public TestStep() {
	}
	
	public TestStep (File location) {
		this.testStepName = location.getName();
	}
	
	public String getTestStepName() {
		return testStepName;
	}
	
	public void setTestStepName(String testStepName) {
		this.testStepName = testStepName;
	}

	public List<ConnectorRef> getConnectorRefs() {
		return connectorRefs;
	}

	public void setConnectorRefs(List<ConnectorRef> connectorRefs) {
		this.connectorRefs = connectorRefs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((testStepName == null) ? 0 : testStepName.hashCode());
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
		TestStep other = (TestStep) obj;
		if (testStepName == null) {
			if (other.testStepName != null)
				return false;
		} else if (!testStepName.equals(other.testStepName))
			return false;
		return true;
	}
	

	/*

	@Override
	public String toString() {
		return System.lineSeparator() + "			TestStep [testStepName=" + testStepName + 
				( dbConnectors.isEmpty()?"":System.lineSeparator() + "				, dbConnectors="+ dbConnectors ) + 
				( httpConnectors.isEmpty()?"":System.lineSeparator() + "			, httpConnectors="+ httpConnectors ) +
				( fileConnectors.isEmpty()?"":System.lineSeparator() + "			, fileConnectors="+ fileConnectors ) +
				( mqConnectors.isEmpty()?"":System.lineSeparator() + "				, mqConnectors="+ mqConnectors ) +
				( scpConnectors.isEmpty()?"":System.lineSeparator() + "				, scpConnectors="+ scpConnectors ) +
				 "]";
	}
	 */ 	

}
