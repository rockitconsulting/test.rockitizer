package io.github.rockitconsulting.test.rockitizer.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

@Command(name = Constants.CLI_COMMAND, mixinStandardHelpOptions = true, version = "subcommand demo 3.0",
commandListHeading = "%nCommands:%n%nThe most commonly used rockitizer commands are:%n",
footer = "%nSee 'rockit help <command>' to read about a specific subcommand or concept.%n %nTriggered command chains:%ncreate-testcase = sync + create-testcase + sync %ncreate-teststep = sync + create-teststep + sync %ncreate-connector = sync + create-connector + sync %ncreate-env = sync + create-env + sync %ndelete-testcase = sync + delete-testcase + sync %ndelete-teststep = sync + delete-teststep + sync %ndelete-connector = sync + delete-connector + sync %ndelete-env = sync + delete-env + sync %n sync = sync %nrun = sync + run + sync %nlist-resources = sync + list-resources + sync %nlist-testcases = sync + list-testcases %n validate = sync + validate %n help = help ",
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
		System.setProperty("picocli.usage.width", "200");
		 for (String line : banner) {
		      System.out.println(CommandLine.Help.Ansi.AUTO.string(line));
		}
		
		
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
