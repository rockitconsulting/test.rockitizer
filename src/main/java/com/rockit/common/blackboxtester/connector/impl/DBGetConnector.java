package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;
import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.util.DatabaseConnection;

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
		StringBuffer writer = new StringBuffer().append("<ROOT>").append(System.lineSeparator());

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery)) {
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				writer.append("<").append("ROW").append(">");
				for (int i = 1; i <= colCount; i++) {
					String col = metaData.getColumnLabel(i);
					String value = rs.getString(i);
					writer.append("<").append(col).append(">");
					writer.append(Strings.nullToEmpty(value).trim());
					writer.append("</").append(col).append(">");
				}
				writer.append("</").append("ROW").append(">").append(System.lineSeparator());
			}
		} catch (final SQLException e) {

			LOGGER.error("can not execute query: " + this.sqlQuery, e);
			throw new ConnectorException(e);

		}
		writer.append("</ROOT>");
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
