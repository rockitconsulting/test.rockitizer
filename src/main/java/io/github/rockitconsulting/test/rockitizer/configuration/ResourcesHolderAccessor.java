package io.github.rockitconsulting.test.rockitizer.configuration;

import io.github.rockitconsulting.test.rockitizer.configuration.model.ResourcesHolder;
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
import io.github.rockitconsulting.test.rockitizer.configuration.utils.FileUtils;
import io.github.rockitconsulting.test.rockitizer.exceptions.InvalidConnectorFormatException;
import io.github.rockitconsulting.test.rockitizer.exceptions.ResourceNotFoundException;
import io.github.rockitconsulting.test.rockitizer.exceptions.ValidationException;
import io.github.rockitconsulting.test.rockitizer.validation.Validatable;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.suite.configuration.Constants;

/**
 * Test.Rockitizer - API regression testing framework Copyright (C) 2020
 * rockit.consulting GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 *
 */

public class ResourcesHolderAccessor extends RuntimeContext {

	public static final Logger log = Logger.getLogger(ResourcesHolderAccessor.class.getName());

	private ResourcesHolder resourcesHolder;

	private String resourcesFileName = "resources.yaml";

	void initFromYaml() throws IOException {
		resourcesHolder = resourcesHolderFromYaml();
	}

	void initFromFileSystem() throws IOException {
		resourcesHolder = resourcesHolderFromFileSystemToYaml(null);
	}

	/**
	 * Reads yam configuration into the holder object CLI relevant: instantiate
	 * Resources from yaml
	 * 
	 * @return
	 * @throws IOException
	 */
	public ResourcesHolder resourcesHolderFromYaml() throws IOException {
		return ConfigUtils.resourcesHolderFromYaml(getFullPath() + getResourcesFileName());
	}

	/**
	 * CLI relevant: generate Resources from yaml
	 * 
	 * @return
	 * 
	 */

	public ResourcesHolder resourcesHolderFromFileSystem() {
		return resourcesHolderFromFileSystem(null);
	}

	/**
	 * CLI relevant: generate Resources from yaml
	 * 
	 * @return
	 * 
	 */

	ResourcesHolder resourcesHolderFromFileSystem(Map<String, String> payloadReplacer) {

		ResourcesHolder resources = new ResourcesHolder();

		if (payloadReplacer != null) {
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

	public String getResourcesFileName() {
		return resourcesFileName;
	}

	public void setResourcesFileName(String resourcesFileName) {
		this.resourcesFileName = resourcesFileName;
	}

	/**
	 * CLI relevant: write resources to yaml
	 * 
	 * @throws IOException
	 */

	public void resourcesHolderToYaml(ResourcesHolder resources) throws IOException {
		ConfigUtils.writeModelObjToYaml(resources, getFullPath() + getResourcesFileName());

	}

	/**
	 * CLI relevant: generate Resources from yaml
	 * 
	 * @return
	 * @throws IOException
	 */

	public ResourcesHolder resourcesHolderFromFileSystemToYaml(Map<String, String> payloadReplacer) throws IOException {

		ResourcesHolder resources = resourcesHolderFromFileSystem(payloadReplacer);
		resourcesHolderToYaml(resources);
		return resources;
	}

	public Validatable getConnectorById(String id) {
		Validatable c = (Validatable) resourcesHolder.findResourceByRef(new ConnectorRef(id));
		if (c == null) {
			throw new ResourceNotFoundException(id);
		}
		if (!c.isValid()) {
			throw new ValidationException(c.validate());
		}
		return c;
	}

	public DBDataSource getDBDataSourceByConnector(DBConnectorCfg connector) {
		DBDataSource ds = resourcesHolder.findDBDataSourceById(connector.getDsRefId());
		if (ds == null) {
			throw new ResourceNotFoundException(connector.getDsRefId());
		}

		if (!ds.isValid()) {
			throw new ValidationException(ds.validate());
		}
		return ds;
	}

	public MQDataSource getMQDataSourceByConnector(MQConnectorCfg connector) {
		MQDataSource ds = resourcesHolder.findMQDataSourceById(connector.getDsRefId());
		if (ds == null) {
			throw new ResourceNotFoundException(connector.getDsRefId());
		}

		if (!ds.isValid()) {
			throw new ValidationException(ds.validate());
		}
		return ds;
	}

	public KeyStore getKeyStoreByConnector(HTTPConnectorCfg connector) {
		if (connector.getDsRefId() == null) {
			return null;
		}

		KeyStore ds = resourcesHolder.findKeyStoreById(connector.getDsRefId());
		if (ds == null) {
			throw new ResourceNotFoundException(connector.getDsRefId());
		}

		if (!ds.isValid()) {
			throw new ValidationException(ds.validate());
		}
		return ds;
	}

	public String contextAsString() {
		return "[ filename: " + getResourcesFileName() + ", absPath: " + getAbsolutePath() + ", relPath: " + getRelativePath() + "]";

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
				HTTPConnectorCfg httpConnector = new HTTPConnectorCfg(connFolder);
				resources.addHttpConnector(httpConnector);
				break;

			case "FILEDEL":
				FileConnectorCfg fileDelConnector = new FileConnectorCfg(connFolder);
				fileDelConnector.setType(FileConnectorCfg.Types.FILEDEL);
				resources.addFileConnector(fileDelConnector);
				break;

			case "FILEPUT":
				FileConnectorCfg filePutConnector = new FileConnectorCfg(connFolder);
				filePutConnector.setType(FileConnectorCfg.Types.FILEPUT);
				resources.addFileConnector(filePutConnector);
				break;

			case "FILEGET":
				FileConnectorCfg fileGetConnector = new FileConnectorCfg(connFolder);
				fileGetConnector.setType(FileConnectorCfg.Types.FILEGET);
				resources.addFileConnector(fileGetConnector);
				break;

			case "MQGET":
				MQConnectorCfg mqGetConnector = new MQConnectorCfg(connFolder);
				mqGetConnector.setType(MQConnectorCfg.Types.MQGET);
				resources.addMqConnector(mqGetConnector);
				break;

			case "MQPUT":
				MQConnectorCfg mqPutConnector = new MQConnectorCfg(connFolder);
				mqPutConnector.setType(MQConnectorCfg.Types.MQPUT);
				resources.addMqConnector(mqPutConnector);
				break;

			case "DBPUT":
				DBConnectorCfg dbPutConnector = new DBConnectorCfg(connFolder);
				dbPutConnector.setType(DBConnectorCfg.Types.DBPUT);
				resources.addDbConnector(dbPutConnector);
				break;

			case "DBGET":
				DBConnectorCfg dbGetConnector = new DBConnectorCfg(connFolder);
				dbGetConnector.setType(DBConnectorCfg.Types.DBGET);
				resources.addDbConnector(dbGetConnector);
				break;

			case "SCPPUT":
				SCPConnectorCfg scpConnector = new SCPConnectorCfg(connFolder);
				scpConnector.setType(SCPConnectorCfg.Types.SCPPUT);
				resources.addScpConnector(scpConnector);
				break;

			default:
				throw new InvalidConnectorFormatException("Invalid connector : " + cType + " under " + connFolder.getAbsolutePath());
			}

		}
	}

	public ResourcesHolder getResourcesHolder() {
		return resourcesHolder;
	}

	public void setResourcesHolder(ResourcesHolder resourcesHolder) {
		this.resourcesHolder = resourcesHolder;
	}

}
