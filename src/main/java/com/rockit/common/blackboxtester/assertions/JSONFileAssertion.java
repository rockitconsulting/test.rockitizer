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

public class JSONFileAssertion extends XMLFileAssertion {

	public static final Logger LOGGER = Logger.getLogger(JSONFileAssertion.class.getName());

	
	public JSONFileAssertion(String step) {
		super(step);
	}

	
	@Override
	public void proceed() {
		File recordFolder = new File(recordPath+ File.separator + getStep());
		File replayFolder = new File(replayPath+ File.separator + getStep());

		for (File recordFile : Files.fileTraverser().depthFirstPreOrder(recordFolder)) {
			String relativePath = recordFolder.toURI().relativize(recordFile.toURI()).getPath();
			File replayFile = new File(replayFolder + File.separator + relativePath);

			if (recordFile.isFile() && replayFile.isFile()) {
				LOGGER.debug("jsonasserting " + recordFile.getPath()  + " with " + replayFile.getPath() );

				
				try { 
					

				    compare(Input.fromString(jsonToXMLFile(recordFile)),Input.fromString(jsonToXMLFile(replayFile))).build();
				} catch (AssertionError e) {
					throw new AssertionException(JSONFileAssertion.class.getSimpleName() + ":" +  relativePath , e);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				
				
			}
		}
	}


	 String jsonToXMLFile(File recordFile) throws IOException {
		 List<String> lines = Files.readLines(recordFile , Charset.defaultCharset());
		 
		JSONObject json = new JSONObject(Joiner.on("").join(lines));
		String xml = XML.toString(json);
		return xml;
	}

}
