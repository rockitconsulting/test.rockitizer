package io.github.rockitconsulting.test.rockitizer.configuration.utils;

import io.github.rockitconsulting.test.rockitizer.configuration.model.EnvironmentsHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.snakeyaml.ConfigurationModelRepresenter;
import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * Test.Rockitizer - API regression testing framework Copyright (C) 2020
 * rockit.consulting GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 *
 */

public class ConfigUtils {

	public static final String getAbsolutePathToRoot() {
		String path = new File("").getAbsolutePath();
		if (System.getProperty("basedir") != null && System.getProperty("app.home") != null) {
			path = path.replace("\\bin", "").replace("\\target", "").replace("\\appassembler", "");
		}

		return path.replace("\\", "/");
	}

	public static final String getAbsolutePathToResources() {
		return getAbsolutePathToRoot() + "/src/test/resources/";
	}

	public static final String getAbsolutePathToJava() {
		return getAbsolutePathToRoot() + "/src/test/java/";
	}

	public static String connectorTypeFromFolder(File connFolder) {
		return connectorTypeFromConnectorId(connFolder.getName());

	}

	public static String connectorTypeFromConnectorId(String connId) {
		ValidationUtils.validateConnector(connId);
		return connId.split("\\.")[0];
	}

	public static void writeModelObjToYaml(Object container, String yamlFileNameWithAbsPath) throws IOException {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(FlowStyle.BLOCK);
		options.setPrettyFlow(true);
		ConfigurationModelRepresenter customRepresenter = new ConfigurationModelRepresenter();

		Yaml yaml = new Yaml(customRepresenter, options);

		try (FileWriter fw = new FileWriter(yamlFileNameWithAbsPath, false)) {
			fw.write("#########################################################################################################################"
					+ System.lineSeparator());
			fw.write("############### This file has been generated. Kindly consider to replace the @@ placeholders with your values ###########"
					+ System.lineSeparator());
			fw.write("#########################################################################################################################"
					+ System.lineSeparator());
			fw.write(yaml.dumpAsMap(container));
		}

	}

	public static ResourcesHolder resourcesHolderFromYaml(String path) throws IOException {

		Constructor constructor = new Constructor(ResourcesHolder.class);
		TypeDescription customTypeDescription = new TypeDescription(ResourcesHolder.class);
		constructor.addTypeDescription(customTypeDescription);

		InputStream inputStream = new FileInputStream(path);
		// ConfigUtils.class.getClassLoader().getResourceAsStream(yamlFileNameWithRelPath);

		Yaml yaml = new Yaml(constructor);
		return yaml.load(inputStream);

	}

	public static TestCasesHolder testCasesHolderFromYaml(String path) throws IOException {

		Constructor constructor = new Constructor(TestCasesHolder.class);
		TypeDescription customTypeDescription = new TypeDescription(TestCasesHolder.class);
		constructor.addTypeDescription(customTypeDescription);

		InputStream inputStream = new FileInputStream(path);

		Yaml yaml = new Yaml(constructor);
		return yaml.load(inputStream);

	}

	public static EnvironmentsHolder envrironmentsHolderFromYaml(String path) throws IOException {

		Constructor constructor = new Constructor(EnvironmentsHolder.class);
		TypeDescription customTypeDescription = new TypeDescription(EnvironmentsHolder.class);
		constructor.addTypeDescription(customTypeDescription);

		try {
			InputStream inputStream = new FileInputStream(path);
			Yaml yaml = new Yaml(constructor);
			return yaml.load(inputStream);
		} catch (FileNotFoundException fnfe) {
			return null;
		}

	}

}
