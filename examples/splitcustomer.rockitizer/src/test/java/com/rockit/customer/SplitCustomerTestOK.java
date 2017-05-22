package com.rockit.customer;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlunit.diff.ElementSelectors;

import com.google.common.collect.ImmutableList;
import com.rockit.common.blackboxtester.assertions.XMLFileAssertion;
import com.rockit.common.blackboxtester.connector.settings.SettingsBuilder;
import com.rockit.common.blackboxtester.suite.structures.TestBuilder;
import com.rockit.common.blackboxtester.wrapper.AbstractTestWrapper;

public class SplitCustomerTestOK extends AbstractTestWrapper {
	public static Logger logger = Logger.getLogger(SplitCustomerTestOK.class.getName());
	public TestBuilder testBuilder = newTestBuilderFor(SplitCustomerTestOK.class);


	@Test
	public void sendBatchFileAndTestRouting() throws Exception {
		testBuilder.addStep("a001putCustomerBatch")
				   .addSettings("MQPUT@SPLITCUSTOMER.BATCH.IN", SettingsBuilder
				                                                    .addMQHeader()
                    				                                .setMsgFormat("MQSTR")
                    ).execute();
		
		testBuilder.addStep("a002getCustomerBatch")
		           .sleep(1000)
		           .execute();

		testBuilder.addAssertion(
				new XMLFileAssertion("a002getCustomerBatch")
    				.withNodeMatcher(ElementSelectors.byNameAndText)
    				.ignoreAttrs(ImmutableList.of("number"))
    				.ignore(ImmutableList.of("startDate","endDate"))
    				.checkForSimilar()
		);
	}
}
