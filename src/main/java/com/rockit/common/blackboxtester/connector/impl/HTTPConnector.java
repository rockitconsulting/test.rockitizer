package com.rockit.common.blackboxtester.connector.impl;

import static com.rockit.common.blackboxtester.suite.configuration.ConfigurationHolder.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;

public class HTTPConnector implements ReadConnector, WriteConnector {
	public static final Logger LOGGER = Logger.getLogger(HTTPConnector.class.getName());

	private StringBuilder resultBuilder;
	private String name, urlStr, method, contentType;

	private URL url;
	private File file;

	public HTTPConnector(String name) {
		this.name = name;
		this.urlStr = configuration().getPrefixedString(name, Constants.URL);
		this.method = configuration().getPrefixedString(name, Constants.METHOD);
		this.contentType = configuration().getPrefixedString(name, Constants.CONTENTTYPE);
	}
	
	
	@Override
	public void proceed() {


		try {

			
			URL url = new URL(urlStr);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod(this.method);
			//urlConnection.setPayload(this.file);
			urlConnection.setRequestProperty("Content-Type", this.contentType);
			if(  url.getUserInfo() != null && url.getUserInfo().split(":").length==2 ) {
				LOGGER.debug("Basic authentication usr/pwd >   " + url.getUserInfo());
				String name = url.getUserInfo().split(":")[0];
				String password = url.getUserInfo().split(":")[1];
		        byte[] authEncBytes = Base64.getEncoder().encode( (name + ":" + password).getBytes() );
		        urlConnection.setRequestProperty("Authorization", "Basic " + new String(authEncBytes));
			}
			
			
			
	        InputStream is = urlConnection.getInputStream();
	        String result = IOUtils.toString(is);

			setReponse(new StringBuilder().append("<root>").append(parseJSON(result)).append("</root>").toString());
	         
		} catch (IOException e) {

			LOGGER.error("can not open connection: " + url, e);
			throw new ConnectorException(e);

		} catch (JSONException je) {
			//( [TODO] eigene Exception werfen!!!!!!!!!
			LOGGER.error("JSON deserialization Exception", je);
			throw new ConnectorException(je);

		}

	
		}
	//( [TODO] eigene Exception werfen!!!!!!!!!
	public String parseJSON(String result) throws JSONException{
		try {
			return XML.toString(new JSONObject(result));
//			new JSONObject();
		} catch (JSONException ex) {
			// e.g. in case JSONArray is valid as well...
			return XML.toString(new JSONArray(result));
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return getType() + Constants.FLOW_NAME_SEPARATOR + getName();
	}

	@Override
	public String getType() {
		return Constants.Connectors.HTTP.toString();
	}

	@Override
	public String getResponse() {
		return resultBuilder.toString();
	}

	@Override
	public void setReponse(String response) {
		this.resultBuilder = new StringBuilder(response);
	}

	@Override
	public void setRequest(final File requestFile) {
		file = PayloadReplacer.interpolate(requestFile);
	}

	@Override
	public void setRequest(final String request) {
		LOGGER.error("Not supported: ");
		throw new GenericException("not yet supported");

	}
	
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


}
