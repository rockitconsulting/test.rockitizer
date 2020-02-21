package io.github.rockitconsulting.test.rockitizer.configuration.model.tc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation of connector 
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conRefId == null) ? 0 : conRefId.hashCode());
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
		if (conRefId == null) {
			if (other.conRefId != null)
				return false;
		} else if (!conRefId.equals(other.conRefId))
			return false;
		return true;
	}
	
	

	
}