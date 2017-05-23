# Basic execution flow
According to the maven conventions the following folders are important:
- `src/test/resources/` - location of the test plans
- `src/test/java/` - location of the junit test runner

Junit serves as glue and looks for the test plan with the same name starting the execution from the root folder of the corresponding test plan:
1. each step of the test is the folder which is executed via `testBuilder.addStep("foldername").execute()` command. During execution all connectors within the folder are processed automatically according to the connector type PUT/GET.
2. The results, if any, are kept in the `output` within the step and corresponding connector folder.  
3. The `record` mode puts the output data inside `src/test/resources/<testname>/output/<teststep>/<connector>`, the `replay` stores the temporary data within the `target/replay/<testname>/output/<teststep>/<connector>`
4. Within the last step of `replay` mode the configured assertions `testBuilder.addAssertion(<AssertionImpl>(<fodername>)` being processed.

![Sample test project](http://www.rockit.consulting/images/github/test_rockitizer_project.PNG "Sample test project; IBM Integration Bus; Integration testing; Test framework;test.rockitizer")

For the fast and easy start with the `test.project` use the provided [example](examples/README.md)


## <a name="configuration"></a> Connector configuration
The following configuration is available for your test project: 
1. `config.mq.properties` - MQ Configuration
2. `config.properties` - all other connectors
3. `xyz.properties` - optional maven profile to support multiple environments 

According to placeholders pattern the `config.properties` are allowed to store the placeholders `${propertyName}` which will be replaces with the value of the same key in `xyz.properties`.

Basically it allows to make all connector settings in main files environment independent, providing the exact values using maven profiles. 



### <a name="confimqproperties"></a> config.mq.properties - MQ Configuration
Configure the mq connection settings

   ```

########################################################################
#  Environment Index (IDX Variable), can be used to provide 
#  the environment dependent queues, case the nameing convention allows.
#  i.e. MQENV = TEST 
#  Test-plan connector: MQGET@SPLITCUSTOMER.ERROR.OUT.@ENV@
#  Configuration: MQGET@ = SPLITCUSTOMER.CUSTOMER.OUT.@ENV@, SPLITCUSTOMER.ERROR.OUT.@ENV@
########################################################################
MQENV = 
########################################################################
#  MQ Manager Connection  
########################################################################
MQ@MQMANAGER.NAME=IB9QMGR
MQ@MQMANAGER.PORT=1414
MQ@MQMANAGER.HOST=localhost
MQ@MQMANAGER.CHANNEL=SYSTEM.BKR.CONFIG

########################################################################
#  MQ OUT Queue List generated from IIB Project  
########################################################################
MQGET@ = SPLITCUSTOMER.CUSTOMER.OUT, SPLITCUSTOMER.ERROR.OUT
   ```


### <a name="testprojectconfiguration"></a> config.properties - project configuration

 ```
###############################
#   Test Mode [record/replay] 
#   replay mode captures output and executes  assertions against recorded "master" results
###############################
suite.mode = replay

###############################
#   DB Connection
###############################
#dataSource.username= ${dataSource.username}
#dataSource.password= ${dataSource.password}
#dataSource.url= ${dataSource.url}

#DBGET@CUSTOMERS = SELECT NAME FROM ROCKIT.CUSTOMER ORDER BY NAME DESC

###############################
#   HTTP Connection
###############################
#HTTPGET@MYHTTPCONNECTOR=http://google.com/getz
```
   
  

### <a name="testplan"></a> Creating the testplan


### <a name="junit"></a> Write the JUnit to make it fly
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
	}
}
```

---
[Back to Readme](../README.md)

