package com.rockit.common.blackboxtester.assertions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.rockit.common.blackboxtester.exceptions.AssertionException;
import com.rockit.common.blackboxtester.exceptions.GenericException;

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

public class FileContainsStringAssertion extends AbstractAssertion {

	public static final Logger LOGGER = Logger.getLogger(FileContainsStringAssertion.class.getName());
	private List<String> mustContainTokens = new ArrayList<String>();

	
	@SuppressWarnings("unused")
	private FileContainsStringAssertion() {
	}
	
	
	/**
	 * FileContainsStringAssertion for all payloads on the step/connector/payload.ext level
     *   
	 * @param step  -subfolder of testcase
	 * @param connector - subfolder of step
	 * @param filename - payload file
	 * 
	 */
	public FileContainsStringAssertion(String step, String connector, String filename) {
		this.relPath = File.separator + step + File.separator + connector + File.separator + filename;
	}

	public FileContainsStringAssertion containsStrings(List<String> mustContainTokens) {
		this.mustContainTokens = mustContainTokens;
		return this;
	}

    @Override
    public void proceed() {
        File targetFile = new File(replayPath + relPath);

        // Read the file content
        String content;
        try {
            content = new String(java.nio.file.Files.readAllBytes(Paths.get(targetFile.getPath())), Charsets.UTF_8);
        } catch (IOException e) {
            throw new GenericException("Failed to read file: " + targetFile.getPath(), e);
        }

        // Check if the content contains each must-contain token
        for (String item : mustContainTokens) {
            if (!content.contains(item)) {
                throw new AssertionException(FileContainsStringAssertion.class.getSimpleName() + ": File " + targetFile.getPath() + " doesn't contain: " + item);
            }
        }
    }


	
	/** Is used by ResultBuilder's return. Checks values not to be nulls or empty.  
	 * @see com.rockit.common.blackboxtester.assertions.AbstractAssertion#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName() +
				(null != mustContainTokens && !mustContainTokens.isEmpty() ? ", assertContainsTokens:\""+Joiner.on(",").useForNull("").join(mustContainTokens)+"\"" : "" );
				
	}
	
}
