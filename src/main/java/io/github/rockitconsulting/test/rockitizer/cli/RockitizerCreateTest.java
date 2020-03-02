package io.github.rockitconsulting.test.rockitizer.cli;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "create", sortOptions = false, headerHeading = "@|bold,underline Benutzung:|@%n%n", synopsisHeading = "%n", descriptionHeading = "%n@|bold,underline Description:|@%n%n", 
parameterListHeading = "%n@|bold,underline Parameters:|@%n", optionListHeading = "%n@|bold,underline Options:|@%n", header ="create-testcase <testcase> [<teststep>] [<connector>]", 
description = " Creates the test objects in the file system ")
public class RockitizerCreateTest implements Runnable {

 
	
	@Parameters(index = "0", arity = "1", description = " testCase name ")
	String testcase;

	@Parameters(index = "1", arity = "0..1", description = "testStep name ")
	String teststep;

	@Parameters(index = "2", arity = "0..1", description = " Connector : HTTP.<ID>, SCPPUT.<ID>, MQGET.<ID>, MQPUT.<ID>, FILEPUT.<ID>, FILEDEL.<ID>, FILEGET.<ID>, DBGET.<ID>, DBPUT.<ID>")
	String connector;

	@Override
	public void run() {
		
		
		try {
			if (teststep != null && connector != null) {
				createConnector();
			} else if (teststep != null) {
				createTesStep();
			} else {
				createTestCase();
			}

		} catch (IOException e) {
			System.err.println("Error occured: " + e);
		}
	

	}

	void createTestCase() throws IOException {
		String path = configuration().getFullPath() + this.testcase; 
		new File(path).mkdirs();
		createJunitClass();
		System.out.println("Successfully created: " + path);

	}

	 void createTesStep() throws IOException {
		String path = configuration().getFullPath() + this.testcase + File.separator + this.teststep;
		new File(path).mkdirs();
		System.out.println("Successfully created: " + path);

	}

	void createConnector() throws IOException {
		String path = configuration().getFullPath() + this.testcase + File.separator + this.teststep + File.separator + this.connector;
		new File(path).mkdirs();
		System.out.println("Successfully created: " + path);

	}

	void createJunitClass() {
		String path = ConfigUtils.getAbsolutePathToJava() + testcase + ".java";
		
		try (FileWriter myWriter = new FileWriter(path)) {
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
			System.err.println("Failed by generation: " + e);
		}
		System.out.println("JUnit template created: " + path);

	}

}
