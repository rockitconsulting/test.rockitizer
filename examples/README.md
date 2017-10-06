[Back to Readme](../README.md)

## Examples contents
-  `mqsc.scripts` - queue creation scripts
-  `splicustomerApp` - broker application which splits the batch messages in single customers.
-  `splitcustomer.rockitizer` - the junit rockitizer project 

## Configuration
### runtime environment
`examples/mqsc.scripts` - prepare mq infrastructure: create the queues using the mo72 or runmqsc.
### install you broker application 
import `examples/splicustomerApp` in your toolkit, create the bar file it and deploy to your local broker using the drag & drop deployment provided by the toolkit.
### junit test
import `examples/splitcustomer.rockitizer` as maven into java eclipse or toolkit for IIB 10 with maven plugin installed
   1. Configure mqmanager connection settings, (see mq manager configuration)[#confimqproperties] 
   2. Configure record/replay mode (default replay), (see test project configuration)[#testprojectconfiguration]    

## Run and Report
1. locate within your jee ide the imported splitcustomer.rockitizer\src\test\java\com\rockit\customer\SplitCustomerTestOK.java
2. run it as the junit
3. check the generated console report or log in ./target folder

```
 INFO #############################################################################
 INFO # 		 <SplitCustomerTestOK>: Configuration
 INFO #############################################################################
 INFO TESTNAME: SplitCustomerTestOK
 INFO RECORD FOLDER: C:/rockit/github/test.rockitizer/examples/splitcustomer.rockitizer/src/test/resources/SplitCustomerTestOK/
 INFO REPLAY FOLDER: C:/rockit/github/test.rockitizer/examples/splitcustomer.rockitizer/target/replay/SplitCustomerTestOK/
 INFO MODE: replay
 INFO #############################################################################
 INFO # 		 <SplitCustomerTestOK>: Executing  [replay]
 INFO #############################################################################
 INFO 0BEFORE	 Copying /src/test/resources/0BEFORE to /target/replay/0BEFORE
 INFO 
 INFO *****************************************************************************
 INFO 0BEFORE	 Step Added. Executing... 
 INFO Connecting MQQueueManager IB9QMGR
 INFO 0BEFORE	 Deleting replay  folder C:/rockit/github/test.rockitizer/examples/splitcustomer.rockitizer/src/test/resources/SplitCustomerTestOK/output
 INFO a001putCustomerBatch	 Copying /src/test/resources/a001putCustomerBatch to /target/replay/a001putCustomerBatch
 INFO 
 INFO *****************************************************************************
 INFO a001putCustomerBatch	 Step Added. Executing... 
 INFO a001putCustomerBatch	 [Connector:MQPUT@SPLITCUSTOMER.BATCH.IN] - Writing ...
 INFO Message successfully written to SPLITCUSTOMER.BATCH.IN                          
 INFO a002getCustomerBatch	 Copying /src/test/resources/a002getCustomerBatch to /target/replay/a002getCustomerBatch
 INFO 
 INFO *****************************************************************************
 INFO a002getCustomerBatch	 Step Added. Executing... 
 INFO  Waiting 1000ms for results
 INFO MQConnectorOut [SPLITCUSTOMER.CUSTOMER.OUT] get  message size 186
 INFO a002getCustomerBatch	 [Connector:MQGET@SPLITCUSTOMER.CUSTOMER.OUT] - Reading ...
 INFO MQConnectorOut [SPLITCUSTOMER.CUSTOMER.OUT] get  message size 213
 INFO a002getCustomerBatch	 [Connector:MQGET@SPLITCUSTOMER.CUSTOMER.OUT] - Reading ...
 INFO MQConnectorOut [SPLITCUSTOMER.CUSTOMER.OUT] get  message size 188
 INFO a002getCustomerBatch	 [Connector:MQGET@SPLITCUSTOMER.CUSTOMER.OUT] - Reading ...
 INFO MQConnectorOut [SPLITCUSTOMER.CUSTOMER.OUT] get  message size 247
 INFO a002getCustomerBatch	 [Connector:MQGET@SPLITCUSTOMER.CUSTOMER.OUT] - Reading ...
 INFO #############################################################################
 INFO # 		 <SplitCustomerTestOK>: Assertion
 INFO #############################################################################
 INFO  Number of assertions processed successfully: 3 [
	com.rockit.common.blackboxtester.assertions.XMLFileAssertion@ae9b55,
	com.rockit.common.blackboxtester.assertions.FileAssertion@1700915,
	com.rockit.common.blackboxtester.assertions.FileAssertion@1de60b4
      ]
```
 


### <a name="confimqproperties"></a> config.mq.properties - MQ Configuration
Configure the mq connection settings

   ```
	########################################################################
	#  Environment Index (IDX Variable) 
	########################################################################
	MQENV = 
	########################################################################
	#  MQ Manager Connection  
	########################################################################
	MQ@MQMANAGER.NAME=IB9QMGR
	MQ@MQMANAGER.PORT=1414
	MQ@MQMANAGER.HOST=localhost
	MQ@MQMANAGER.CHANNEL=SYSTEM.BKR.CONFIG
	MQ@MQMANAGER.USR=
	MQ@MQMANAGER.PWD=
	
	########################################################################
	#  MQ OUT Queue List generated from IIB Project  
	########################################################################
	MQGET@ = SPLITCUSTOMER.CUSTOMER.OUT, SPLITCUSTOMER.ERROR.OUT
   ```


### <a name="testprojectconfiguration"></a> config.properties - project configuration
*For the test there is no db connection required*
   ```
    ###############################
    #   Test Mode
    ###############################
    suite.mode = replay
   ```
   
   
---
[Back to Readme](../README.md)
