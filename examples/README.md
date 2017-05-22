## Examples
---
- mqsc.scripts - queue creation scripts
- splicustomerApp - broker application which splits the batch messages in single customers.
- splitcustomer.rockitizer - the junit rockitizer project 

## Configuration
---
1. **environment**: mqsc.scripts - create the queues using the mo72 or runmqsc.
2. **broker**:import splitcustomerApp in your toolkit, build it and deploy to your local broker
3. **junit test**:import splitcustomer.rockitizer as maven into java eclipse or toolkit for IIB 10 with maven plugin installed
   3.1  Configure mqmanager, (see mq manager configuration)[#confimqproperties] 
   3.2  Configure record/replay mode (default replay), (see test project configuration)[#testprojectconfiguration]    

## Run and Report
1. go to splitcustomer.rockitizer\src\test\java\com\rockit\customer\SplitCustomerTestOK.java
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
   ```
	########################################################################
	#  Environment Index (IDX Variable) 
	########################################################################
	MQENV = 
	########################################################################
	#  MQ Manager Connection  
	########################################################################
	MQ@MQMANAGER.NAME=IB9QMGR
	MQ@MQMANAGER.PORT=2414
	MQ@MQMANAGER.HOST=192.168.92.1
	MQ@MQMANAGER.CHANNEL=SYSTEM.BKR.CONFIG
	
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
    ###############################
    #   DB Connection
    ###############################
    #dataSource.username= ${dataSource.username}
    #dataSource.password= ${dataSource.password}
    #dataSource.url= ${dataSource.url}
    #DBGET@CUSTOMERS = SELECT NAME FROM ROCKIT.CUSTOMER ORDER BY NAME DESC
   ```
   
   

Main Readme
----
[Readme](../README.md)
