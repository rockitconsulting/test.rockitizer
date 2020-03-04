package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.util.stream.StreamSupport;

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

public class CommonCLI {
	public static final Logger log = Logger.getLogger(CommonCLI.class.getName());
	
	
	protected static final String[] banner = {
		"@|bold,green __________               __   .__  __  .__                      |@", 
        "@|bold,green \\______   \\ ____   ____ |  | _|__|/  |_|__|_______ ___________  |@",
        "@|bold,green  |       _//  _ \\_/ ___\\|  |/ /  \\   __\\  \\___   // __ \\_  __ \\ |@",
        "@|bold,green  |    |   (  <_> )  \\___|    <|  ||  | |  |/    /\\  ___/|  | \\/ |@",
        "@|bold,green  |____|_  /\\____/ \\___  >__|_ \\__||__| |__/_____ \\\\___  >__|    |@",		
        "@|bold,green         \\/            \\/     \\/                 \\/    \\/        |@"
	};
	
	
	


	public File findChildByName(final String context, final String name) {
		Iterable<File> childs = FileUtils.listFolders(new File(context));
		File file = StreamSupport.stream(childs.spliterator(), false).filter(f -> f.getName().equalsIgnoreCase(name)).findFirst().orElse(null);

		return file;

	}



}
