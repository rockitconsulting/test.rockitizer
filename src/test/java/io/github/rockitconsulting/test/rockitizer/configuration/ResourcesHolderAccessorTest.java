package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.ResourcesHolderAccessor;
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

public class ResourcesHolderAccessorTest {

	public static Logger log = Logger.getLogger(ResourcesHolderAccessorTest.class.getName());

	final String rootPath = ConfigUtils.getAbsolutePathToResources();
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
		ResourcesHolder rh1 = rhCLI.resourcesHolderFromFileSystemToYaml(null);

		ResourcesHolder rh2 = ConfigUtils.resourcesHolderFromYaml(rootPathTestSrc + "resources-generated.yaml");

		Assert.assertTrue("db connectors sizes:  " + rh1.getDbConnectors().size() + "/" + rh2.getDbConnectors().size(), rh1.getDbConnectors().size() == rh2
				.getDbConnectors().size());
		Assert.assertTrue("http connectors sizes: " + rh1.getHttpConnectors().size() + "/" + rh2.getHttpConnectors().size(),
				rh1.getHttpConnectors().size() == rh2.getHttpConnectors().size());
		Assert.assertTrue("mq connectors sizes: " + rh1.getMqConnectors().size() + "/" + rh2.getMqConnectors().size(), rh1.getMqConnectors().size() == rh2
				.getMqConnectors().size());
	}

	@Test
	public void generateResources() throws IOException {
		rhCLI.setResourcesFileName("resources-generated-01.yaml");
		ResourcesHolder rh1 = rhCLI.resourcesHolderFromFileSystemToYaml(null);
		Assert.assertNotNull(rh1);
		Assert.assertTrue(new File(rhCLI.getFullPath() + rhCLI.getResourcesFileName()).exists());

	}

	@Test
	public void generateResourcesWithHttpHeaders() throws IOException {
		rhCLI.setResourcesFileName("resources-generated-with-http-headers-01.yaml");
		ResourcesHolder rh1 = rhCLI.resourcesHolderFromFileSystemToYaml(null);
		
		rh1.getHttpConnectors().get(0).getHeaders().put("key1", "value1");
		rh1.getHttpConnectors().get(0).getHeaders().put("key2", "value2");
		rhCLI.resourcesHolderToYaml(rh1);
		Assert.assertNotNull(rh1);
		Assert.assertTrue(new File(rhCLI.getFullPath() + rhCLI.getResourcesFileName()).exists());

	}
	
	
	@Test
	public void generateResourcesWithPlaceholders() throws IOException {

		Map<String, String> replacements = new HashMap<>();
		replacements.put("KEY1", "VALUE1");
		replacements.put("KEY2", "VALUE2");

		rhCLI.setResourcesFileName("resources-generated-with-payload-replacements-01.yaml");
		ResourcesHolder rh1 = rhCLI.resourcesHolderFromFileSystemToYaml(replacements);

		Assert.assertNotNull(rh1);
		Assert.assertTrue(new File(rhCLI.getFullPath() + rhCLI.getResourcesFileName()).exists());

		ResourcesHolder rh2 = ConfigUtils.resourcesHolderFromYaml(rootPathTestSrc + "resources-generated-with-payload-replacements-01.yaml");
		Assert.assertTrue(rh1.getPayloadReplacer().size() == rh2.getPayloadReplacer().size());
	}

}
