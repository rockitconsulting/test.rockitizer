package com.rockit.common.blackboxtester.assertions;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.rockit.common.blackboxtester.exceptions.AssertionException;
import com.rockit.common.blackboxtester.exceptions.GenericException;
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

public class DBAssertion extends AbstractAssertion {

	public static final Logger LOGGER = Logger.getLogger(DBAssertion.class.getName());
	private List<String> mustContainTokens;
	private String refDsId;
	private String sql;
	private Connection connection;

	/**
	 * default DB lookup with assertion, checking if the values are contained in
	 * sql reslut
	 * 
	 * @param sqlQuery
	 * @param mustContainTokens
	 *            - list of values to check on
	 */
	public DBAssertion(String sqlQuery, List<String> mustContainTokens) {
		this("defaultDB", sqlQuery, mustContainTokens);
	}

	/**
	 * DB lookup with assertion, checking if the values are contained in sql
	 * reslut
	 * 
	 * @param datasourceId
	 *            - id from resources.yaml
	 * @param sqlQuery
	 * @param mustContainTokens
	 *            - list of values to check on
	 */
	public DBAssertion(String datasourceId, String sqlQuery, List<String> mustContainTokens) {
		this.refDsId = datasourceId;
		this.mustContainTokens = mustContainTokens;
		this.sql = sqlQuery;
	}

	/** Executes an SQL query with assertion of mustContainTokens. 
	 * @see com.rockit.common.blackboxtester.assertions.Assertions#proceed()
	 */
	@Override
	
	public void proceed() {

		String result = executeSql();
		LOGGER.info("sql: " + sql + "\n result: \n  " + result);
		for (String token : mustContainTokens) {
			try {
				assertTrue("mustContainTokens: " + token + " must be in result", result.contains(token));
			} catch (AssertionError e) {
				throw new AssertionException(e);
			}

		}
	}

	/** Executes an SQL query, using connection details.
	 * @return - returns result converted to a string.
	 */
	private String executeSql() {

		StringBuilder resultBuilder = new StringBuilder();

		createDatabaseConnection();

		ResultSetMetaData md;
		int cols;

		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
			md = rs.getMetaData();
			cols = md.getColumnCount();

			for (int i = 0; i < cols; i++) {
				resultBuilder.append(md.getColumnLabel(i + 1) + "\t");
			}

			while (rs.next()) {
				for (int i = 0; i < cols; i++) {
					String value = rs.getString(i + 1);
					resultBuilder.append(value + "\t");
				}
				resultBuilder.append("\n");
			}

		} catch (SQLException e) {
			LOGGER.error("Database access error: ", e);
			throw new GenericException(e);
		}
		return resultBuilder.toString();
	}

	/** Tries to establish connection with a database. 
	 *  Applies a DB2 or Oracle driver. 
	 */
	private void createDatabaseConnection() {

		DBConnectorCfg dummy = new DBConnectorCfg();
		dummy.setDsRefId(refDsId);

		DBDataSource ds = configuration().getDBDataSourceByConnector(dummy);

		String url = ds.getUrl();
		String user = ds.getUser();
		String password = ds.getPassword();

		try {
			Class.forName(url.contains("db2") ? Constants.DB2_DRIVER : Constants.ORACLE_DRIVER).newInstance();
			connection = DriverManager.getConnection(url, user, password);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {

			LOGGER.error("DB Connection cannot be instantiated. \n" + "url:" + url + ", user:" + user + ", password:" + password, e);
			throw new GenericException(e);

		}
	}
	
	
	/** Is used by ResultBuilder's return. Checks values not to be nulls or empty.  
	 * @see com.rockit.common.blackboxtester.assertions.AbstractAssertion#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName() +
				", datasourceId:\"" + (refDsId != null && !refDsId.isEmpty() ? refDsId : "")  +
				", sqlQuery:\"" + (sql != null && !sql.isEmpty() ? sql : "")  +
				(null != mustContainTokens && !mustContainTokens.isEmpty() ? ", assertContainsTokens:\""+Joiner.on(",").useForNull("").join(mustContainTokens)+"\"" : "" )+ 
						" )";
	}
	
}
