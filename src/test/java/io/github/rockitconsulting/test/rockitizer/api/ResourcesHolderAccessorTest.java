package io.github.rockitconsulting.test.rockitizer.api;

import io.github.rockitconsulting.test.rockitizer.api.ResourcesHolderAccessor;
import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResourcesHolderAccessorTest {

	public static Logger log = Logger.getLogger(ResourcesHolderAccessorTest.class.getName());

	final String rootPath = ConfigUtils.getAbsoluteRootPath();
	final String relPath = "full.demo.project.cli.tests/src/test/resources/";
	String rootPathTestSrc = rootPath + relPath;

	ResourcesHolderAccessor rhCLI = new ResourcesHolderAccessor();

	@Before
	public void before() {
		rhCLI.setAbsolutePath(rootPath);
		rhCLI.setRelativePath(relPath);
		rhCLI.setResourcesFileName("resources-generated.yaml");
		log.info(rhCLI.contextAsString());

	}

	@Test
	public void readResources() throws IOException {
		ResourcesHolder rh1 = rhCLI.generateResources();
		
		ResourcesHolder rh2 = ConfigUtils.resourcesHolderFromYaml(relPath + "resources-generated.yaml");

		Assert.assertTrue("db connectors sizes:  " + rh1.getDbConnectors().size() + "/" + rh2.getDbConnectors().size(), rh1.getDbConnectors().size() == rh2.getDbConnectors()
				.size());
		Assert.assertTrue("http connectors sizes: " + rh1.getHttpConnectors().size() + "/" + rh2.getHttpConnectors().size(), rh1.getHttpConnectors().size() == rh2
				.getHttpConnectors().size());
		Assert.assertTrue("mq connectors sizes: " + rh1.getMqConnectors().size() + "/" + rh2.getMqConnectors().size(), rh1.getMqConnectors().size() == rh2.getMqConnectors()
				.size());
	}

	@Test
	public void generateResources() throws IOException {
		rhCLI.setResourcesFileName("resources-generated-01.yaml");
		ResourcesHolder rh1 = rhCLI.generateResources();
		Assert.assertNotNull(rh1);
		Assert.assertTrue(new File(rhCLI.getFullPath() + rhCLI.getResourcesFileName()).exists());

	}

	@Test
	public void generateResourcesWithPlaceholders() throws IOException {

		Map<String, String> replacements = new HashMap<>();
		replacements.put("KEY1", "VALUE1");
		replacements.put("KEY2", "VALUE2");

		rhCLI.setResourcesFileName("resources-generated-with-payload-replacements-01.yaml");
		ResourcesHolder rh1 = rhCLI.generateResources(replacements);

		Assert.assertNotNull(rh1);
		Assert.assertTrue(new File(rhCLI.getFullPath() + rhCLI.getResourcesFileName()).exists());

		ResourcesHolder rh2 = ConfigUtils.resourcesHolderFromYaml(relPath + "resources-generated-with-payload-replacements-01.yaml");
		Assert.assertTrue(rh1.getPayloadReplacer().size() == rh2.getPayloadReplacer().size());
	}

}
