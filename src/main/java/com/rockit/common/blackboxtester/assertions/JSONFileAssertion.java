package com.rockit.common.blackboxtester.assertions;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.xmlunit.builder.Input;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.rockit.common.blackboxtester.exceptions.AssertionException;

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

public class JSONFileAssertion extends XMLFileAssertion {

	public static final Logger LOGGER = Logger.getLogger(JSONFileAssertion.class.getName());

	public JSONFileAssertion(String step) {
		super(step);
	}

	public JSONFileAssertion(String step, String connector) {
		super(step, connector);
	}

	@Override
	public void proceed() {
		File recordFolder = new File(recordPath + getRelPath());
		File replayFolder = new File(replayPath + getRelPath());

		for (File recordFile : Files.fileTraverser().depthFirstPreOrder(recordFolder)) {
			String relativePath = recordFolder.toURI().relativize(recordFile.toURI()).getPath();
			File replayFile = new File(replayFolder + File.separator + relativePath);

			if (recordFile.isFile() && replayFile.isFile()) {
				LOGGER.debug("jsonasserting " + recordFile.getPath() + " with " + replayFile.getPath());

				try {

					compare(Input.fromString(jsonToXMLFile(recordFile)), Input.fromString(jsonToXMLFile(replayFile))).build();
				} catch (AssertionError e) {
					throw new AssertionException(JSONFileAssertion.class.getSimpleName() + ":" + relativePath, e);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

			}
		}
	}

	String jsonToXMLFile(File recordFile) throws IOException {
		List<String> lines = Files.readLines(recordFile, Charset.defaultCharset());

		JSONObject json = new JSONObject(Joiner.on("").join(lines));
		String xml = XML.toString(json);
		return xml;
	}

}
