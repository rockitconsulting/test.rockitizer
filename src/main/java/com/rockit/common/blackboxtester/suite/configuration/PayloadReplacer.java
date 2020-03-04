package com.rockit.common.blackboxtester.suite.configuration;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;





import org.apache.commons.text.StringSubstitutor;
import org.apache.log4j.Logger;

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

public class PayloadReplacer {

	public static final Logger LOGGER = Logger.getLogger(PayloadReplacer.class.getName());
	
	public static final String TEMP = System.getProperty("java.io.tmpdir") ;
	
	public static File interpolate(File file)  {
		File tmpFile = null;
		try {
			String content = new String(Files.readAllBytes(file.toPath()));
			String replace =StringSubstitutor.replace(content, configuration().getPayloadReplacements());
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
