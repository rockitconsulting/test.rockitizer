package io.github.rockitconsulting.test.rockitizer.cli;

import org.fusesource.jansi.AnsiConsole;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Ansi;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

@Command(name = Constants.CLI_COMMAND,
usageHelpAutoWidth = true, mixinStandardHelpOptions = true,
version = "rockitizer 1.0",
commandListHeading = "%nCommands:%n%nThe most commonly used rockitizer commands are:%n",
footer = "%nSee '" + Constants.CLI_COMMAND + " help <command>' to read about a specific subcommand or concept.%n ",

subcommands = {
		RockitizerRunTest.class,
		RockitizerListTestCases.class,
		RockitizerListResources.class,
		RockitizerCreateTest.class,
		RockitizerDeleteTest.class,
		RockitizerCreateConfig.class,
		RockitizerDeleteConfig.class,
		RockitizerSync.class,
		RockitizerValidate.class,
		CommandLine.HelpCommand.class
		})

public class RockitizerCLI extends CommonCLI implements Runnable {

	public static void main(String[] args) {
		System.setProperty("picocli.usage.width", "400");
		LogUtils.disableLogging();
		AnsiConsole.systemInstall(); 
		for (String line : banner) {
			System.out.println(CommandLine.Help.Ansi.AUTO.string(line));
		}
		
		System.out.println();

		
		int execute = new CommandLine(new RockitizerCLI()).setColorScheme( CommandLine.Help.defaultColorScheme(Ansi.ON ) ).execute(args);

		
		LogUtils.enableLogging();
		System.clearProperty(Constants.INIT_CONFIG_FROM_FILESYSTEM_KEY);
		System.clearProperty(Constants.ENV_KEY);

		System.out.println();
		System.out.println();

		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,underline,green test.rockizer Copyright (C) 2020 rockit.consutling GmbH |@"));
		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|white This program is distributed under GPL v3.0 License and comes with ABSOLUTELY NO WARRANTY |@"));
		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|white This is free software, and you are welcome to redistribute it under GPL v3.0 conditions |@"));

		AnsiConsole.systemUninstall();
		
		System.exit(execute);
	}

	@Spec
	CommandSpec spec;

	@Override
	public void run() {
		// if the command was invoked without subcommand, show the usage help
		spec.commandLine().usage(System.err);

	}

}
