package io.github.rockitconsulting.test.rockitizer.configuration.utils;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

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

