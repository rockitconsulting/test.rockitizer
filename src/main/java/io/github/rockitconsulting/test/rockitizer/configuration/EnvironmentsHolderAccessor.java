package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.model.EnvironmentsHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import java.io.IOException;

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

public class EnvironmentsHolderAccessor extends RuntimeContext {

	public static final Logger log = Logger.getLogger(EnvironmentsHolderAccessor.class.getName());
	
	private String envsFileName = "envs.yaml";

	private EnvironmentsHolder envsHolder;

	
	
	/**
	 * Reads yam configuration into the holder object CLI relevant: instantiate
	 * Resources from yaml
	 * 
	 * @return
	 * @throws IOException
	 */
	public EnvironmentsHolder envsHolderFromYaml() throws IOException {
		return ConfigUtils.envrironmentsHolderFromYaml(getFullPath() + getEnvsFileName());
	}

	

	public void envsHolderToYaml(EnvironmentsHolder holder) throws IOException {
		ConfigUtils.writeModelObjToYaml(holder, getFullPath() + getEnvsFileName());
	}

	

	public String getEnvsFileName() {
		return envsFileName;
	}

	public void setTestcasesFileName(String testcasesFileName) {
		this.envsFileName = testcasesFileName;
	}

	public String contextAsString() {
		return "[ filename: " + getEnvsFileName() + ", absPath: " + getAbsolutePath() + ", relPath: " + getRelativePath() + "]";

	}


	public EnvironmentsHolder getEnvsHolder() {
		if(envsHolder==null) {
			try {
				envsHolder=envsHolderFromYaml();
			} catch (IOException e) {
				envsHolder=null;
			}
		}
		return envsHolder;
	}


	
	
	
	public void setEnvsHolder(EnvironmentsHolder envsHolder) {
		this.envsHolder = envsHolder;
	}


	public void setEnvsFileName(String envsFileName) {
		this.envsFileName = envsFileName;
	}

}
