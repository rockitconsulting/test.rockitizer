package com.rockit.common.blackboxtester.suite.configuration;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;
import org.apache.log4j.Logger;
import com.rockit.common.blackboxtester.suite.structures.TestBuilder;

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
	    LOGGER.info("MODE: " + configuration().getString(Constants.MODE_KEY));
		
	}

	
	public static void write(String string) {
	    LOGGER.info(string);
		
	}

	public static void writeFatal(Throwable thr) {
		LOGGER.fatal("ERROR: Execution cancelled.  Please see the below Exception for details  \n\n", thr);
	}
	
	
}
