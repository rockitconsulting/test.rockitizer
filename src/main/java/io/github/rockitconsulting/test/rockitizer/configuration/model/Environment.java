package io.github.rockitconsulting.test.rockitizer.configuration.model;

import java.util.HashMap;
import java.util.Map;

public class Environment {
	
	private String  name;
	private Map<String, String> props = new HashMap<>();

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getProps() {
		return props;
	}
	public void setProps(Map<String, String> props) {
		this.props = props;
	}
	
	
}
