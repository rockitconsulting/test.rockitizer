package io.github.rockitconsulting.test.rockitizer.configuration.model.tc;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

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

public class TestStep  {
	
	private String testStepName;
	
	List<ConnectorRef> connectorRefs = new ArrayList<>();

	private String testStepDescription;

	public TestStep() {
	}
	
	public TestStep (File location) {
		this.testStepName = location.getName();
		this.testStepDescription = FileUtils.readFile( new File(location.getAbsolutePath()+"/"+ Constants.DESCRIPTION_TXT) );

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

	public String getTestStepDescription() {
		return testStepDescription;
	}

	public void setTestStepDescription(String testStepDescription) {
		this.testStepDescription = testStepDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connectorRefs == null) ? 0 : connectorRefs.hashCode());
		result = prime * result + ((testStepDescription == null) ? 0 : testStepDescription.hashCode());
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
		if (connectorRefs == null) {
			if (other.connectorRefs != null)
				return false;
		} else if (!connectorRefs.equals(other.connectorRefs))
			return false;
		if (testStepDescription == null) {
			if (other.testStepDescription != null)
				return false;
		} else if (!testStepDescription.equals(other.testStepDescription))
			return false;
		if (testStepName == null) {
			if (other.testStepName != null)
				return false;
		} else if (!testStepName.equals(other.testStepName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestStep [testStepName=" + testStepName + ", connectorRefs=" + connectorRefs + ", testStepDescription=" + testStepDescription + "]";
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
