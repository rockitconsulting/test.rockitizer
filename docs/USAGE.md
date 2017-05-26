# <a name="basicexecutionflow"></a> Basic execution flow
According to the maven conventions the following folders are important:
- `src/test/resources/` - location of the test plans
- `src/test/java/` - location of the junit test runner

Junit serves as glue and looks for the test plan with the same name starting its the execution from the root folder and has following steps:
1. each step of the test is the folder which is executed via `testBuilder.addStep("foldername").execute()` command. During execution all connectors within the folder are processed automatically according to the connector type PUT/GET.
2. The results, if any, are kept in the `output` within the step and corresponding connector folder.  
3. The `record` mode puts the output data inside `src/test/resources/<testname>/output/<teststep>/<connector>`, the `replay` stores the temporary data within the `target/replay/<testname>/output/<teststep>/<connector>`
4. Within the last step of `replay` mode the configured assertions `testBuilder.addAssertion(<AssertionImpl>(<fodername>)` being processed.

## Sample project layout
![Sample test project](http://www.rockit.consulting/images/github/test_rockitizer_project.PNG "Sample test project; IBM Integration Bus; Integration testing; Test framework;test.rockitizer")

For the fast and easy start with the `test.project` use the provided [example](../examples/README.md)


# <a name="configuration"></a> Test project configuration
The following configuration files are available for your test project: 
1. `config.mq.properties` - MQ Configuration
2. `config.properties` - all other connectors
3. `env_xyz.properties` - optional maven profile to support multiple environments 
4. `pom.xml` - maven project file maintains the profiles like `env_xyz.properties`

According to placeholders pattern the `config.properties` are allowed to store the placeholders `${propertyName}` which will be replaces with the value of the same key in `env_xyz.properties`.

Basically it allows to make all connector settings in main files environment independent, providing the exact values using maven profiles. 

## Connectors
### MQGet
Description: reads all messages from queue as xmls

Testplan: 
        
	-MQGET@MY.QUEUE.NAME
	-MQGET@MY.QUEUE.NAME.@ENV@
	
	
config.mq.properties or config.properties:
        
	MQENV=ENV #support for environment dependent queues
	MQ@MQMANAGER.NAME=<IB9QMGR>
	MQ@MQMANAGER.PORT=<1414>
	MQ@MQMANAGER.HOST=<localhost>
	MQ@MQMANAGER.CHANNEL=<SYSTEM.BKR.CONFIG>


### MQPut
Description: puts all messages from connector folder into queue 
Testplan: 
        
	-MQPUT@MY.QUEUE.NAME
	-MQGET@MY.QUEUE.NAME.@ENV@
	
	
config.mq.properties or config.properties:
        
	MQENV=ENV #support for environment dependent queues
	MQ@MQMANAGER.NAME=<IB9QMGR>
	MQ@MQMANAGER.PORT=<1414>
	MQ@MQMANAGER.HOST=<localhost>
	MQ@MQMANAGER.CHANNEL=<SYSTEM.BKR.CONFIG>

### HTTPGet
Description: gets http/https response as xml
Testplan: 
         
	 HTTPGET@<MYHTTPCONNECTOR>
config.properties:
         
	 HTTPGET@MYHTTPCONNECTOR=http://google.com/get


### SCPPut
Description: writes the connector payload to configured destination
Testplan: 
        
	SCPPUT@<YOURNAME> 
config.properties:
	
	SCPPUT@<YOURNAME>.HOST=
	SCPPUT@<YOURNAME>.USR=
	SCPPUT@<YOURNAME>.PWD=
	SCPPUT@<YOURNAME>.PATH=

### DBGet
Description: executes the configured SQL and stores it as xml 
Testplan: 
        
	DBGET@<YOURNAME> 
config.properties:
        
	DBGET@<YOURNAME> = SELECT * FROM TABLE
        dataSource.username= 
	dataSource.password= 
	dataSource.url= 


### DBPut

Description: executes the connector payloads against configured db
Testplan: 
        
	DBPUT@<YOURNAME> 
config.properties:

        dataSource.username= 
	dataSource.password= 
	dataSource.url= 
	



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

#### Supported connectors
- `MQGET`/`MQPUT`, `DBGET`/`DBPUT`, `HTTPGET`, `SCPPUT` connectors available
 	
1. **Location**: The test plans are folders stored under `src/test/resources/` and must have the same arbitrary name as corresponding junit starter
2. **Test plan**: has one or more test steps (subfolders) with arbitrary names. The `0BEFORE` is an optional and will be automatically executed as first in order to prepare the environment for the test, i.e. clean the DB. JUnit starter submits the step to execution. 
3. **Test step**:  has multiple subfolders (connectors), with the strict naming convention `<ConnectorType>@<ID>`. The `ID` will be looked up in configuration. 
4. **connectors**: All connectors within the step folder processed automatically based on `<ConnectorType>`: PUT/GET i.e.: 
    - `MQGET@` - reads all messages in Queue with `<ID>`
    - `MQPUT@` - submits the payloads from connector folder into Queue with `<ID>` 


### <a name="junit"></a> Write the JUnit to make it fly

1. Use the same name for Junit starter as the underlying test plan
2. Each step has to be added and executed via `testBuilder.addStep("foldername").execute()` command. 
3. The custom assertions has to be configured separately and will be used during the `replay` cycle. 


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

