package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.model.Environment;
import io.github.rockitconsulting.test.rockitizer.configuration.model.EnvironmentsHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

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

		Environment env1 =  new Environment();
		env1.setName("myenv1");
		env1.setProps(ImmutableMap.of("k1","v1","k2","v2","k3","v3"));
		eh.getEnvs().add(env1);
		Environment env2 =  new Environment();
		env2.setName("myenv2");
		env2.setProps(ImmutableMap.of("k1","v1","k2","v2","k3","v3"));
		eh.getEnvs().add(env2);
		Environment env3 = new Environment();
		env3.setName("myenv3");
		env3.setProps(ImmutableMap.of("k3","zzzzzzz3"));
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
		Assert.assertTrue(envsHolderFromYaml.getEnvs().size()==3);
		
	
	}
}
