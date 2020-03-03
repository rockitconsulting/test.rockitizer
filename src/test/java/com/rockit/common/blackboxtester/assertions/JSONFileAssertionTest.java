package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.ElementSelectors;

import com.google.common.collect.ImmutableList;

public class JSONFileAssertionTest {

	@Test
	public void testJSONWrapperForXMLAssertionReadFromFileMatched() throws URISyntaxException, IOException {

		assertJSON("assertions/JSONFileAssertion/test/match/one.json", "assertions/JSONFileAssertion/test/match/two.json");
	}

	@Test(expected = AssertionError.class)
	public void testJSONWrapperForXMLAssertionReadFromFileUnmatched() throws URISyntaxException, IOException {

		assertJSON("assertions/JSONFileAssertion/test/notmatch/one.json", "assertions/JSONFileAssertion/test/notmatch/two.json");
	}

	private void assertJSON(String path1, String path2) throws URISyntaxException, IOException {
		Path json1File = Paths.get(ClassLoader.getSystemResource(path1).toURI());
		File one = new File(json1File.toString());
		assertTrue(one.exists());

		Path json2File = Paths.get(ClassLoader.getSystemResource(path2).toURI());
		File two = new File(json2File.toString());
		assertTrue(two.exists());

		JSONFileAssertion jsonFileAssertion = new JSONFileAssertion(null);
		String jsonToXMLFile1 = jsonFileAssertion.jsonToXMLFile(one);
		String jsonToXMLFile2 = jsonFileAssertion.jsonToXMLFile(two);

		System.out.println("one -> " + jsonToXMLFile1);
		System.out.println("two -> " + jsonToXMLFile2);

		jsonFileAssertion.ignore(ImmutableList.of("dateTime", "create_ms", "total_ms", "elastic_ms", "_id", "sort", "total", "took"))
				.compare(Input.fromString(jsonToXMLFile1), Input.fromString(jsonToXMLFile2)).withNodeMatcher(ElementSelectors.byNameAndText)
				.checkForIdentical().build();
	}

}
