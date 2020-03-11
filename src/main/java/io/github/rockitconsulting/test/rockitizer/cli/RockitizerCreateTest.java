package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "create-test", sortOptions = false, 
headerHeading = "@|bold,underline Usage:|@%n%n", synopsisHeading = "%n", 
descriptionHeading = "%n@|bold,underline Description:|@%n%n", 
parameterListHeading = "%n@|bold,underline Parameters:|@%n", 
optionListHeading = "%n@|bold,underline Options:|@%n", 
header ="cli create-test <testcase> [<teststep>] [<connector>]", 
description = " Creates the test objects in the file system. E.g.:%n%n"
		+ " FILEinFILEOutTest%n" 
		+ "       \\_0BEFORE%n"
		+ "              \\__FILEDEL.IN.FILE2FILE%n"
		+ "              \\__FILEDEL.OUT.FILE2FILE%n"
		+ "       \\_a001FILEPutMessage%n"
		+ "              \\__FILEPUT.IN.FILE2FILE%n"
		+ "       \\_a002FILEGetMessage%n"
		+ "              \\__FILEGET.OUT.FILE2FILE%n",
footer = "%nNotice: You are free to use any file manager to create testcases."
		+ "")

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

public class RockitizerCreateTest implements Runnable {

 
	
	@Parameters(index = "0", arity = "1", description = "TestCase name (For Maven compatibility, best practice is to use \"Test\" in testcase names. E.g. \"MyFirstTest\")")
	String testcase;

	@Parameters(index = "1", arity = "0..1", description = "TestStep name (For name-based sorting, best practice is to use indexing in teststep names. E.g. \"0InitialStep\", \"1NextStep\" etc.)")
	String teststep;

	@Parameters(index = "2", arity = "0..1", description = "Connector: HTTP.<ID>, SCPPUT.<ID>, MQGET.<ID>, MQPUT.<ID>, FILEPUT.<ID>, FILEDEL.<ID>, FILEGET.<ID>, DBGET.<ID>, DBPUT.<ID>")
	String connector;

	@Override
	public void run() {
		
		File fPath = new File(ConfigUtils.getAbsolutePathToJava() + testcase + ".java");
		
		try {
			if (teststep != null && connector != null) {
				ValidationUtils.validateConnector(connector);
				createConnector();
			} else if (teststep != null) {
				createTesStep();
			} else {
				createTestCase();
			}
			
			if(!fPath.exists()){
				createJunitClass(fPath);
			}

		} catch (Exception e) {
			System.err.println(CommandLine.Help.Ansi.AUTO.string("@|bold,red Error occured: |@" + e));
		}
	}

	void createTestCase() throws IOException {
		String path = configuration().getFullPath() + this.testcase; 
		new File(path).mkdirs();
		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Successfully created: |@" + path));

	}

	 void createTesStep() throws IOException {
		String path = configuration().getFullPath() + this.testcase + File.separator + this.teststep;
		new File(path).mkdirs();
		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Successfully created: |@" + path));

	}

	void createConnector() throws IOException {
		String path = configuration().getFullPath() + this.testcase + File.separator + this.teststep + File.separator + this.connector;
		new File(path).mkdirs();
		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Successfully created: |@" + path));

	}

	void createJunitClass(File fPath) {
		String sPath = fPath.toString();
		
		try (FileWriter myWriter = new FileWriter(sPath)) {
			myWriter.write("import org.apache.log4j.Logger;" + System.lineSeparator());
			myWriter.write("import org.junit.Test;" + System.lineSeparator());
			myWriter.write("import org.xmlunit.diff.ElementSelectors;" + System.lineSeparator());
			myWriter.write(" " + System.lineSeparator());
			myWriter.write("import com.google.common.collect.ImmutableList;" + System.lineSeparator());
			myWriter.write("import com.rockit.common.blackboxtester.assertions.XMLFileAssertion;" + System.lineSeparator());
			myWriter.write("import com.rockit.common.blackboxtester.suite.structures.TestBuilder;" + System.lineSeparator());
			myWriter.write("import com.rockit.common.blackboxtester.wrapper.AbstractTestWrapper;" + System.lineSeparator());
			myWriter.write(" " + System.lineSeparator());
			myWriter.write("public class " + testcase + " extends AbstractTestWrapper {" + System.lineSeparator());
			myWriter.write("	public static Logger logger = Logger.getLogger(" + testcase + ".class.getName());" + System.lineSeparator());
			myWriter.write("	public TestBuilder testBuilder = newTestBuilderFor(" + testcase + ".class);" + System.lineSeparator());
			myWriter.write(" " + System.lineSeparator());
			myWriter.write("	@Test" + System.lineSeparator());
			myWriter.write("	public void test" + testcase.replaceFirst("Test", "") + "() throws Exception {" + System.lineSeparator());
			myWriter.write(" " + System.lineSeparator());
			myWriter.write(" " + System.lineSeparator());
			myWriter.write("	}" + System.lineSeparator());
			myWriter.write("}" + System.lineSeparator());
			myWriter.close();
		} catch (IOException e) {
			System.err.println(CommandLine.Help.Ansi.AUTO.string("@|bold,red Failed by generation: |@" + e));
		}
		System.out.println(CommandLine.Help.Ansi.AUTO.string("@|bold,green Successfully JUnit template created: |@" + sPath));

	}

}
