package com.rockit.common.blackboxtester.assertions;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;
import static org.junit.Assert.assertTrue;

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
	private String sql;
	private Connection connection;

	public DBAssertion(String sql, List<String> mustContainTokens) {
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
				
		Statement statement;
		ResultSet rs;
		ResultSetMetaData md;
		int cols;
		
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
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
			
			rs.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			LOGGER.error("Database access error: ", e);
			throw new GenericException(e);
		}
		return resultBuilder.toString();
	}

	private void createDatabaseConnection(){
		
		String url = configuration().getString(Constants.DATASOURCE_URL_KEY);
		String user = configuration().getString(Constants.DATASOURCE_USERNAME_KEY);
		String password = configuration().getString(Constants.DATASOURCE_PASSWORD_KEY);
		
		try {
			Class.forName(url.contains("db2") ? Constants.DB2_DRIVER : Constants.ORACLE_DRIVER).newInstance();
			connection =  DriverManager.getConnection(url, user, password);
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			
			LOGGER.error("DB Connection cannot be instantiated. \n"+
					"url:"+ url + ", user:"+user +", password:"+password , e);
			throw new GenericException(e);
			
		} 
	}
}
