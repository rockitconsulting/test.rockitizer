package io.github.rockitconsulting.test.rockitizer.configuration.utils;

import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.snakeyaml.ConfigurationModelRepresenter;
import io.github.rockitconsulting.test.rockitizer.exceptions.InvalidConnectorFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class ConfigUtils {
	
	public static final String getAbsoluteRootPath() {
		return new File("").getAbsolutePath().replace('\\', '/') + "/src/test/resources/";
	}
	

	public static String connectorTypeFromFolder(File connFolder) {
		return connectorTypeFromConnectorId(connFolder.getName());

	}

	public static String connectorTypeFromConnectorId(String connId) {
		if (!connId.contains(".")) {
			throw new InvalidConnectorFormatException (connId);
		}
		return connId.contains(".") ? connId.split("\\.")[0] : connId;
	}
	
	
	public static void writeToYaml(Object container, String yamlFileNameWithAbsPath) throws IOException {
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(FlowStyle.BLOCK);
		options.setPrettyFlow(true);
		ConfigurationModelRepresenter customRepresenter = new ConfigurationModelRepresenter();

		Yaml yaml = new Yaml(customRepresenter, options);

		FileWriter fw = new FileWriter(yamlFileNameWithAbsPath, false);
		fw.write("#########################################################################################################################"
				+ System.lineSeparator());
		fw.write("############### This file has been generated. Kindly consider to replace the @@ placeholders with your values ###########"
				+ System.lineSeparator());
		fw.write("#########################################################################################################################"
				+ System.lineSeparator());
		fw.write(yaml.dumpAsMap(container));
		fw.close();

	}

	public static ResourcesHolder resourcesHolderFromYaml(String path) throws IOException {

		Constructor constructor = new Constructor(ResourcesHolder.class);
		TypeDescription customTypeDescription = new TypeDescription(ResourcesHolder.class);
		constructor.addTypeDescription(customTypeDescription);

		InputStream inputStream = new FileInputStream(path);
				//ConfigUtils.class.getClassLoader().getResourceAsStream(yamlFileNameWithRelPath);

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
	


}
