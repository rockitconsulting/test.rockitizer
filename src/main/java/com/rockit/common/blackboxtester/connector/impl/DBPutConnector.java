package com.rockit.common.blackboxtester.connector.impl;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;
import com.rockit.common.blackboxtester.util.DatabaseConnection;

/**
 * Submitting of payload stored in the DBPUT@KEY folder.  
 * @author rockit2lp
 *
 */
public class DBPutConnector extends DatabaseConnection implements WriteConnector {

	public static final Logger LOGGER = Logger.getLogger(DBPutConnector.class.getName());
	private static final String DELIMITER = ";";

	private String name;
	private File file;


	public DBPutConnector(final String name) {
	
		super(configuration().getPrefixedString(name, Constants.DATASOURCE_URL_KEY),
				configuration().getPrefixedString(name, Constants.DATASOURCE_USERNAME_KEY),
				configuration().getPrefixedString(name, Constants.DATASOURCE_PASSWORD_KEY));
		this.name = name;
	}

	@Override
	public String getId() {
		return getType() + getName();
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String getType() {
		return Connectors.DBPUT.toString();
	}

	@Override
	public void proceed() {
		createDatabaseConnection();
		runScript();

	}

	private void runScript() {

		try {

			List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.defaultCharset());
			for (String line : lines) {
				StringBuilder command = new StringBuilder();
				final String trimmedLine = line.trim();
				if ((trimmedLine.length() < 1) || trimmedLine.startsWith("//") || trimmedLine.startsWith("--")) {
					continue;
				}
				command.append(line.substring(0, line.lastIndexOf(DELIMITER))).append(" ");
				LOGGER.info(command);
				final Statement statement = connection.createStatement();
				statement.execute(command.toString());
				connection.commit();
				statement.close();
			}
		} catch (SQLException |

				IOException e) {
			LOGGER.error(e);
			throw new ConnectorException(e);

		} finally {
			try {
				connection.rollback();
				connection.close();
			} catch (final SQLException e) {
				LOGGER.error(e);
			}
		}

	}

	@Override
	public void setRequest(final File requestFile) {
		file = PayloadReplacer.interpolate(requestFile);
	}

	@Override
	public void setRequest(final String request) {
		LOGGER.error("Not supported: ");
		throw new GenericException("not yet supported");

	}
}
