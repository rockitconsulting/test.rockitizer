package com.rockit.common.blackboxtester.suite.structures;

import static com.rockit.common.blackboxtester.suite.configuration.ConnectorFactory.connectorByFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.connector.Connector;
import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.util.FileUtils;


public class ConnectorFolder extends AbstractTestFolder {
	public static final Logger LOGGER = Logger.getLogger(ConnectorFolder.class.getName());
	
	
	public ConnectorFolder(String testName , String testStepName, String connectorName) {
		super(testName, testStepName, connectorName);
	}
	
	
	public void execute()  {
		List<Connector> connectors = getConnectorByFolderName();
		if(connectors.isEmpty()) LOGGER.info(getTestStepName() + "\t Constains no Connector.");
		for(Connector connector : connectors ) {
			execute(connector);	
		}
		 
	}

	
	private void execute(Connector connector)  {
		if(connector instanceof WriteConnector){
			for (File input: FileUtils.listFiles( getInFolder() ) ) {
				
				((WriteConnector)connector).setRequest(input);
				LOGGER.info( getTestStepName() + "\t [Connector:"+ connector.getName() +"] - Writing ...");
				connector.proceed();
				 
			     if (connector instanceof ReadConnector){
						saveResponse(connector);
					}
			}
		} else if (connector instanceof ReadConnector){
			saveResponse(connector);
		} else if (connector instanceof Connector){
			LOGGER.info( getTestStepName() + "\t [Connector:"+ connector.getName() +"] - Proceed ...");
			connector.proceed();
		}
		
	}
	
		
	private void saveResponse(Connector connector) {
		int idx = 0;
		do { 
			
			connector.proceed();
			String response =  ((ReadConnector)connector).getResponse();
			
			if(null != response) {
//				LOGGER.info(getTestStepName()+"\t [connector:"+ connector.getName() +"] - Saving connector "+ connector.getName() +" content to output folder "  + getOutFolder() );
				try {
					LOGGER.info( getTestStepName() + "\t [Connector:"+ connector.getName() +"] - Reading ...");
					Files.write(
						response.getBytes("UTF-8"), new File( getOutFolder() + "/" +  idx + ".txt")  
					);
				} catch (IOException e) {
					LOGGER.error("can not write output file " + idx , e);
					throw new GenericException(e);
				}
				idx++;
			}
		} while(null!=((ReadConnector)connector).getResponse() && Constants.Connectors.MQGET.toString().equalsIgnoreCase(connector.getType()));
	}


	
	private  List<Connector> getConnectorByFolderName() {
			return connectorByFolder( getInFolder().getName() );
				
	}
	
}