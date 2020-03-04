package com.rockit.common.blackboxtester.connector.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlunit.diff.ElementSelectors;

import com.google.common.collect.ImmutableList;
import com.rockit.common.blackboxtester.assertions.XMLFileAssertion;
import com.rockit.common.blackboxtester.suite.structures.TestBuilder;
import com.rockit.common.blackboxtester.wrapper.AbstractTestWrapper;

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
