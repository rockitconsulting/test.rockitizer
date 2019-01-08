package com.rockit.common.blackboxtester.util;

import java.io.File;
import java.io.IOException;


import org.apache.log4j.Logger;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import com.rockit.common.blackboxtester.exceptions.GenericException;

public class FileUtils {
	
	public static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());
	
	public static Iterable<File> listFolders(final File root) { 
		return Iterables.filter(
				Files.fileTraverser().breadthFirst( root ), new Predicate<File>() {
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
		return Iterables.filter(
				Files.fileTraverser().breadthFirst( root ), new Predicate<File>() {
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

	public static void copy(File from, File to) throws IOException  {
		if( from.isDirectory() ) {
			org.apache.commons.io.FileUtils.copyDirectory(from, to);			
		} else if (from.isFile() && to.isDirectory()){
			org.apache.commons.io.FileUtils.copyFileToDirectory(from, to);
		} else {
			Files.copy(from, to);
		}
		
	}
	
	

	
	public static void deleteDirectory(File file)  {
		try {
			org.apache.commons.io.FileUtils.deleteDirectory(file);
		} catch (IOException e) {
			LOGGER.warn("cannot delete " + file.getAbsolutePath(), e);
		} 
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

