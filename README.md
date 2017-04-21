# test.rockitizer
---
[![N|Solid](http://www.rockit.consulting/images/logo-fixed.png)](http://www.rockit.consulting)

Easy-to-Use test framework for complex integration environemnts which saves you time and money up to 30% and improves the quality of development. You can write the regression suites for huge integration environments and **you will enjoy doing it**. 

*Originally developed for ESB, especially for IBM Message Broker/IBM Integration Bus, but can be used for any integration platform.* **Has been succesfully proved in numerous EAI projects**.

## Core features: 
  - Java, JUnit based framework
  - extendable connector/plugin architecture
  - Record/Replay with post Assertion
  - Separation of Testdata from Connectors
  - MQ, DB, HTTP, SCP connectors available
  - DB, File, XMLUnit assertion plugins
  - regression testing and continuous integration

## Money saving factors:
  - Complex testcases are built up within the minutes
  - One tool for everything, no need in db- ,mq- ,scp-, http-, soap- tools
  - faster delivery 
  - better quality of the product 
  


## How it works?
![Lifecycle test.rockitizer](https://rockit.atlassian.net/wiki/download/thumbnails/5439549/image2017-4-21_12-51-58.png?width=961&height=250 "Lifecycle test.rockitizer")

![Test Data separation](https://rockit.atlassian.net/wiki/download/thumbnails/5439549/image2017-4-21_12-40-16.png?width=515&height=250 "Test Data separation")

### Sample test project
![Sample test project](https://rockit.atlassian.net/wiki/download/attachments/5439549/image2017-4-21_12-44-43.png?version=1&modificationDate=1492771485552&cacheVersion=1&api=v2 "Sample test project")



## How to get it running?
1. Build the test.rockitizer framework with dependencies.
2. Create your test project having the previous artifact configured as dependency
3. Configure the testframework including your connectors
4. Write your test scenario using the step/connector/payload convention
5. Whrite the JUnit to make it fly







### Whrite the JUnit to make it fly
```java
public class SplitCustomerTest extends AbstractTestWrapper {
	public TestBuilder testBuilder = newTestBuilderFor(SplitCustomerTest.class);

	@Test
	public void sendBatchFileAndTestRouting() throws Exception {
		testBuilder.addStep("a001putCustomerBatch")
				   .addSettings("MQPUT@CUSTOMERBATCH.IN.@ENV@", SettingsBuilder
				                                                    .addMQHeader()
                    				                                .setMsgFormat("MQSTR")
                    ).execute();
		
		testBuilder.addStep("a002getCustomerBatch")
		           .sleep(5000)
		           .execute();

		testBuilder.addAssertion(
				new XMLFileAssertion("a002getCustomerBatch")
    				.withNodeMatcher(ElementSelectors.byNameAndText).
    				.ignoreAttrs(ImmutableList.of("number"))
    				.ignore(ImmutableList.of("startDate","endDate"))
    				.checkForSimilar()
		);

		testBuilder.addAssertion(
				new DBAssertion("select dbms_xmlgen.getxml('select ID,NAME from ROCKIT.CUSTOMER') xml from dual",						    ImmutableList.of("<NAME>Martin Test</NAME>", "<NAME>Max Mustermann</NAME>"))
		);
	}
}
```


License
----

MIT

