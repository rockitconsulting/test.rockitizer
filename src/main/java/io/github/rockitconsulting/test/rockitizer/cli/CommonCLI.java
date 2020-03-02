package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.TestCasesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;



public class CommonCLI {
	public static final Logger log = Logger.getLogger(CommonCLI.class.getName());
	
	
	public static final String[] banner = {
		"@|green __________               __   .__  __  .__                      |@", 
        "@|green \\______   \\ ____   ____ |  | _|__|/  |_|__|_______ ___________  |@",
        "@|green  |       _//  _ \\_/ ___\\|  |/ /  \\   __\\  \\___   // __ \\_  __ \\ |@",
        "@|green  |    |   (  <_> )  \\___|    <|  ||  | |  |/    /\\  ___/|  | \\/ |@",
        "@|green  |____|_  /\\____/ \\___  >__|_ \\__||__| |__/_____ \\\\___  >__|    |@",		
        "@|green         \\/            \\/     \\/                 \\/    \\/        |@"
	};
	
	
	
	/**
	 * CLI relevant: print testsuite
	 */
	public void listAll() {
		Iterable<File> testCases = FileUtils.listFolders(new File(configuration().getFullPath()));
		testCases.forEach(tcase -> {
			System.out.println("====================TC=======================================");
			System.out.println(tcase.getName());
			FileUtils.listFolders(tcase).forEach(tstep -> {
				System.out.println("	\\_" + tstep.getName());
				FileUtils.listFolders(tstep).forEach(connector -> {
					System.out.println("		\\__" + connector.getName());
					FileUtils.listFiles(connector).forEach(payload -> {
						System.out.println("			\\___" + payload.getName());

					});
				});

			});
			;

		});
	}


	public File findChildByName(final String context, final String name) {
		Iterable<File> childs = FileUtils.listFolders(new File(context));
		File file = StreamSupport.stream(childs.spliterator(), false).filter(f -> f.getName().equalsIgnoreCase(name)).findFirst().orElse(null);

		return file;

	}


	public void deleteConfig(String path) {

		File jUnitFile = new File(path);

		if (jUnitFile.delete()) {
			System.out.println(" File deleted successfully");
		} else {
			System.out.println(" Failed to delete the file");
		}

	}

}
