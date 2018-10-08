package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.log4j.Logger;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.exceptions.AssertionException;

public class FileAssertion extends AbstractAssertion  {

	public static final Logger LOGGER = Logger.getLogger(FileAssertion.class.getName());


	public FileAssertion(String recordPath, String replayPath) {

		setRecordPath(recordPath);
		setReplayPath(replayPath);
	}
	
	@Override
	public void proceed() {
		File recordFolder = new File( recordPath );
		File replayFolder = new File( replayPath );
		
		for (File recordFile : Files.fileTraverser().depthFirstPreOrder(recordFolder)) {
			
			String relativePath = recordFolder.toURI().relativize(recordFile.toURI()).getPath();
			File replayFile = new File(replayFolder + "/" + relativePath );
			
			LOGGER.debug("asserting: source " + recordFile.getAbsolutePath() + " with target "  + replayFile.getAbsolutePath() );
			
			try { 
				assertTrue(" matching file "  + replayFile.getAbsolutePath() + " must exist", replayFile.exists()==true);
			} catch (AssertionError e) {
				throw new AssertionException(" matching file "  + replayFile.getAbsolutePath() + " must exist "  , e);
			}
				
			
		}		
	}
}
