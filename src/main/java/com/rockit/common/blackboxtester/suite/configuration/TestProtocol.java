package com.rockit.common.blackboxtester.suite.configuration;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.suite.structures.TestBuilder;

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

public class TestProtocol {
	
	public static final Logger LOGGER = Logger.getLogger(TestProtocol.class.getName());
	
	public static String getHashSeperator(){
		return "#############################################################################";
	}

	public static String getStepSeparator() {
		return "*****************************************************************************";
	}
	
	

	public static void writeHeading(String testName, String heading) {
		LOGGER.info(TestProtocol.getHashSeperator());
		LOGGER.info( "# \t\t <"+testName+">: " + heading);
		LOGGER.info(TestProtocol.getHashSeperator());
	}

	public static void writeStep(String stepName) {
		LOGGER.info("");
		LOGGER.info(TestProtocol.getStepSeparator());
		LOGGER.info( stepName + "\t Step Added. Executing... ");
	}
	

	public static void write(TestBuilder testBuilder) {
	    LOGGER.info("TESTNAME: " + testBuilder.getTestName());
	    LOGGER.info("RECORD FOLDER: " + testBuilder.getRecordFolder());
	    LOGGER.info("REPLAY FOLDER: " + testBuilder.getReplayFolder());
	    LOGGER.info("MODE: "+ configuration().getRunMode());
		
	}

	
	public static void write(String string) {
	    LOGGER.info(string);
		
	}
	
	public static void writeError(String m) {
		LOGGER.error("Critical: Execution cancelled. " + m);
	}
	

	public static void writeFatal(Throwable thr) {
		LOGGER.error("Critical: Execution cancelled.  Please see the below Exception for details  \n\n", thr);
	}
	
	
}
