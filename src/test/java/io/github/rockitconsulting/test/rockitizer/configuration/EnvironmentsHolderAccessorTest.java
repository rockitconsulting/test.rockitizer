package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.model.Environment;
import io.github.rockitconsulting.test.rockitizer.configuration.model.EnvironmentsHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class EnvironmentsHolderAccessorTest {

	public static Logger log = Logger.getLogger(EnvironmentsHolderAccessorTest.class.getName());

	final String rootPath = ConfigUtils.getAbsolutePathToResources();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";
	String rootPathTestSrc = rootPath + relPath;

	EnvironmentsHolderAccessor ehCLI = new EnvironmentsHolderAccessor();

	@Before
	public void before() {
		ehCLI.setAbsolutePath(rootPath);
		ehCLI.setRelativePath(relPath);
		ehCLI.setEnvsFileName("envs-generated.yaml");
		log.info(ehCLI.contextAsString());

	}

	@Test
	public void generateResources() throws IOException {
		EnvironmentsHolder eh = new EnvironmentsHolder();

		Environment env1 = new Environment();
		env1.setName("myenv1");
		env1.setProps(ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3"));
		eh.getEnvs().add(env1);
		Environment env2 = new Environment();
		env2.setName("myenv2");
		env2.setProps(ImmutableMap.of("k1", "v1", "k2", "v2", "k3", "v3"));
		eh.getEnvs().add(env2);
		Environment env3 = new Environment();
		env3.setName("myenv3");
		env3.setProps(ImmutableMap.of("k3", "zzzzzzz3"));
		eh.getEnvs().add(env3);

		ehCLI.setEnvsFileName("envs-generated.yaml");
		ehCLI.envsHolderToYaml(eh);

		Assert.assertNotNull(ehCLI);
		Assert.assertTrue(new File(ehCLI.getFullPath() + ehCLI.getEnvsFileName()).exists());

	}

	@Test
	public void readResources() throws IOException {

		this.generateResources();
		EnvironmentsHolder envsHolderFromYaml = ehCLI.envsHolderFromYaml();
		Assert.assertTrue(envsHolderFromYaml.getEnvs().size() == 3);

	}

	@Test
	public void noEnvExists() throws IOException {
		ehCLI.setEnvsFileName("notexists.yaml");
		EnvironmentsHolder envsHolderFromYaml = ehCLI.envsHolderFromYaml();
		Assert.assertTrue(envsHolderFromYaml == null);

	}

	@Test
	public void getConfigByEnvWithInheritanceFromDefault() throws IOException {
		this.generateResources();
		EnvironmentsHolder envsHolderFromYaml = ehCLI.envsHolderFromYaml();
		Assert.assertTrue(envsHolderFromYaml.getEnvs().size() == 3);
		Assert.assertTrue(envsHolderFromYaml.getEnvs().get(0).getName().equalsIgnoreCase("myenv1"));

		Map<String, String> props = envsHolderFromYaml.getProps("myEnv3");

		Assert.assertTrue(props.size() == 3);
		Assert.assertTrue(props.containsKey("k1"));
		Assert.assertTrue(props.containsKey("k2"));
		Assert.assertTrue(props.containsKey("k3"));
		Assert.assertTrue(props.get("k1").equalsIgnoreCase("v1"));
		Assert.assertTrue(props.get("k2").equalsIgnoreCase("v2"));
		Assert.assertTrue(props.get("k3").equalsIgnoreCase("zzzzzzz3"));

	}

	@Test
	public void checkEnvByNameExists() throws IOException {
		this.generateResources();
		EnvironmentsHolder envsHolderFromYaml = ehCLI.envsHolderFromYaml();
		Assert.assertTrue(envsHolderFromYaml.getEnvs().get(0).getName().equalsIgnoreCase("myenv1"));
	}

	@Test
	public void getConfigByEnvEqNull() throws IOException {
		ehCLI.setAbsolutePath(rootPath);
		ehCLI.setRelativePath(relPath);
		ehCLI.setEnvsFileName("envs-generated.yaml");

		EnvironmentsHolder envsHolderFromYaml = ehCLI.envsHolderFromYaml();
		Map<String, String> props = envsHolderFromYaml.getProps(null);

		Assert.assertTrue(envsHolderFromYaml.getEnvs().size() == 3);
		Assert.assertTrue(props.size() == 3);
		Assert.assertTrue(props.containsKey("k1"));
		Assert.assertTrue(props.containsKey("k2"));
		Assert.assertTrue(props.containsKey("k3"));
		Assert.assertTrue(props.get("k1").equalsIgnoreCase("v1"));
		Assert.assertTrue(props.get("k2").equalsIgnoreCase("v2"));
		Assert.assertTrue(props.get("k3").equalsIgnoreCase("v3"));
	}

	@Test
	public void getConfigByNotExistingEnv() throws IOException {
		ehCLI.setAbsolutePath(rootPath);
		ehCLI.setRelativePath(relPath);
		ehCLI.setEnvsFileName("envs-generated.yaml");

		EnvironmentsHolder envsHolderFromYaml = ehCLI.envsHolderFromYaml();
		Map<String, String> props = envsHolderFromYaml.getProps("notExistingEnv");

		Assert.assertTrue(envsHolderFromYaml.getEnvs().size() == 3);
		Assert.assertTrue(props.size() == 3);
		Assert.assertTrue(props.containsKey("k1"));
		Assert.assertTrue(props.containsKey("k2"));
		Assert.assertTrue(props.containsKey("k3"));
		Assert.assertTrue(props.get("k1").equalsIgnoreCase("v1"));
		Assert.assertTrue(props.get("k2").equalsIgnoreCase("v2"));
		Assert.assertTrue(props.get("k3").equalsIgnoreCase("v3"));
	}

}
