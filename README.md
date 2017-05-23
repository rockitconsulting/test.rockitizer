# test.rockitizer - keep your integration tested!  
[![N|Solid](http://www.rockit.consulting/images/logo-fixed.png)](http://www.rockit.consulting)

Easy-to-use junit based framework for the integration testing of any complexity. It is based on record/reply/assert logic and basically compares the current system/interface snapshot with the recorded master results using the numerous predefined or user-defined assertions. Therefore it keeps you informed on any suspicious change of system behavior.

![Lifecycle test.rockitizer](http://www.rockit.consulting/images/github/test_rockitizer_lifecycle.PNG "Lifecycle test.rockitizer;IBM Integration Bus; Integration testing; Test framework;test.rockitizer")

Furthermore, the framework enables the "test first" approach, thus developing against pre-defined "target" interface until the replay snapshot matches.

*Originally developed for ESB, especially for IBM Message Broker/IBM Integration Bus (used in examples), but can be used for any integration platform.* 

## Core features: 
  - declarative `test-plan` concept,[see details](#declarativetestplan)
  - separation of `test-plan` and `connector configuration` concept, [see details](#testdataseparation)
  - extendable connector/plugin architecture
  - `Record`/`Replay with post Assertion` Modes
  - `MQ`, `DB`, `HTTP`, `SCP` connectors available
  - `DB`, `File`, `XMLUnit` assertion plugins
  - regression testing and continuous integration
  
## Skill level
  Basic understanding of following Java frameworks: 
  - Maven - used for build and configuration of runtime and used in example for test project. 
  - Junit - used as test starter
  - XMLUnit - used for assertions
  
  *To run the sample ESB project you will need the basic IBM Integration Bus know-how for import and deployment.


## Main Concepts
According to the maven conventions the following folders are important:
- `src/test/resources/` - location of the test plans
- `src/test/java/` - location of the junit test runner

Junit serves as glue and looks for the test plan with the same name starting the execution from the root folder of the corresponding test plan.

### <a name="declarativetestplan"></a> Concept of declarative test plan

1. each step of the test is the folder which is executed via `testBuilder.addStep("foldername").execute()` command. *During execution all connectors within the folder are processed automatically according to the connector type `PUT/GET`*. The `PUT` connectors write the payload files stored as folder content.  
2. The results, if any `GET` connectors executed, are kept in the `output` within the step and corresponding connector folder.  
3. The `record` mode puts the output data inside `src/test/resources/<testname>/output/<teststep>/<connector>`, the `replay` stores the temporary data within the `target/replay/<testname>/output/<teststep>/<connector>`
4. Within the last step of `replay` mode the configured assertions `testBuilder.addAssertion(<AssertionImpl>(<fodername>)` being processed.


For the complete understanding including junit starter, please refer to  [basic execution flow](doc/USAGE.md#basicexecutionflow)



### <a name="testdataseparation"></a> Test data separation from environment configuration

<img alt="Concept of test data separation from environment configuration" src="docs/img/test_data_separation.png" width="450"  width="250" align="right"/> 

The following parts of the test.rpoject  supposed to be not environment dependent:
- junit starter with assertions
- test-plan with payloads 
- recorded test output (master record)
- replay output 

That's why they shall be committed in the source repository (except replay output).  

All the environment dependent properties belong to the test.project configuration files and basically contain the connector configurations.


## Getting started
<img alt="test rockitizer architecture" src="docs/img/architecture_with_dependency_new.png" width="200" height="200" align="right"/>

1. Build the test.rockitizer framework with dependencies, [see framework build instructions](docs/BUILD.md)
2. Create and configure your maven test project having the test.rockitizer as dependency, [see test project configuration](docs/USAGE.md#configuration)
3. Write your test scenario using the step/connector/payload convention [see testplan manual](docs/USAGE.md#testplan)
4. Whrite the JUnit and create the record "master" output [see junit manual](docs/USAGE.md#junit)
5. Reconfigure the test project to replay mode and execute replay with following asssertion



## Examples
[Examples Readme](examples/README.md)



License
----

MIT

