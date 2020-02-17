package io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors;

import io.github.rockitconsulting.test.rockitizer.configuration.model.validation.ValidationUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

public class MQConnector extends BaseConnector  {

	public enum Types {
		MQGET, MQPUT
	}

	private String queue="@defaultQueue@";
	private String dsRefId="defaultMQ";
	private Types type;
	
	public MQConnector() {
		super();
	}
	
	public MQConnector(File location) {
		super(location);
	}
	
	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}


	public String getDsRefId() {
		return dsRefId;
	}

	public void setDsRefId(String dsRefId) {
		this.dsRefId = dsRefId;
	}
	
	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}
	

	@Override
	public String toString() {
		return type + ":{" +  (Strings.isNullOrEmpty(getId())?"": "id=" + getId() + ", ") +   
				(Strings.isNullOrEmpty(queue)?"": "queue=" + queue + ", ") +
				(Strings.isNullOrEmpty(dsRefId)?"":"dsRefId=" + dsRefId+", ") 
			+ "}";
	}


	@Override
	public Map<String, List<String>> validate() {
		return ValidationUtils.checkValid(getValidationContext(), (Map<String, String>) ImmutableMap.of(
				"queue", queue, 
				"dsRefId", dsRefId
				));
	}
	


}
