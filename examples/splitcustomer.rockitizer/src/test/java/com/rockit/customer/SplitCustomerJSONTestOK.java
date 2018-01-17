package com.rockit.customer;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.rockit.common.blackboxtester.assertions.XMLFileAssertion;
import com.rockit.common.blackboxtester.suite.structures.TestBuilder;
import com.rockit.common.blackboxtester.wrapper.AbstractTestWrapper;

public class SplitCustomerJSONTestOK extends AbstractTestWrapper {
	public static Logger logger = Logger.getLogger(SplitCustomerJSONTestOK.class.getName());
	public TestBuilder testBuilder = newTestBuilderFor(SplitCustomerJSONTestOK.class);


	@Test
	public void executeAndTestGetJson() throws Exception {
		testBuilder.addStep("a001getJsonResp").execute();

		testBuilder.addAssertion(
				new XMLFileAssertion("a001getJsonResp")
    				.checkForSimilar()
		);
	}
}
