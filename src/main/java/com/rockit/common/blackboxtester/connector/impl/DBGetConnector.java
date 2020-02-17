package com.rockit.common.blackboxtester.connector.impl;





import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;

import java.io.StringWriter;
import java.sql.SQLException;

import javax.sql.rowset.WebRowSet;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.util.DatabaseConnection;
import com.sun.rowset.WebRowSetImpl;


public class DBGetConnector extends DatabaseConnection implements ReadConnector {
	public static final Logger LOGGER = Logger.getLogger(DBGetConnector.class.getName());

	/**
	 * Connector/Folder name/id 
	 */
	private String name;
	private StringBuilder resultBuilder;
	private String sqlQuery;


	public DBGetConnector(String id) {
		super(id);
		DBConnector dbConCfg = (DBConnector) configuration().getConnectorById(id);
		this.name = id;
		this.sqlQuery = dbConCfg.getQuery();

	}

	private String executeSql() {
		WebRowSet webRS;
		StringWriter writer = null;

		try {
			webRS = new WebRowSetImpl();
			webRS.setCommand(sqlQuery);
			webRS.execute(connection);

			writer = new StringWriter();
			webRS.writeXml(writer);
			webRS.close();

		} catch (final SQLException e) {

			LOGGER.error("can not execute query: " + this.sqlQuery, e);
			throw new ConnectorException(e);

		}

		return writer.toString();

	}

	@Override
	public String getId() {
		return getType()  + getName();
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String getResponse() {
		return this.resultBuilder.toString();
	}

	@Override
	public String getType() {
		return Constants.Connectors.DBGET.toString();
	}

	@Override
	public void proceed() {

		createDatabaseConnection();

		setReponse(executeSql());

		try {
			this.connection.close();

		} catch (final SQLException e) {

			LOGGER.error("Database access error or other errors. \n" + e);
			throw new GenericException(e);
		}

	}

	@Override
	public void setReponse(final String response) {
		this.resultBuilder = new StringBuilder(response);

	}
}
