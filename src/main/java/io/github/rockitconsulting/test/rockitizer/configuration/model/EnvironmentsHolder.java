package io.github.rockitconsulting.test.rockitizer.configuration.model;

import java.util.ArrayList;
import java.util.List;

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

public class EnvironmentsHolder  {
	
	private List<Environment> envs 	  =  new ArrayList<>();	
	
	

	public EnvironmentsHolder() {}




	public List<Environment> getEnvs() {
		return envs;
	}




	public void setEnvs(List<Environment> envs) {
		this.envs = envs;
	}

	
	
	
	
	
}
