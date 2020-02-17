package io.github.rockitconsulting.test.rockitizer.configuration.model;

import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.tc.TestCase;

import java.util.ArrayList;
import java.util.List;

public class TestSuite  {
	
	public enum Mode {
		record, replay
	}

	/*
	suiteMode: record
	dataSources:
	  - type: db
	    username: admin
	    password: admin
	    url: jdbc:db2://localhost:50000/ROCKIT
	    id: defDB
	    
	  - type: mq
	    name: QM1
	    port: 1414
	    host: localhost
	    channel: SYSTEM.BKR.CONFIG
	    username: admin  
	    password: admin 
	    id: defMQ
*/
	
	
	Mode suiteMode;
	
	List<MQDataSource> mqDataSources =  new ArrayList<>();
	List<DBDataSource> dbDataSources =  new ArrayList<>();
	List<KeyStore> keyStores =  new ArrayList<>();
	List<TestCase> testCases =  new ArrayList<>();

	public TestSuite() {}

	
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

	
	public List<TestCase> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	
	
	public Mode getSuiteMode() {
		return suiteMode;
	}

	public void setSuiteMode(Mode suiteMode) {
		this.suiteMode = suiteMode;
	}


	public String validate () {
		//TODO implement
		return null;
		
	}
	


	@Override
	public String toString() {
		return "TestSuite " + System.lineSeparator() +" [ suiteMode=" + suiteMode + System.lineSeparator() 
				+ ( mqDataSources.isEmpty()?"":", mqDataSources="+ mqDataSources + System.lineSeparator()) 
				+ ( dbDataSources.isEmpty()?"":", dbDataSources="+ dbDataSources + System.lineSeparator() )
				+ ( keyStores.isEmpty()?"":", keyStores="+ keyStores +  System.lineSeparator())
				+ ( testCases.isEmpty()?"":", testCases="+ testCases + System.lineSeparator() )
				+ "]";
	}
	
	
	
	
	
}
