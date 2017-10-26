package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.exceptions.AssertionException;

public class CSVFileAssertion extends AbstractAssertion  {

	public static final Logger LOGGER = Logger.getLogger(CSVFileAssertion.class.getName());


	public CSVFileAssertion(String recordPath, String replayPath) {

		setRecordPath(recordPath);
		setReplayPath(replayPath);
	}
	
	@Override
	public void proceed() {
		File recordFolder = new File( recordPath );
		File replayFolder = new File( replayPath );
		
		for (File recordFile : Files.fileTreeTraverser().preOrderTraversal(recordFolder)) {
			
			String relativePath = recordFolder.toURI().relativize(recordFile.toURI()).getPath();
			File replayFile = new File(replayFolder + "/" + relativePath );
			
			LOGGER.debug("asserting: source " + recordFile.getAbsolutePath() + " with target "  + replayFile.getAbsolutePath() );
			
			try { 
				if(!recordFile.isDirectory() && !replayFile.isDirectory()) {
					csvAssert(recordFile, replayFile);
				}
			} catch (AssertionError e) {
				throw new AssertionException(" csv files doesn't match each other "  + replayFile.getAbsolutePath() + " / " + recordFile.getAbsolutePath()  , e);
			}
				
			
		}		
	}
	
	private void csvAssert(File f1, File f2)   {
		
		assertTrue(f1.getAbsolutePath()+ " not exists",  f1.exists());
		assertTrue(f2.getAbsolutePath()+ " not exists" , f2.exists());

		Set<String> f1Lines;
		try {
			f1Lines = new HashSet<String>(FileUtils.readLines(f1));
			Set<String> ff1Lines = new HashSet<String>(f1Lines);
			Set<String> f2Lines = new HashSet<String>(FileUtils.readLines(f2));
			Set<String> ff2Lines = new HashSet<String>(f2Lines);
			f1Lines.removeAll(ff2Lines);
			f2Lines.removeAll(ff1Lines);
			assertTrue("\n " + f1.getAbsolutePath() + " diff: \n " + StringUtils.join(f1Lines, "\n") + "\n-----------------\n" + f2.getAbsolutePath() + " diff: \n " + StringUtils.join(f2Lines, "\n"), f2Lines.isEmpty() && f1Lines.isEmpty());
		} catch (IOException e) {
			LOGGER.error("asserting: " + f1.getAbsolutePath() + " with target "  + f2.getAbsolutePath() , e );
			throw new AssertionError("asserting: " + f1.getAbsolutePath() + " with target "  + f2.getAbsolutePath(), e);
		}
	}
}
