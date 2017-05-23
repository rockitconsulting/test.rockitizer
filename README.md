# test.rockitizer - keep your integration tested!  
[![N|Solid](http://www.rockit.consulting/images/logo-fixed.png)](http://www.rockit.consulting)

Easy-to-use java framework for the integration testing of any complexity. It is based on record/reply/assert logic and basically compares the current system/interface snapshot with the recorded master results using the numerous predefined or user-defined assertions. Therefore it keeps you informed on any suspicious change of system behavior.

![Lifecycle test.rockitizer](http://www.rockit.consulting/images/github/test_rockitizer_lifecycle.PNG "Lifecycle test.rockitizer;IBM Integration Bus; Integration testing; Test framework;test.rockitizer")

Furthermore, the framework enables the "test first" approach, thus developing against pre-defined "target" interface until the replay snapshot matches.

*Originally developed for ESB, especially for IBM Message Broker/IBM Integration Bus, but can be used for any integration platform.* 

## Core features: 
  - Java, JUnit based framework
  - extendable connector/plugin architecture
  - Record/Replay with post Assertion
  - Separation of Testdata from Connectors
  - MQ, DB, HTTP, SCP connectors available
  - DB, File, XMLUnit assertion plugins
  - regression testing and continuous integration
  - Complex testcases are built up within the minutes


## Getting started?
<img alt="test rockitizer architecture" src="docs/img/architecture_with_dependency_new.png" width="200" height="200" align="right"/>
1. Build the test.rockitizer framework with dependencies, [see framework build instructions](docs/BUILD.md)
2. Create and configure your maven test project having the test.rockitizer as dependency, [see test project configuration](docs/USAGE.md#configuration)
3. Write your test scenario using the step/connector/payload convention [see testplan manual](docs/USAGE.md#testplan)
4. Whrite the JUnit and create the record "master" output [see junit manual](docs/USAGE.md#junit)
5. Reconfigure the test project to replay mode and execute replay with following asssertion


## Concepts
### Concept of test separation from environment configuration

![Test Data separation](http://www.rockit.consulting/images/github/test_rockitizer_process.PNG "Test Data separation;IBM Integration Bus; Integration testing; Test framework;test.rockitizer")

### Concept of daclarative test plan


## Examples
[Examples Readme](examples/README.md)



License
----

MIT

