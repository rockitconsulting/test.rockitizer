package com.rockit.common.blackboxtester.assertions;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.DBConnector;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.DBDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.exceptions.AssertionException;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class DBAssertion extends AbstractAssertion {

	public static final Logger LOGGER = Logger.getLogger(DBAssertion.class.getName());
	private List<String> mustContainTokens;
	private String refDsId;
	private String sql;
	private Connection connection;

	public DBAssertion(String sql, List<String> mustContainTokens) {
		this("defaultDB", sql, mustContainTokens);
	}

	public DBAssertion(String dsId, String sql, List<String> mustContainTokens) {
		this.refDsId = dsId;
		this.mustContainTokens = mustContainTokens;
		this.sql = sql;
	}

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

	private String executeSql() {

		StringBuilder resultBuilder = new StringBuilder();

		createDatabaseConnection();

		ResultSetMetaData md;
		int cols;

		try (Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql)) {
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

	private void createDatabaseConnection() {

		DBConnector dummy = new DBConnector();
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
}
