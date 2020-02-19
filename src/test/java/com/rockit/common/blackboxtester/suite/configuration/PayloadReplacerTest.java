package com.rockit.common.blackboxtester.suite.configuration;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.github.rockitconsulting.test.rockitizer.configuration.TestObjectFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PayloadReplacerTest {
	public static final Logger log = Logger.getLogger(PayloadReplacerTest.class.getName());



	
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
	
	
	@Before
	public void before() {
		TestObjectFactory.resetConfigurationToContextDemoPrj(this.getClass().getSimpleName());
		Assert.assertNotNull(configuration());
		Assert.assertEquals(configuration().getTchApi().getTestcasesFileName(), "PayloadReplacerTest-testcases.yaml");
		Assert.assertEquals(configuration().getRhApi().getResourcesFileName(), "PayloadReplacerTest-resources.yaml");
	}

	
	@Test
	public void interpolate() throws URISyntaxException, IOException {
		Path SRC = Paths.get(ClassLoader.getSystemResource("PayloadReplacement/test.xml").toURI());
		File srcFile = new File(SRC.toString());
		log.info("replacement for " + srcFile.getAbsolutePath());
		assertTrue(srcFile.exists());
		String content = new String(Files.readAllBytes(SRC));
		log.info("content:  " + content);

		assertFalse("DATEPLACEHOLDER not yet replaced (maybe already replaced by prevous test, need status quo rollback to init )" , content.contains(configuration().getPayloadReplacements().get("DATEPLACEHOLDER")));
		File interpolate = PayloadReplacer.interpolate(srcFile);
		String replace = new String(Files.readAllBytes(interpolate.toPath()));
		assertTrue("DATEPLACEHOLDER replaced ",replace.contains(configuration().getPayloadReplacements().get("DATEPLACEHOLDER")));

	}

}
