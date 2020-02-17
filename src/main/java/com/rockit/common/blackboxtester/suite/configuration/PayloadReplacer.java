package com.rockit.common.blackboxtester.suite.configuration;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Logger;

/**
 * @author rockit2lp
 * Implements the environment dependent configuration of payload. 
 * No modification of test sources takes place, the whole interpolation takes place in temp folder
 */
public class PayloadReplacer {

	public static final Logger LOGGER = Logger.getLogger(PayloadReplacer.class.getName());
	
	public static final String TEMP = System.getProperty("java.io.tmpdir") ;
	
	public static File interpolate(File file)  {
		File tmpFile = null;
		try {
			String content = new String(Files.readAllBytes(file.toPath()));
			String replace = StrSubstitutor.replace(content, configuration().getPayloadReplacements());
			tmpFile = new File(TEMP + File.separator + System.nanoTime() + File.separator + file.getName());
			tmpFile.getParentFile().mkdirs();
			LOGGER.debug("interpolate " + file.toPath() + " to temp " + tmpFile.toPath());
			Files.write(tmpFile.toPath(), replace.getBytes());
			

		} catch (IOException e) {
			LOGGER.error( file  +  " interpolation error ", e);
			
		}
		return tmpFile;
		
	}

	
	

	
}
