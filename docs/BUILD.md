Main Readme
----
[Readme](../README.md)


# test.rockitizer - keep your integration tested!
[![N|Solid](http://www.rockit.consulting/images/logo-fixed.png)](http://www.rockit.consulting)


Easy-to-Use test framework for complex integration environemnts. It treats the ESB as a blackbox, knowing the IN/OUT connectors and the payloads provided by test plan. The JUnit triggers the test execution which has basically the following steps: submit the payloads to IN connectors, get the payloads/states from OUT connectors, finally compares the results with recorded "master". This gives you basically the "total-control" on your integration environment and keeps you informed about all relevant interface changes.

![Lifecycle test.rockitizer](http://www.rockit.consulting/images/github/test_rockitizer_lifecycle.PNG "Lifecycle test.rockitizer;IBM Integration Bus; Integration testing; Test framework;test.rockitizer")

Furthermore, the framework enables the "test first" approach, thus developing against pre-defined "target" interface until the replay snapshot matches.

**And you will enjoy of using it!**. 

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

![Test Data separation](http://www.rockit.consulting/images/github/test_rockitizer_process.PNG "Test Data separation;IBM Integration Bus; Integration testing; Test framework;test.rockitizer")

### Sample test project
![Sample test project](http://www.rockit.consulting/images/github/test_rockitizer_project.PNG "Sample test project; IBM Integration Bus; Integration testing; Test framework;test.rockitizer")



## How to get it running?
1. Build the test.rockitizer framework with dependencies.
2. Create your test project having the previous artifact configured as dependency
3. Configure the testframework including your connectors
4. Write your test scenario using the step/connector/payload convention
5. Whrite the JUnit to make it fly

## Examples
[Examples Readme](examples/README.md)






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

