package io.github.rockitconsulting.test.rockitizer.configuration.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;

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
		return Iterables.filter(Files.fileTraverser().breadthFirst(root), new Predicate<File>() {
			@Override
			public boolean apply(File input) {
				return input.isFile() && input.getParentFile().equals(root);
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

	public static void deleteFilesRecursive(File f) {

		if (f.exists() && f.isDirectory()) {
			File[] files = f.listFiles();
			for (File ff : files) {
				deleteFilesRecursive(ff);
			}
		} else if (f.exists()) {
			if (!f.delete()) {
				LOGGER.warn("cannot delete " + f.getAbsolutePath());
			}
		}
	}

	public static String readFile(String path) throws IOException {

		return Joiner.on(System.lineSeparator()).join(Files.readLines(new File(path), Charsets.UTF_8));

	}

}
