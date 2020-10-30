package io.github.rockitconsulting.test.rockitizer.configuration.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EnvironmentsHolder {

	private List<Environment> envs = new ArrayList<>();

	public EnvironmentsHolder() {
	}

	public List<Environment> getEnvs() {
		return envs;
	}

	public void setEnvs(List<Environment> envs) {
		this.envs = envs;
	}
	
	
	/**
	 * First environment is always used as default. The other environments in list override the  values of the first (default) environment if specified
	 * 
	 * @param pEnvName target Environment 
	 * @return
	 */
	public Map<String,String> getEnvByName(String pEnvName) {
		Map<String,String> props = new HashMap<>();
		this.getEnvs().forEach( env -> {
			if(props.isEmpty() || env.getName().equalsIgnoreCase(pEnvName)) {
				props.putAll( env.getProps() );
			}			
		});
		return props;
	}
	
	
	public boolean checkExistsEnvByName(String pEnvName) {
		return   (getEnvs().stream().filter(e -> e.getName().equalsIgnoreCase(pEnvName)).findFirst().orElse(null)) !=null?true:false ;
		
	}

}
