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
