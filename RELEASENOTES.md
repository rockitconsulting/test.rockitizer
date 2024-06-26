# 1.0.6.0 - Release 
         - Vulnerable dependencies replaced with the latest version 
         - Upgrade log4j to log4j2
         - typesafe json,xml,txt Payload files for mq, http, files connectors with pretty-print formatting
         - MQ Connector support for Selections by correlationId, messageId
         - MQ Connector support for message groups
         - HTTP Connector Support for PATCH, PUT, DELETE Methods added
         - Additional assertion FileContainsString for mixed Payloads added

# 1.0.5.11 - Release 
	- bugfix in HTTP Connector: Wrong respone Content-Type detection.
	
# 1.0.5.10 - Release 
	- Remove unused Connectors/Datasources from resources.yaml
	- Explicitly commit SQL query if AutoCommit is "false"
	- Throw Exception when trying to use a non-existed environment from envs.yaml
	- Display manually added TestCases when executing 'cli sync' command
	
# 1.0.5.9 - Release 
	- Corrected doubled instantiation of HTTPConnector
	
# 1.0.5.8 - Release 
	- Feature: Skip Test
	- No validation error by empty "000BEFORE" and "999AFTER" folders 
	- New environment handling
	
# 1.0.5.5 - Release 
	- Update HTTPConnector.java 
	- HTTPConnector changes: IOException Handling for URL Connection/setResponse XML ---> JSON String (only for JSON Reponses)
	
# 1.0.5.4 - Release 
	- Pretty-Print for Test Report
	- Added support for description files (description.txt) for TestCase, TestStep and Connector
	- 999AFTER folder support for post processing added (analog to 000BEFORE preprocessing)
	- hint for setting the parameter 'mode' over cli or directly in JUnit to enable assertions
	- warning in TestProtocol by fallback to vanilla payload format in MQPut

## Migration needed from version prior to 1.0.5.3  
 - 0BEFORE replaced by 000BEFORE
 - https headers handling has been changed. Instead of dedicated properties contentType/userType direct under httpConnector, the headers structure with key,value pair has been introduced. 
 - In order to use CLI, the pom.xml of each project that utilizes test.rockITizer should be changed so that the configuration for the "appassembler-maven-plugin" will not generate any repository:
 	
 	(under the block <groupId>org.codehaus.mojo</groupId>
		   <artifactId>appassembler-maven-plugin</artifactId>
		   )
				
 		change "<repositoryLayout>flat<repositoryLayout>" to "<repositoryLayout>default</repositoryLayout>"
 		enter new property "<generateRepository>false</generateRepository>"
 	
 - Following the previous step, in order to use CLI, a new "REPO" variable should be defined in both set-env.bat and set-env.sh scripts. The "REPO" variable should contain the path to the local Maven directory 
 	e.g. 
 		set REPO=%userprofile%\.m2\repository (for set-env.bat)
 		set REPO=$HOME/.m2/repository (for set-env.sh)

# 1.0.5.3 - Release

Url: https://mvnrepository.com/artifact/io.github.rockitconsulting/test.rockitizer/1.0.5.3

Changes:

    - Handling MQ Headers (MQMD, RFH2 USR, etc.) over MqPayload wrapper. 
	- MQGet stores payload in XML Format including header. 
	- MQPut supports MqPayload format too, but has backward compatibility for vanila payloads
    - createFatJar Task 
    - JUnit coverage
    - javadocs
    - Fix for handling of json arrays by HTTPConnector

# 1.0.4 Release
	- cli starter auto generation added
	- cli developed
	- json assertion 
	- fixlength assertion
	- GPL v3.0 License
	
# 1.0.0 Release
 	 - gradle with maven central release
	-  yaml configuration
	-  statefull configuration management
        - base for CLI
# 0.0.8 Release
	- migration to Gradle

# 0.0.7 Release
	- The HTTPGetConnector now also supports JSONArray
	- The HTTPConnector has been extended to GET, PUT, POST and DELETE.
	- The header is analyzed and set in response
	- All methods can be used with https
	
# 0.0.6 Release  
	- The keys @ between the connectors and the configuration_alias was passed through "." replaced
	- MQ configuration from this version in the CONFIG.PROPERTIES (CONFIG.MQ.PROPERTIES no longer supported)
	- Multiple MQ and DB connectors supported
	
# 0.0.5 Release   
	- config.properties keys @ replaced with . in order to prevent the conflicts with maven resources plugin
	- All Put connectors submiting the payloads may have the placeholders within. Please use the standard markup ${PLACEHOLDER} and the corresponing entry in the config.properties: PLACEHOLDER=Value.
	- integration tests separated from junits   

# 0.0.4 Release   
	- Bugixing
	- MQ9 Support username/password authorisation 
	- Files Connectors added: FilePutConnector, FileDelConnector, FileGetConnector
