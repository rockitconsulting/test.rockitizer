package com.rockit.common.blackboxtester.connector.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlunit.diff.ElementSelectors;

import com.google.common.collect.ImmutableList;
import com.rockit.common.blackboxtester.assertions.XMLFileAssertion;
import com.rockit.common.blackboxtester.suite.structures.TestBuilder;
import com.rockit.common.blackboxtester.wrapper.AbstractTestWrapper;

public class HTTPRestClientIntegration extends AbstractTestWrapper {
	public static Logger logger = Logger.getLogger(HTTPRestClientIntegration.class.getName());
	public TestBuilder testBuilder = newTestBuilderFor(HTTPRestClientIntegration.class);

	@Test
	public void allInOneTest() throws Exception {

		testBuilder.addStep("a001MakePost").execute();

		testBuilder.addAssertion(

				new XMLFileAssertion("a001MakePost").withNodeMatcher(ElementSelectors.byNameAndText)
						.ignoreAttrs(ImmutableList.of("number"))
						.ignore(ImmutableList.of("CF-RAY", "ETag", "Access-Control-Allow-Origin", "Connection",
								"Set-Cookie", "Date", "Expect-CT", "X-Powered-By", "createdAt", "id"))
						.checkForSimilar());

	}
}
