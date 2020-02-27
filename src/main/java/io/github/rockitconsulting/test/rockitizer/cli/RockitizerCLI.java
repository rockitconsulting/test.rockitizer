package io.github.rockitconsulting.test.rockitizer.cli;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Command(name = Constants.CLI_COMMAND, mixinStandardHelpOptions = true, version = "subcommand demo 3.0",
description = "Rockitizer Command-Line Interface is easy to use and  ......",
commandListHeading = "%nCommands:%n%nThe most commonly used rockitizer commands are:%n",
footer = "%nSee 'rockit help <command>' to read about a specific subcommand or concept.%n %nTriggered command chains:",
subcommands = {
        RockitizerCreateTC.class,
        RockitizerCreateTS.class,
        RockitizerCreateConn.class,
        RockitizerJunitStarter.class,
        RockitizerCreateConfig.class,
        RockitizerDeleteTC.class,
        RockitizerDeleteTS.class,
        RockitizerDeleteConn.class,
        RockitizerDeleteConfig.class,
        RockitizerSyncConfig.class,
        RockitizerSyncTC.class,
        RockitizerListAll.class,
        RockitizerListTC.class,
        RockitizerListConfig.class,
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
