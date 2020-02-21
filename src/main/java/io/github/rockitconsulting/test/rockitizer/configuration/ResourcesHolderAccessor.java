package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.FileConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.MQConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.SCPConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.ConfigUtils;
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.suite.configuration.Constants;



public class ResourcesHolderAccessor extends RuntimeContext {

	public static Logger log = Logger.getLogger(ResourcesHolderAccessor.class.getName());

	private String resourcesFileName = "resources.yaml";

	/**
	 * Reads yam configuration into the holder object
	 * CLI relevant: instantiate Resources from yaml
	 * @return
	 * @throws IOException
	 */
	public ResourcesHolder resourcesHolderFromYaml() throws IOException {
		return ConfigUtils.resourcesHolderFromYaml(getFullPath() + getResourcesFileName());
	}

	
	/**
	 * CLI relevant: generate Resources from yaml
	 * @return
	 * @throws IOException
	 */

	public ResourcesHolder resourcesHolderFromFileSystem()  {
		return resourcesHolderFromFileSystem(null);
	}
	
	
	/**
	 * CLI relevant: generate Resources from yaml
	 * @return
	 * @throws IOException
	 */

	public ResourcesHolder resourcesHolderFromFileSystem(Map <String, String> payloadReplacer)  {

		ResourcesHolder resources = new ResourcesHolder();

		if(payloadReplacer!=null) { 
			resources.setPayloadReplacer(payloadReplacer);
		}
		
		resources.getDbDataSources().add(new DBDataSource());
		resources.getMqDataSources().add(new MQDataSource());
		resources.getKeyStores().add(new KeyStore());

		FileUtils.listFolders(new File(getFullPath())).forEach(tcFolder -> {
			FileUtils.listFolders(tcFolder).forEach(tsFolder -> {

				if (tsFolder.getName().equalsIgnoreCase(Constants.OUTPUT_FOLDER)) {
					return;
				}

				FileUtils.listFolders(tsFolder).forEach(connFolder -> {
					ConnectorFactory.processAddConnector(resources, connFolder);
				});
			});
		});

		return resources;
	}
	
	
	/**
	 * CLI relevant: generate Resources from yaml
	 * @return
	 * @throws IOException
	 */

	public ResourcesHolder resourcesHolderToYaml(Map <String, String> payloadReplacer) throws IOException {

		ResourcesHolder resources = resourcesHolderFromFileSystem(payloadReplacer);
		ConfigUtils.writeToYaml(resources, getFullPath() + getResourcesFileName());
		return resources;
	}

	
	
	
	/**
	 * Private class for instantiation and addition of respective connectors
	 *
	 */
	private static class ConnectorFactory {

		public static void processAddConnector(ResourcesHolder resources, File connFolder) {

			String cType = ConfigUtils.connectorTypeFromFolder(connFolder);

			switch (cType) {
			case "HTTP":
				HTTPConnector httpConnector = new HTTPConnector(connFolder);
				resources.addHttpConnector(httpConnector);
				break;

			case "FILEDEL":
				FileConnector fileDelConnector = new FileConnector(connFolder);
				fileDelConnector.setType(FileConnector.Types.FILEDEL);
				resources.addFileConnector(fileDelConnector);
				break;

			case "FILEPUT":
				FileConnector filePutConnector = new FileConnector(connFolder);
				filePutConnector.setType(FileConnector.Types.FILEPUT);
				resources.addFileConnector(filePutConnector);
				break;

			case "FILEGET":
				FileConnector fileGetConnector = new FileConnector(connFolder);
				fileGetConnector.setType(FileConnector.Types.FILEGET);
				resources.addFileConnector(fileGetConnector);
				break;

			case "MQGET":
				MQConnector mqGetConnector = new MQConnector(connFolder);
				mqGetConnector.setType(MQConnector.Types.MQGET);
				resources.addMqConnector(mqGetConnector);
				break;

			case "MQPUT":
				MQConnector mqPutConnector = new MQConnector(connFolder);
				mqPutConnector.setType(MQConnector.Types.MQPUT);
				resources.addMqConnector(mqPutConnector);
				break;

			case "DBPUT":
				DBConnector dbPutConnector = new DBConnector(connFolder);
				dbPutConnector.setType(DBConnector.Types.DBPUT);
				resources.addDbConnector(dbPutConnector);
				break;

			case "DBGET":
				DBConnector dbGetConnector = new DBConnector(connFolder);
				dbGetConnector.setType(DBConnector.Types.DBGET);
				resources.addDbConnector(dbGetConnector);
				break;

			case "SCPPUT":
				SCPConnector scpConnector = new SCPConnector(connFolder);
				scpConnector.setType(SCPConnector.Types.SCPPUT);
				resources.addScpConnector(scpConnector);
				break;

			default:
				throw new IllegalArgumentException("Invalid connector : " + cType + " under " + connFolder.getAbsolutePath());

			}

		}
	}

	public String getResourcesFileName() {
		return resourcesFileName;
	}

	public void setResourcesFileName(String resourcesFileName) {
		this.resourcesFileName = resourcesFileName;
	}



	public String contextAsString() {
		return "[ filename: " + getResourcesFileName() + ", absPath: " + getAbsolutePath() + ", relPath: " + getRelativePath() +"]";
		
	}

}
