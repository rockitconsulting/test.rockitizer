package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.util.DatabaseConnection;

public class DBGetConnector extends DatabaseConnection implements ReadConnector {
	public static final Logger LOGGER = Logger.getLogger(DBGetConnector.class.getName());

	/**
	 * Connector/Folder name/id
	 */
	private String id;
	private StringBuilder resultBuilder;
	private String sqlQuery;

	public DBGetConnector(String id) {
		super(id);
		DBConnector dbConCfg = (DBConnector) configuration().getConnectorById(id);
		this.id = id;
		this.sqlQuery = dbConCfg.getQuery();

	}

	private String executeSql() {
		StringBuffer writer = new StringBuffer().append("<root>");

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			ResultSetMetaData metaData = rs.getMetaData();

			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= colCount; colCount++) {
					String col = metaData.getColumnLabel(i);
					String value = rs.getString(i);
					writer.append("<").append(col).append(">");
					writer.append(value);
					writer.append("</").append(col).append(">");
				}
			}
		} catch (final SQLException e) {

			LOGGER.error("can not execute query: " + this.sqlQuery, e);
			throw new ConnectorException(e);

		}
		writer.append("</root>");
		return writer.toString();

	}

	@Override
	public String getId() {
		return this.id;
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
