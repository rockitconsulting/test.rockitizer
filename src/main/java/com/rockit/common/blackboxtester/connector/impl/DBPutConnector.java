package com.rockit.common.blackboxtester.connector.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Splitter;
import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;
import com.rockit.common.blackboxtester.util.DatabaseConnection;

/**
 * Submitting of payload stored in the DBPUT@KEY folder.
 * 
 * @author rockit2lp
 *
 */
public class DBPutConnector extends DatabaseConnection implements WriteConnector {

	public static final Logger LOGGER = Logger.getLogger(DBPutConnector.class.getName());
	private static final String DELIMITER = ";";

	private String id;
	private File file;

	public DBPutConnector(final String id) {
		super(id);

		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
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
			List<String> commandsList = extractSQLCommandsFromPayload();
			for (String cmd : commandsList) {
				cmd = cmd + DELIMITER;
				LOGGER.info(cmd);

				final Statement statement = connection.createStatement();
				statement.execute(cmd.toString());
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

	List<String> extractSQLCommandsFromPayload() throws IOException {
		StringBuilder commands = new StringBuilder();
		List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.defaultCharset());
		for (String line : lines) {
			final String trimmedLine = line.trim();
			if ((trimmedLine.length() < 1) || trimmedLine.startsWith("//") || trimmedLine.startsWith("--")) {
				continue;
			}
			commands.append(line.trim()).append(" ");
		}

		List<String> commandsList = Splitter.on(DELIMITER).trimResults().omitEmptyStrings().splitToList(commands);
		return commandsList;
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
