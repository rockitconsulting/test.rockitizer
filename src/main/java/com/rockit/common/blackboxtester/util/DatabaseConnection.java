package com.rockit.common.blackboxtester.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

public class DatabaseConnection {
	public static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
	
	protected String dbUrl, dbUser, dbPwd;
	
	protected Connection connection;
	
	public DatabaseConnection(final String dbUrl, final String dbUser, final String dbPwd) {
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbPwd = dbPwd;

	}
	
	
	protected void createDatabaseConnection() {

		try {
			Class.forName(this.dbUrl.contains("db2") ? Constants.DB2_DRIVER : Constants.ORACLE_DRIVER).newInstance();
			this.connection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPwd);

		} catch (final InstantiationException e) {

			LOGGER.error("DB Connection object cannot be instantiated. \n" + "url:" + this.dbUrl + ", user:"
					+ this.dbUser + ", password:" + this.dbPwd, e);
			throw new GenericException(e);

		} catch (final IllegalAccessException | ClassNotFoundException | SQLException e) {

			LOGGER.error("DB Connection is not possible. \n" + "url:" + this.dbUrl + ", user:"
					+ this.dbUser + ", password:" + this.dbPwd, e);
			throw new GenericException(e);

		} 
	}
}
