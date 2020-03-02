package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;



public class CommonCLI {
	public static final Logger log = Logger.getLogger(CommonCLI.class.getName());
	
	
	protected static final String[] banner = {
		"@|green __________               __   .__  __  .__                      |@", 
        "@|green \\______   \\ ____   ____ |  | _|__|/  |_|__|_______ ___________  |@",
        "@|green  |       _//  _ \\_/ ___\\|  |/ /  \\   __\\  \\___   // __ \\_  __ \\ |@",
        "@|green  |    |   (  <_> )  \\___|    <|  ||  | |  |/    /\\  ___/|  | \\/ |@",
        "@|green  |____|_  /\\____/ \\___  >__|_ \\__||__| |__/_____ \\\\___  >__|    |@",		
        "@|green         \\/            \\/     \\/                 \\/    \\/        |@"
	};
	
	
	


	public File findChildByName(final String context, final String name) {
		Iterable<File> childs = FileUtils.listFolders(new File(context));
		File file = StreamSupport.stream(childs.spliterator(), false).filter(f -> f.getName().equalsIgnoreCase(name)).findFirst().orElse(null);

		return file;

	}



}
