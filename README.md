[![N|Solid](http://www.rockit.consulting/images/logo-fixed.png)](http://www.rockit.consulting)

# test.rockitizer - Keep Your Integration Tested!

Easy-to-use junit based framework for the testing of complex integration scenarios. Using the declarative test plans and automated comparison between current test-snapshots and recorded master-results, it keeps you informed on any suspicious change of system behavior. In addition, the framework **boosts** the integration development process, providing all-in-one test solution, making in most of cases the usage of external protocol/vendor specific tools obsolete.  

##  Big Idea Behind 

The modern IT landscape is increasing in integration complexity due to various communication protocols and data formats.  

**test.rockITizer** addresses this problem, introducing an API Testing framework with built in record/replay/assert functionality. 

The main idea for test.rockITizer has been taken from the industry, where the master samples get compared with the produced samples, thus assuring the desired quality

This approach assumes the following major phases: 

1. Recording: definition of the inputs and sample master results  
2. Definition of quality profiles/assertions
3. Regression/Replay Mode: automated comparison between current test-snapshots and recorded master-results.

This way the integration landscape of any complexity is treated as a blackbox, keeping you informed of any suspicious change in the system behavior.


![Lifecycle test.rockitizer](http://www.rockit.consulting/images/github/test_rockitizer_lifecycle.PNG "Lifecycle test.rockitizer;IBM Integration Bus; Integration testing; Test framework;test.rockitizer")

Furthermore, the framework enables the "test first" approach, thus developing against pre-defined "target" interface until the replay snapshot matches.

Test.RockITizer covers with its connectors the majority of protocols in integration area, making obsolete the usage of external tools like soap-ui, db explorer, mq explorer, etc., thus improving productivity of overall development process.

*Originally developed for ESB, especially for IBM Message Broker/IBM Integration Bus (used in examples), but can be used for any integration platform.* 

##  Core Features:

Regression testing and continuous integration enabled, including surefire reports

* Powerful CLI interface
* Binding over single jar dependency from maven central
* `Record`/`Replay` with post `Assertion` Modes
* Numerous connectors available: `HTTP, MQGET/MQPUT, DBGET/DBPUT, FILEGET/FILEPUT/FILEDEL, SCPPUT/SCPGET`
* Various assertions which enable deep comparison for different types of payloads: `XML, CSV`, etc. and Resources, e.g. DB
* Environment-dependent configuration for connectors and payloads (once written runs everywhere: `Local`/`Jenkins`/`INT`)
* Declarative test-plan concept
* Extendable connector/plugin architecture
* Support for multiple DB-DataSources, KeyStores, Queue-Managers



## Unique Advantages
* Improved productivity for developers. No additional tools like SOAP-UI, DB-Viewers, MQ-Tools needed.
* Test-Driven Development for Integration landscapes, via developing against desired targets.



##  CLI
![CLI](https://github.com/rockitconsulting/test.rockitizer/blob/master/docs/img/CLI.png?raw=true)


##  Running Tests

###  Console

   
```
__________               __   .__  __  .__                      
\______   \ ____   ____ |  | _|__|/  |_|__|_______ ___________  
 |       _//  _ \_/ ___\|  |/ /  \   __\  \___   // __ \_  __ \ 
 |    |   (  <_> )  \___|    <|  ||  | |  |/    /\  ___/|  | \/ 
 |____|_  /\____/ \___  >__|_ \__||__| |__/_____ \\___  >__|    
        \/            \/     \/                 \/    \/        

 INFO #############################################################################
 INFO # 		 <JSONwithDatabaseTest>: Configuration
 INFO #############################################################################
 INFO TESTNAME: JSONwithDatabaseTest
 INFO RECORD FOLDER: C:/rockit/projects/github/test.rockitizer.demo/demo.rockitizer/src/test/resources/JSONwithDatabaseTest/
 INFO REPLAY FOLDER: C:/rockit/projects/github/test.rockitizer.demo/demo.rockitizer/target/replay/JSONwithDatabaseTest/
 INFO MODE: REPLAY
 INFO #############################################################################
 INFO # 		 <JSONwithDatabaseTest>: Executing  [REPLAY]
 INFO #############################################################################
 INFO 
 INFO *****************************************************************************
 INFO 0BEFORE	 Step Added. Executing... 
 INFO 0BEFORE	 [Connector:DBPUT.CLAEN] - Writing ...
 INFO 0BEFORE	 [Connector:MQGET.ERROR] - Reading ...
 INFO 0BEFORE	 Deleting replay  folder C:/rockit/projects/github/test.rockitizer.demo/demo.rockitizer/target/replay/JSONwithDatabaseTest/
 INFO 
 INFO *****************************************************************************
 INFO a001JSONPutMessage	 Step Added. Executing... 
 INFO a001JSONPutMessage	 [Connector:HTTP.JADDBOOK] - Writing ...
 INFO a001JSONPutMessage	 [Connector:HTTP.JADDBOOK] - Reading ...
 INFO 
 INFO *****************************************************************************
 INFO a002DBGETMessage	 Step Added. Executing... 
 INFO a002DBGETMessage	 [Connector:DBGET.GETBOOKS] - Reading ...
 INFO #############################################################################
 INFO # 		 <JSONwithDatabaseTest>: Assertion
 INFO #############################################################################
 INFO  Number of assertions processed successfully: 4 [
	XMLFileAssertion( path:"\a001JSONPutMessage", ignoreFields:"CF-RAY,ETag,Access-Control-Allow-Origin,Connection,Set-Cookie,Date,Expect-CT,X-Powered-By,createdAt,id,ID" ),
	XMLFileAssertion( path:"\a002DBGETMessage" ),
	FileAssertion("\"),
	FileAssertion("\")
      ]

Time: 1,36

OK (1 test)

Result: OK
Total runs 1


test.rockizer Copyright (C) 2020 rockit.consutling GmbH 
This program is distributed under GPL v3.0 License and comes with ABSOLUTELY NO WARRANTY 
This is free software, and you are welcome to redistribute it under GPL v3.0 conditions 
```

##  IDE
![IDE](https://github.com/rockitconsulting/test.rockitizer/blob/master/docs/img/ide.JPG?raw=true)

## Jenkins
![Jenkins](https://github.com/rockitconsulting/test.rockitizer/blob/master/docs/img/jenkins.JPG?raw=true)


## Getting started
<span>
<img align="right" src="https://github.com/rockitconsulting/test.rockitizer/blob/master/docs/img/architecture_with_dependency_small.jpg?raw=true">
<br>
For a complete understanding please refer to Documentation: 
<br>
<ul>
<li><a href="https://rockit.atlassian.net/wiki/spaces/TR/pages/926842891/Introduction" target="_blank">Introduction</a></li>
<li><a href="https://rockit.atlassian.net/wiki/spaces/TR/pages/941228053/Quick+Start" target="_blank">Quick Start Guide</a></li>
<li><a href="https://rockit.atlassian.net/wiki/spaces/TR/pages/947716159/Project+layout" target="_blank">Project Layout/Testplan</a></li>
<li><a href="https://rockit.atlassian.net/wiki/spaces/TR/pages/947191889/Configuration" target="_blank">Confiuration</a></li>
<li><a href="https://rockit.atlassian.net/wiki/spaces/TR/pages/947224777/JUnit+and+Assertions" target="_blank">Execution Plan</a></li>
<li><a href="https://rockit.atlassian.net/wiki/spaces/TR/pages/947322990/Command-Line" target="_blank">Command Line Interface</a></li>
<li><a href="https://rockit.atlassian.net/wiki/spaces/TR/pages/929824873/FAQ" target="_blank">FAQ</a></li>
</ul>
</span>
<br><br><br><br>







## Authors

`test.rockitizer` was created by [`Yefym Dmukh`](https://github.com/yefymdmukh).

`test.rockitizer` is sponsored by [`rockit.consulting GmbH`](http://www.rockit.consulting/)

## License
GNU General Public License v3.0 or later

See [LICENSE](LICENSE.md) to see the full text.

## Publications
[IBM Integration Community: junit based integration testing](https://developer.ibm.com/integration/blog/2017/08/29/junit-based-integration-testing-ibm-integration-bus/).
