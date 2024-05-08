package io.github.rockitconsulting.test.rockitizer.configuration.model;

import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.FileConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.MQConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.SCPConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.ConnectorRef;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.exceptions.UnknownConnectorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*  Test.Rockitizer - API regression testing framework 
*   Copyright (C) 2020  rockit.consulting GmbH
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see http://www.gnu.org/licenses/.
*
*/

public class ResourcesHolder  {
	
	private Map<String, String> payloadReplacer = new HashMap<>();
	private List<DBConnectorCfg>   dbConnectors 	= new ArrayList<>();
	private List<HTTPConnectorCfg> httpConnectors 	= new ArrayList<>();
	private List<FileConnectorCfg> fileConnectors 	= new ArrayList<>();
	private List<MQConnectorCfg>   mqConnectors 	= new ArrayList<>();
	private List<SCPConnectorCfg>  scpConnectors	= new ArrayList<>();
	
	private List<MQDataSource> 	mqDataSources =  new ArrayList<>();
	private List<DBDataSource> 	dbDataSources =  new ArrayList<>();
	private List<KeyStore> 		keyStores 	  =  new ArrayList<>();

	public ResourcesHolder() {}

	
	public List<MQDataSource> getMqDataSources() {
		return mqDataSources;
	}

	public void setMqDataSources(List<MQDataSource> mqDataSources) {
		this.mqDataSources = mqDataSources;
	}

	public List<DBDataSource> getDbDataSources() {
		return dbDataSources;
	}

	public void setDbDataSources(List<DBDataSource> dbDataSources) {
		this.dbDataSources = dbDataSources;
	}

	public List<KeyStore> getKeyStores() {
		return keyStores;
	}

	public void setKeyStores(List<KeyStore> keyStores) {
		this.keyStores = keyStores;
	}


	public List<DBConnectorCfg> getDbConnectors() {
		return dbConnectors;
	}


	public void setDbConnectors(List<DBConnectorCfg> dbConnectors) {
		this.dbConnectors = dbConnectors;
	}


	public List<HTTPConnectorCfg> getHttpConnectors() {
		return httpConnectors;
	}


	public void setHttpConnectors(List<HTTPConnectorCfg> httpConnectors) {
		this.httpConnectors = httpConnectors;
	}


	public List<FileConnectorCfg> getFileConnectors() {
		return fileConnectors;
	}


	public void setFileConnectors(List<FileConnectorCfg> fileConnectors) {
		this.fileConnectors = fileConnectors;
	}


	public List<MQConnectorCfg> getMqConnectors() {
		return mqConnectors;
	}


	public void setMqConnectors(List<MQConnectorCfg> mqConnectors) {
		this.mqConnectors = mqConnectors;
	}


	public List<SCPConnectorCfg> getScpConnectors() {
		return scpConnectors;
	}


	public void setScpConnectors(List<SCPConnectorCfg> scpConnectors) {
		this.scpConnectors = scpConnectors;
	}


	/**
	 * Because of snakeyaml limitation only List (no Set) usage is suitable, therefore specific add method to control uniqueness. 
	 * In addtion custom searchById function, instead of dependency on equals and hashcode
	 * 
	 * @param httpConnector configuration for @see {@link HTTPConnectorCfg#HTTPConnectorCfg(java.io.File)}
	 */
	public void addHttpConnector(HTTPConnectorCfg httpConnector) {
		if (!getHttpConnectors().stream().anyMatch(c -> c.getId().equals(httpConnector.getId()))) {
			getHttpConnectors().add(httpConnector);
		}
	}

	/**
	 * Becasue of snakeyaml limitation only List (no Set) usage is suitable, therefore specific add method to control uniqueness. 
	 * In addition custom searchById function, instead of dependency on equals and hashcode
	 * 
	 * @param fileConnector configuration for @see {@link FileConnectorCfg#FileConnectorCfg(java.io.File)}
	 */
	public void addFileConnector(FileConnectorCfg fileConnector) {
		if (!getFileConnectors().stream().anyMatch(c -> c.getId().equals(fileConnector.getId()))) {
			getFileConnectors().add(fileConnector);
		}
		
	}

	/**
	 * Becasue of snakeyaml limitation only List (no Set) usage is suitable, therefore specific add method to control uniqueness. 
	 * In addition custom searchById function, instead of dependency on equals and hashcode
	 * 
	 * @param mqConnector configuration for @see {@link MQConnectorCfg#MQConnectorCfg(java.io.File)}
	 */
	public void addMqConnector(MQConnectorCfg mqConnector) {
		if (!getMqConnectors().stream().anyMatch(c -> c.getId().equals(mqConnector.getId()))) {
			getMqConnectors().add(mqConnector);
		}
		
	}

