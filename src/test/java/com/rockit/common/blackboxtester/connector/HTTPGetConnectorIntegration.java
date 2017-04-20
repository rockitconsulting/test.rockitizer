package com.rockit.common.blackboxtester.connector;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import com.rockit.common.blackboxtester.connector.impl.HTTPGetConnector;

public class HTTPGetConnectorIntegration {
	public static final Logger LOGGER = Logger.getLogger(HTTPGetConnectorIntegration.class.getName());

	@Test
	public void test() throws JSONException, IOException {
		
		String requestUrl  = "http://elastic:changeme@linux-wmnh:9200/log_idx/_search?sort=dateTime:desc&size=1";
		
		



		URL url = new URL(requestUrl);
		URLConnection urlConnection = url.openConnection();
		
		if(  url.getUserInfo() != null && url.getUserInfo().split(":").length==2 ) {
			LOGGER.info("Basic authentication usr/pwd >   " + url.getUserInfo());
			String name = url.getUserInfo().split(":")[0];
			String password = url.getUserInfo().split(":")[1];
	        byte[] authEncBytes = Base64.getEncoder().encode( (name + ":" + password).getBytes() );
	        urlConnection.setRequestProperty("Authorization", "Basic " + new String(authEncBytes));
	        

		} 
        
        InputStream is = urlConnection.getInputStream();
		
		String xmlResp = XML.toString(new JSONObject(IOUtils.toString(is)));
        
		StringBuilder response = new StringBuilder().append("<root>").append(xmlResp).append("</root>");
        LOGGER.info( response );

		
//		HTTPGetConnector httpget = HTTPGetConnector();
		
//		http://elastic:changeme@linux-wmnh:9200/log_idx/_search?sort=dateTime:desc&size=1
			

	}

}
