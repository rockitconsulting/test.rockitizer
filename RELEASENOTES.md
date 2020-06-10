# 1.0.5.3 - Snapshot

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