	/**
	 * Becasue of snakeyaml limitation only List (no Set) usage is suitable, therefore specific add method to control uniqueness. 
	 * In addition custom searchById function, instead of dependency on equals and hashcode
	 * 
	 * @param dbConnector configuration for @see {@link DBConnectorCfg#DBConnectorCfg(java.io.File)}
	 */
	public void addDbConnector(DBConnectorCfg dbConnector) {
		if (!getDbConnectors().stream().anyMatch(c -> c.getId().equals(dbConnector.getId()))) {
			getDbConnectors().add(dbConnector);
		}
		
	}

	/**
	 * Becasue of snakeyaml limitation only List (no Set) usage is suitable, therefore specific add method to control uniqueness. 
	 * In addition custom searchById function, instead of dependency on equals and hashcode
	 * 
	 * @param scpConnector configuration for @see {@link SCPConnectorCfg#SCPConnectorCfg(java.io.File)}
	 */
	public void addScpConnector(SCPConnectorCfg scpConnector) {
		if (!getScpConnectors().stream().anyMatch(c -> c.getId().equals(scpConnector.getId()))) {
			getScpConnectors().add(scpConnector);
		}
		
	}

	/**
	 * DBDataSource Finder method
	 * @param id
	 * @return
	 */
	public DBDataSource findDBDataSourceById(String id) {
		return getDbDataSources().stream().filter( c -> c.getId().equals(id)).findAny().orElse(null);			
	}
	
	/**
	 * MQDataSource Finder method
	 * @param id
	 * @return
	 */
	public MQDataSource findMQDataSourceById(String id) {
		return getMqDataSources().stream().filter( c -> c.getId().equals(id)).findAny().orElse(null);			
	}

	
	/**
	 * KeyStore Finder method
	 * @param id
	 * @return
	 */
	public KeyStore findKeyStoreById(String id) {
		return getKeyStores().stream().filter( c -> c.getId().equals(id)).findAny().orElse(null);			
	}

	/**
	 * Find resource by reference (connector folder name)
	 * @param conRef
	 * @return
	 */
	public Object findResourceByRef(ConnectorRef conRef) {
		String cType = ConfigUtils.connectorTypeFromConnectorId(conRef.getConRefId());
		Object result  = null;
		
		switch (cType) {
		case "HTTP":
			result = getHttpConnectors().stream().filter( c -> c.getId().equals(conRef.getConRefId())).findAny().orElse(null);			
			break;

		case "FILEDEL":
			result = getFileConnectors().stream().filter( c -> c.getId().equals(conRef.getConRefId())).findAny().orElse(null);
			break;

		case "FILEPUT":
			result = getFileConnectors().stream().filter( c -> c.getId().equals(conRef.getConRefId())).findAny().orElse(null);
			break;

		case "FILEGET":
			result = getFileConnectors().stream().filter( c -> c.getId().equals(conRef.getConRefId())).findAny().orElse(null);
			break;

		case "MQGET":
			result = getMqConnectors().stream().filter( c -> c.getId().equals(conRef.getConRefId())).findAny().orElse(null);
			break;

		case "MQPUT":
			result = getMqConnectors().stream().filter( c -> c.getId().equals(conRef.getConRefId())).findAny().orElse(null);
			break;

		case "DBPUT":
			result = getDbConnectors().stream().filter( c -> c.getId().equals(conRef.getConRefId())).findAny().orElse(null);
			break;

		case "DBGET":
			result = getDbConnectors().stream().filter( c -> c.getId().equals(conRef.getConRefId())).findAny().orElse(null);
			break;

		case "SCPPUT":
			result = getScpConnectors().stream().filter( c -> c.getId().equals(conRef.getConRefId())).findAny().orElse(null);
			break;
		
		case "SCPGET":
			result = getScpConnectors().stream().filter( c -> c.getId().equals(conRef.getConRefId())).findAny().orElse(null);
			break;

			
		default:
			throw new UnknownConnectorException(conRef.getConRefId());

		}
		return result;
	}


	public Map<String, String> getPayloadReplacer() {
		return payloadReplacer;
	}


	public void setPayloadReplacer(Map<String, String> payloadReplacer) {
		this.payloadReplacer = payloadReplacer;
	}
	

	
	
	
	
	


	@Override
	public String toString() {
		return this.getClass().getSimpleName() + System.lineSeparator() +" [ "
				+ ( mqDataSources.isEmpty()?"":", mqDataSources="+ mqDataSources + System.lineSeparator()) 
				+ ( dbDataSources.isEmpty()?"":", dbDataSources="+ dbDataSources + System.lineSeparator() )
				+ ( keyStores.isEmpty()?"":", keyStores="+ keyStores +  System.lineSeparator())
				+ ( scpConnectors.isEmpty()?"":", scpConnectors="+ scpConnectors + System.lineSeparator() )
				+ ( payloadReplacer.isEmpty()?"":", payloadReplacer="+ payloadReplacer + System.lineSeparator() )
				+ "]";
	}

	
	
	
	
}
