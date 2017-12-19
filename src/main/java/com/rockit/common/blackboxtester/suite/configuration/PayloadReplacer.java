package com.rockit.common.blackboxtester.suite.configuration;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.configuration.ConfigurationMap;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Logger;

public class PayloadReplacer {

	public static final Logger LOGGER = Logger.getLogger(PayloadReplacer.class.getName());
	public static void interpolate(File file)  {
		String content;
		try {
			content = new String(Files.readAllBytes(file.toPath()));
			String replace = StrSubstitutor.replace(content, new ConfigurationMap( configuration()));
			Files.write(file.toPath(), replace.getBytes());

		} catch (IOException e) {
			LOGGER.error( file  +  " interpolation error ", e);
			
		}
		
	}
	
	

	
}
