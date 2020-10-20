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

public class ConnectorRef {

	
	/* 
	 *     - conRefId: FILEPUT.IN.FILE2FILE
     * 		 payloads:
     * 		 - fileName: testinput.xml
	 */
	
	
	/**
	 * Connector Folder  
	 */
	private String conRefId;
	
	private String conRefDescription;
	/**
	 * Files in connector folder 
	 */
	private List<Payload> payloads = new ArrayList<>();

	public ConnectorRef(String id) {
		this.conRefId = id;
	}
	
	public ConnectorRef() {}
	
	public ConnectorRef (File location) {
		this.conRefId = location.getName();
		this.conRefDescription = FileUtils.readFile( new File(location.getAbsolutePath()+"/"+ Constants.DESCRIPTION_TXT) );

	}
	
	
	public String getConRefId() {
		return conRefId;
	}

	public void setConRefId(String conRefId) {
		this.conRefId = conRefId;
	}

	public List<Payload> getPayloads() {
		return payloads;
	}

	public void setPayloads(List<Payload> payloads) {
		this.payloads = payloads;
	}

	public String getConRefDescription() {
		return conRefDescription;
	}

	public void setConRefDescription(String conRefDescription) {
		this.conRefDescription = conRefDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conRefDescription == null) ? 0 : conRefDescription.hashCode());
		result = prime * result + ((conRefId == null) ? 0 : conRefId.hashCode());
		result = prime * result + ((payloads == null) ? 0 : payloads.hashCode());
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
		ConnectorRef other = (ConnectorRef) obj;
		if (conRefDescription == null) {
			if (other.conRefDescription != null)
				return false;
		} else if (!conRefDescription.equals(other.conRefDescription))
			return false;
		if (conRefId == null) {
			if (other.conRefId != null)
				return false;
		} else if (!conRefId.equals(other.conRefId))
			return false;
		if (payloads == null) {
			if (other.payloads != null)
				return false;
		} else if (!payloads.equals(other.payloads))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConnectorRef [conRefId=" + conRefId + ", conRefDescription=" + conRefDescription + ", payloads=" + payloads + "]";
	}


	
}
