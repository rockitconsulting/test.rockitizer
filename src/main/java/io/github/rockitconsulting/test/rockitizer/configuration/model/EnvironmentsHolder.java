package io.github.rockitconsulting.test.rockitizer.configuration.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rockit.common.blackboxtester.suite.configuration.TestProtocol;

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

	public boolean isValid() {
		return getEnvs() != null && !getEnvs().isEmpty() && getFirstEnvironment() != null;
	}

	public Map<String, String> getProps(String pEnvName) {

		if (pEnvName == null) {
			TestProtocol.writeWarn("env.yaml exists, but environment param not provided. Replacing placeholders in resources.yaml with first environment: "
					+ getEnvs().get(0).getName() + System.lineSeparator());
			return getFirstEnvironment();
		} else if (checkExistsEnvByName(pEnvName)) {
			TestProtocol.write("env.yaml exists,  environment '" + pEnvName + "' found. Replacing placeholders in resources.yaml" + System.lineSeparator());
			return getPropsByEnvName(pEnvName);
		} else {
			TestProtocol.writeError("env.yaml exists, but environment '" + pEnvName + "' not found." + System.lineSeparator());
			return null;
		}
	}

	private Map<String, String> getFirstEnvironment() {
		return getEnvs().get(0).getProps();
	}

	/**
	 * First environment is always used as default. The other environments in
	 * list override the values of the first (default) environment if specified
	 * 
	 * @param pEnvName
	 *            target Environment
	 * @return
	 */
	private Map<String, String> getPropsByEnvName(String pEnvName) {
		Map<String, String> props = new HashMap<>();
		this.getEnvs().forEach(env -> {
			if (props.isEmpty() || env.getName().equalsIgnoreCase(pEnvName)) {
				props.putAll(env.getProps());
			}
		});
		return props;
	}

	private boolean checkExistsEnvByName(String pEnvName) {
		return (getEnvs().stream().filter(e -> e.getName().equalsIgnoreCase(pEnvName)).findFirst().orElse(null)) != null ? true : false;

	}

}
