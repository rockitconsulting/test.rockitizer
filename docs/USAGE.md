# Sample test project
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
   
   
---
[Back to Readme](../README.md)

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
