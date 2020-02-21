package io.github.rockitconsulting.test.rockitizer.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Command(name = "rockit", mixinStandardHelpOptions = true, version = "subcommand demo 3.0",
description = "Rockitizer is ......",
commandListHeading = "%nCommands:%n%nThe most commonly used rockitizer commands are:%n",
footer = "%nSee 'rockit help <command>' to read about a specific subcommand or concept.",
subcommands = {
        RockitizerCreateTC.class,
        RockitizerCreateTS.class,
        RockitizerCreateConn.class,
        RockitizerCreateConfig.class,
        RockitizerDeleteTC.class,
        RockitizerSync.class,
        RockitizerList.class,
        RockitizerValidate.class,
        CommandLine.HelpCommand.class
})

public class RockitizerCLI implements Runnable {

	public static void main(String[] args) {
        System.exit(new CommandLine(new RockitizerCLI()).execute(args));
    }

	@Spec
    CommandSpec spec;
	
	@Override
	public void run() {
		// if the command was invoked without subcommand, show the usage help
        spec.commandLine().usage(System.err);
		
	}

}
