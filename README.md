# test.rockitizer - keep your integration tested!
[![N|Solid](http://www.rockit.consulting/images/logo-fixed.png)](http://www.rockit.consulting)

<!--Easy-to-use junit based framework for the testing of complex integration scenarios. Using the declarative test plans and automated comparison between current test-snapshots and recorded master-results, it keeps you informed on any suspicious change of system behavior.-->


![Lifecycle test.rockitizer](http://www.rockit.consulting/images/github/test_rockitizer_lifecycle.PNG "Lifecycle test.rockitizer;IBM Integration Bus; Integration testing; Test framework;test.rockitizer")

Furthermore, the framework enables the "test first" approach, thus developing against pre-defined "target" interface until the replay snapshot matches.

Test.RockITizer covers with its connectors the majority of protocols in integration area, making obsolete the usage of external tools like soap-ui, db explorer, mq explorer, etc., thus improving productivity of overall development process.

*Originally developed for ESB, especially for IBM Message Broker/IBM Integration Bus (used in examples), but can be used for any integration platform.* 

## <a name="corefeatures"></a> Core features: 
  - environment dependent configuration for connectors and payloads (once written runs everywhere: Local/DEV/INT)
  - declarative `test-plan` concept, [see details](#declarativetestplan)
  - separation of `test-plan` and `connector configuration` concept, [see details](#testdataseparation)
  - extendable connector/plugin architecture
  - `Record`/`Replay with post Assertion` Modes
  - `MQGET`/`MQPUT`, `DBGET`/`DBPUT`, `FILEGET`/`FILEPUT`/`FILEDEL`, `SCPPUT`/`SCPGET` connectors available
  - `HTTP` - `GET` / `PUT` / `POST` / `DELETE` connectors available and enabled for HTTPS, SOAP, REST, including support for PKI, BasicAuth, WS-Security, etc.
  - `DB`, `File`, `CSV`, `XMLUnit` assertion plugins available, enabling full control by comparing the different types of master-/test-payloads
  - regression testing and continuous integration enabled, including surefire reports
  
## Skill level
  Basic understanding of following Java frameworks: 
  - Maven - used for test project build/configuration/packaging purposes 
  - Junit - used as test starter
  - XMLUnit - used for assertions
  
  *To run the sample ESB project you will need the basic IBM Integration Bus know-how for import and deployment.


## <a name="mainconcepts"></a> Main Concepts
According to the maven conventions the following folders of  your test.project are important:
- `src/test/resources/` - location of the test plans
- `src/test/java/` - location of the junit test runner

Junit serves as glue and looks for the test plan with the same name starting its execution from the root folder.
Dependent on mode record/replay it keeps the test outputs in xml format and starts the preconfigured assertions between the recorded and replayed payloads, writing the test protocol [to the console](#reportsample). 

For the complete understanding including junit starter and project layout, please refer to  [basic execution flow](docs/USAGE.md#basicexecutionflow)

### <a name="declarativetestplan"></a> Concept of declarative test plan

<img alt="Concept of test data separation from environment configuration" src="docs/img/test_plan_sample.png" width="400"  width="250" align="right"/> 
 	
1. The test plans are folders stored under `src/test/resources/` and must have the same arbitrary name as corresponding junit starter, i.e. `SplitCustomerTest`.
2. Each **test plan** has one or more test steps (subfolders) with arbitrary names, i.e. `a001putCustomerBatch`,`a002getCustomerBatch`.  Junit starter adds step to execution.  
3. Each **test step** has multiple subfolders (connectors), with the strict naming convention `<ConnectorType>.<ID>`. The `ID` will be looked up in configuration, i.e. `DBGET.CUSTOMERS`,`MQPUT.CUSTOMERBATCH` 
4. All **connectors** processed automatically based on `<ConnectorType>`: PUT/GET i.e.: 
    - MQGET. - reads all messages in Queue with `<ID>`
    - MQPUT. - submits the payloads from connector folder into Queue with `<ID>` 

For the test plan reference check [docu](docs/USAGE.md#testplan)

**The declarative test-plan approach reduces the maintanence costs of the test-suite**.  


### <a name="testdataseparation"></a> Test data separation from environment configuration

<img alt="Concept of test data separation from environment configuration" src="docs/img/test_data_separation.png" width="450"  width="250" align="right"/> 

The following parts of the test.project supposed to be  environment independent:
- junit starter with assertions
- test-plan with payloads 
- recorded test output (master record)


That's why they shall be committed in the source repository and will be shared across users and environments. **Write test onces and start it anywhere**.  

* replay output is also environment neutral but generated during the test execution, thus not checked in.

All the environment dependent properties belong to the test.project configuration files and basically contain the connector configurations, [see configuration](docs/USAGE.md#configuration).


## Getting started
<img alt="test rockitizer architecture" src="docs/img/architecture_with_dependency_new.png" width="200" height="200" align="right"/>

1. Build the test.rockitizer framework with dependencies, [see framework build instructions](docs/BUILD.md)
2. Create and configure your maven test project having the test.rockitizer as dependency, [see test project configuration](docs/USAGE.md#configuration)
3. Write your test scenario using the step/connector/payload convention [see testplan manual](docs/USAGE.md#testplan)
4. Whrite the JUnit and create the record "master" output [see junit manual](docs/USAGE.md#junit)
5. Reconfigure the test project to replay mode and execute replay with following asssertion



## Examples
[Examples Readme](examples/README.md)



##  <a name="reportsample"></a> Report sample

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
 INFO a001putCustomerBatch	 [Connector:MQPUT.SPLITCUSTOMER.BATCH.IN] - Writing ...
 INFO Message successfully written to SPLITCUSTOMER.BATCH.IN                          
 INFO a002getCustomerBatch	 Copying /src/test/resources/a002getCustomerBatch to /target/replay/a002getCustomerBatch
 INFO 
 INFO *****************************************************************************
 INFO a002getCustomerBatch	 Step Added. Executing... 
 INFO  Waiting 1000ms for results
 INFO MQConnectorOut [SPLITCUSTOMER.CUSTOMER.OUT] get  message size 186
 INFO a002getCustomerBatch	 [Connector:MQGET.SPLITCUSTOMER.CUSTOMER.OUT] - Reading ...
 INFO MQConnectorOut [SPLITCUSTOMER.CUSTOMER.OUT] get  message size 213
 INFO a002getCustomerBatch	 [Connector:MQGET.SPLITCUSTOMER.CUSTOMER.OUT] - Reading ...
 INFO MQConnectorOut [SPLITCUSTOMER.CUSTOMER.OUT] get  message size 188
 INFO a002getCustomerBatch	 [Connector:MQGET.SPLITCUSTOMER.CUSTOMER.OUT] - Reading ...
 INFO MQConnectorOut [SPLITCUSTOMER.CUSTOMER.OUT] get  message size 247
 INFO a002getCustomerBatch	 [Connector:MQGET.SPLITCUSTOMER.CUSTOMER.OUT] - Reading ...
 INFO #############################################################################
 INFO # 		 <SplitCustomerTestOK>: Assertion
 INFO #############################################################################
 INFO  Number of assertions processed successfully: 3 [
	com.rockit.common.blackboxtester.assertions.XMLFileAssertion@ae9b55,
	com.rockit.common.blackboxtester.assertions.FileAssertion@1700915,
	com.rockit.common.blackboxtester.assertions.FileAssertion@1de60b4
      ]
```

Known issues
----
	Issue: 
		Base64.class from java 8 package
	Solution:
		Java 1.8 recommended. Use the code compatibility option to Java 1.7 for all IIB versions prior 10.0.0.12

Authors
----

`test.rockitizer` was created by [`Yefym Dmukh`](https://github.com/yefymdmukh).


`test.rockitizer` is sponsored by [`rockit.consulting GmbH`](https://www.rockit.consulting)

License
----

GNU General Public License v3.0 or later

See [LICENSE](LICENSE.md) to see the full text.


Publications
----
1. [IBM Integration Community: junit based integration testing](https://developer.ibm.com/integration/blog/2017/08/29/junit-based-integration-testing-ibm-integration-bus/).
