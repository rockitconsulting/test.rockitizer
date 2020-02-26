package io.github.rockitconsulting.test.rockitizer.cli;

import java.io.FileWriter;
import java.io.IOException;

public class TemplateCLI {
	
	
	public void createJunitClass(String testcase, String path){
		

		try (FileWriter myWriter = new FileWriter(path)) {
			myWriter.write("import org.apache.log4j.Logger;"+System.lineSeparator());
			myWriter.write("import org.junit.Test;"+System.lineSeparator());
			myWriter.write("import org.xmlunit.diff.ElementSelectors;"+System.lineSeparator());
			myWriter.write(" "+System.lineSeparator());
			myWriter.write("import com.google.common.collect.ImmutableList;"+System.lineSeparator());
			myWriter.write("import com.rockit.common.blackboxtester.assertions.XMLFileAssertion;"+System.lineSeparator());
			myWriter.write("import com.rockit.common.blackboxtester.suite.structures.TestBuilder;"+System.lineSeparator());
			myWriter.write("import com.rockit.common.blackboxtester.wrapper.AbstractTestWrapper;"+System.lineSeparator());
			myWriter.write(" "+System.lineSeparator());
			myWriter.write("public class " + testcase +" extends AbstractTestWrapper {"+System.lineSeparator());
			myWriter.write("	public static Logger logger = Logger.getLogger("+testcase+".class.getName());"+System.lineSeparator());
			myWriter.write("	public TestBuilder testBuilder = newTestBuilderFor("+testcase+".class);"+System.lineSeparator());
			myWriter.write(" "+System.lineSeparator());
			myWriter.write("	@Test"+System.lineSeparator());
			myWriter.write("	public void test"+testcase.replaceFirst("Test","")+"() throws Exception {"+System.lineSeparator());
			myWriter.write(" "+System.lineSeparator());
			myWriter.write(" "+System.lineSeparator());
			myWriter.write("	}"+System.lineSeparator());
			myWriter.write("}"+System.lineSeparator());
		    myWriter.close();
		} catch (IOException e) {
			System.err.println("Failed by generation: "+ e);
		}
	      System.out.println("Successfully wrote to the file.");
		
	}
	

}
