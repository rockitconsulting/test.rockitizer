package io.github.rockitconsulting.test.rockitizer.cli;

import io.github.rockitconsulting.test.rockitizer.configuration.utils.LogUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

@Command(name = Constants.CLI_COMMAND, mixinStandardHelpOptions = true, version = "rockitizer 1.0", commandListHeading = "%nCommands:%n%nThe most commonly used rockitizer commands are:%n", footer = "%nSee '"
		+ Constants.CLI_COMMAND
		+ " help <command>' to read about a specific subcommand or concept.%n "
		+ "%n Triggered command chains:"
		+ "%n create-test       = create-test + sync "
		+ "%n delete-test       = delete-test + sync "
		+ "%n create-config     = create-env  + validate "
		+ "%n run 	           = validate    + run "
		+ "%n list-resources    = sync        + list-resources  "
		+ "%n list-testcases    = sync        + list-testcases "
		+ "%n validate          = sync        + validate ",

subcommands = { RockitizerRunTest.class, RockitizerListTestCases.class, RockitizerListResources.class, RockitizerCreateTest.class, RockitizerDeleteTest.class,
		RockitizerCreateConfig.class, RockitizerDeleteConfig.class, RockitizerSync.class, RockitizerValidate.class, CommandLine.HelpCommand.class })
public class RockitizerCLI extends CommonCLI implements Runnable {

	public static void main(String[] args) {
		System.setProperty("picocli.usage.width", "200");
		LogUtils.disableLogging();
		for (String line : banner) {
			System.out.println(CommandLine.Help.Ansi.AUTO.string(line));
		}

		int execute = new CommandLine(new RockitizerCLI()).execute(args);

		LogUtils.enableLogging();
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
