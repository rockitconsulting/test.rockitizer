### Sample test project
![Sample test project](http://www.rockit.consulting/images/github/test_rockitizer_project.PNG "Sample test project; IBM Integration Bus; Integration testing; Test framework;test.rockitizer")

[Please refer to examples for details](examples/README.md)


### <a name="configuration"></a> Connector configuration


### <a name="testplan"></a> Creating the testplan


### <a name="junit"></a> Whrite the JUnit to make it fly
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
