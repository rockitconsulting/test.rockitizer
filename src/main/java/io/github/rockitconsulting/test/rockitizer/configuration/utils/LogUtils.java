package io.github.rockitconsulting.test.rockitizer.configuration.utils;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class LogUtils {
	
	public static final Logger log = Logger.getLogger(LogUtils.class.getName());
	
	
	public static void enableLogging () {
		System.clearProperty(Constants.SHOWLOG);
		
	}
	
	public static void disableLogging() {
		System.setProperty(Constants.SHOWLOG, "false");
	}
	
	
	public static void LogInfo(String m) {
		if(!isShowLogDisabled()) {
			log.info(m);
		}
	}

	private static boolean isShowLogDisabled () {
		
		if( System.getProperty(Constants.SHOWLOG) != null && System.getProperty(Constants.SHOWLOG).equalsIgnoreCase("false") ) {
			return true;
		} else {
			return false;
		}
		
	}

	public static void LogWarn(String m) {
		if(!isShowLogDisabled()) {
			log.warn(m);
		}

		
	}

	public static void LogError(String m, Throwable thr) {
		if(!isShowLogDisabled()) {
			log.error(m, thr);
		}

		
	}
	
	
}

