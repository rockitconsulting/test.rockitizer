package com.rockit.common.blackboxtester.suite.configuration;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Test;

public class PayloadReplacerTest {

//	@Test
//	public void replace() throws URISyntaxException, IOException {
//		Path SRC = Paths.get(ClassLoader.getSystemResource("TestFileConnectors/test.xml").toURI());
//		File srcFile = new File(SRC.toString());
//		assertTrue(srcFile.exists());
//		String content = new String(Files.readAllBytes(SRC));
//		assertFalse(content.contains(configuration().getString("PAYLOAD.DATEPLACEHOLDER")));
//		String replace = StrSubstitutor.replace(content, new ConfigurationMap( configuration()));
//		assertTrue(replace.contains(configuration().getString("PAYLOAD.DATEPLACEHOLDER")));
//		Files.write(SRC, replace.getBytes());
//		assertTrue(srcFile.exists());
//		content = new String(Files.readAllBytes(SRC));
//		assertTrue(content.contains(configuration().getString("PAYLOAD.DATEPLACEHOLDER")));
//
//	}
//	
	@Test
	public void interpolate() throws URISyntaxException, IOException {
		Path SRC = Paths.get(ClassLoader.getSystemResource("PayloadReplacement/test.xml").toURI());
		File srcFile = new File(SRC.toString());
		assertTrue(srcFile.exists());
		String content = new String(Files.readAllBytes(SRC));
		assertFalse("PAYLOAD.DATEPLACEHOLDER not yet replaced (maybe already replaced by prevous test, need status quo rollback to init )" , content.contains(configuration().getString("PAYLOAD.DATEPLACEHOLDER")));
		PayloadReplacer.interpolate(srcFile);
		String replace = new String(Files.readAllBytes(SRC));
		assertTrue("PAYLOAD.DATEPLACEHOLDER replaced ",replace.contains(configuration().getString("PAYLOAD.DATEPLACEHOLDER")));

	}

}
