package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;

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
import com.rockit.common.blackboxtester.connector.impl.db.DatabaseConnection;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants.Connectors;
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;

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

public class DBPutConnector extends DatabaseConnection implements WriteConnector {

	public static final Logger LOGGER = Logger.getLogger(DBPutConnector.class.getName());
	private static final String DELIMITER = ";";
	static final String LINE_SEPARATOR = "###"; //to handle additional semicolons, e.g. in methods like TO_CBLOB('') 


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
		List<String> commandsList = extractSQLCommandsFromPayload();
		for (String cmd : commandsList) {
			cmd = cmd.replaceAll(LINE_SEPARATOR,  " ");
			LOGGER.debug(cmd);

			try (final Statement statement = connection.createStatement()) {
				statement.execute(cmd);
				connection.commit();
			} catch (SQLException e) {
				throw new ConnectorException("Error for cmd: " + cmd, e);
			}

		}

	}

	List<String> extractSQLCommandsFromPayload() {
		StringBuilder commands = new StringBuilder();
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.defaultCharset());
			for (String line : lines) {
				final String trimmedLine = line.trim();
				if ((trimmedLine.length() < 1) || trimmedLine.startsWith("//") || trimmedLine.startsWith("--")) {
					continue;
				}
				commands.append(line.trim()).append(LINE_SEPARATOR);
			}
		} catch (IOException e) {
			throw new ConnectorException("Error for reading payload file: " + file.getAbsolutePath(), e);
		}

		List<String> commandsList = Splitter.on(DELIMITER+LINE_SEPARATOR).trimResults().omitEmptyStrings().splitToList(commands);
		return commandsList;
	}

	@Override
	public void setRequest(final File requestFile) {
		file = PayloadReplacer.interpolate(requestFile, configuration().getPayloadReplacements());
	}

	@Override
	public void setRequest(final String request) {
		throw new ConnectorException("[Connector:" + getId() + "] \t Set method is not allowed");
	}
}
