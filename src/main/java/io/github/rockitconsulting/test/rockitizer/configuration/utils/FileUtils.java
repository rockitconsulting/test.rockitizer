package io.github.rockitconsulting.test.rockitizer.configuration.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;

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

public class FileUtils {

	public static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

	public static Iterable<File> listFolders(final File root) {
		return Iterables.filter(Files.fileTraverser().breadthFirst(root), new Predicate<File>() {
			@Override
			public boolean apply(File input) {
				return input.isDirectory() && input.getParentFile().equals(root);
			}

			@Override
			public boolean test(File input) {
				return apply(input);
			}
		});
	}
	
	public static Iterable<File> listFiles(final File root) {
		return listFiles(root, false);
	}


	public static Iterable<File> listFiles(final File root, boolean ignoreGitIgnore) {
		return Iterables.filter(Files.fileTraverser().breadthFirst(root), new Predicate<File>() {
			@Override
			public boolean apply(File input) {
				if(!ignoreGitIgnore) {
					return input.isFile() && input.getParentFile().equals(root);
				} else {
					return input.isFile() && input.getParentFile().equals(root) && !input.getName().equalsIgnoreCase(Constants.GITIGNORE);
				}
			}

			@Override
			public boolean test(File input) {
				return apply(input);
			}

		});

	}

	
	
	public static void copy(File from, File to) throws IOException {
		if (from.isDirectory()) {
			org.apache.commons.io.FileUtils.copyDirectory(from, to);
		} else if (from.isFile() && to.isDirectory()) {
			org.apache.commons.io.FileUtils.copyFileToDirectory(from, to);
		} else {
			Files.copy(from, to);
		}

	}

	public static void deleteDirectory(File file) {
		try {
			org.apache.commons.io.FileUtils.deleteDirectory(file);
		} catch (IOException e) {
			LOGGER.warn("cannot delete " + file.getAbsolutePath(), e);
		}
	}

	public static String readFile(String path) throws IOException {

		return Joiner.on(System.lineSeparator()).join(Files.readLines(new File(path), Charsets.UTF_8));

	}
	
	public static byte[] getContents(File file)  {
		byte[] content;
		try {
			
			content =  Files.toByteArray(file);
		} catch (IOException e) {
			LOGGER.error(e);
			throw new GenericException(e);
		} 
		return content;
		
	}

	public static void deleteFilesRecursive(File f) {
		
		if(f.exists() && f.isDirectory()) {
			File[] files = f.listFiles();
			for (File ff: files ) {
				deleteFilesRecursive(ff);
			}
		} else if(f.exists()) {
		   if(!f.delete()) {
			   LOGGER.warn("cannot delete " + f.getAbsolutePath());
		   }	
		}
	}

}
