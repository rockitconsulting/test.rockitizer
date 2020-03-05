package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.google.common.io.Files;
import com.rockit.common.blackboxtester.connector.ReadConnector;
import com.rockit.common.blackboxtester.connector.WriteConnector;
import com.rockit.common.blackboxtester.connector.impl.http.ResponseHeader;
import com.rockit.common.blackboxtester.exceptions.ConnectorException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;

/**
 * Test.Rockitizer - API regression testing framework Copyright (C) 2020
 * rockit.consulting GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 *
 */

public class HTTPConnector implements ReadConnector, WriteConnector {

	public static final Logger LOGGER = Logger.getLogger(HTTPConnector.class.getName());

	private StringBuilder resultBuilder;
	private String id, urlStr, method, contentType, userAgent, trustStore;

	private Integer connectTimeOut;
	private URL url;
	private File file;

	public HTTPConnector(String id) {
		io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnector cfg = (io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnector) configuration()
				.getConnectorById(id);

		this.id = id;
		this.urlStr = cfg.getUrl();
		this.method = cfg.getMethod();
		this.contentType = cfg.getContentType();
		this.userAgent = cfg.getUserAgent();
		this.connectTimeOut = Integer.valueOf(cfg.getTimeout());

		KeyStore ks = configuration().getKeyStoreByConnector(cfg);
		if (ks != null) {
			this.trustStore = ks.getPath();
		}
	}

	static {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String s, SSLSession sslSession) {
				return true;// NOSONAR
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rockit.common.blackboxtester.connector.Connector#proceed()
	 */
	@Override
	public void proceed() {
		HttpURLConnection urlConnection = null;
		try {

			this.url = new URL(urlStr);

			if (this.trustStore != null) {
				System.setProperty("javax.net.ssl.trustStore", this.trustStore);
			}
			System.setProperty("javax.net.ssl.trustStoreType", "jks");

			if (urlStr.startsWith("https://")) {
				urlConnection = (HttpsURLConnection) url.openConnection();
			} else {
				urlConnection = (HttpURLConnection) url.openConnection();
			}

			urlConnection.setRequestProperty("User-Agent", this.userAgent);
			urlConnection.setRequestMethod(this.method);
			urlConnection.setRequestProperty("Content-Type", this.contentType);
			if (!this.method.equalsIgnoreCase("GET")) {
				urlConnection.setDoOutput(true);
			}
			if (this.connectTimeOut > 0) {
				urlConnection.setConnectTimeout(this.connectTimeOut);
			}

			enhanceBasicAuthentication(urlConnection);
			if (urlConnection.getDoOutput()) {
				enhancePayload(urlConnection);
			}

			InputStream is = urlConnection.getInputStream();
			String result = IOUtils.toString(is);

			if (this.contentType.equalsIgnoreCase(CONTENT_TYPE_XML)) {
				setReponse(result);
			} else {

				JSONObject response = new JSONObject();

				// Header & Body
				Map<String, List<String>> map = urlConnection.getHeaderFields();
				ResponseHeader newResonseHeader = new ResponseHeader(map);
				JSONObject responseHeader = newResonseHeader.getResponsHeader();
				response.put("response", new JSONObject().put("header", responseHeader).put("body", getJsonArrayBody(result)));

				setReponse(XML.toString(response));

			}

		} catch (IOException e) {
			throw new ConnectorException("can not open connection: " + url, e);

		} catch (JSONException je) {
			throw new ConnectorException("JSON deserialization Exception", je);

		} finally {
			try {
				if (urlConnection != null) {
					urlConnection.disconnect();
				}
			} catch (Exception e) {// NOSONAR
			}
		}

	}

	private JSONArray getJsonArrayBody(String result) throws IOException {

		JSONObject jsonObject = null;

		try {
			JSONObject myJsonObject = new JSONObject(result);
			JSONArray newJsonArray = new JSONArray();
			newJsonArray.put(myJsonObject);
			return newJsonArray;

		} catch (JSONException je) {

			try {

				JSONArray newJsonArray = new JSONArray(result);
				return newJsonArray;

			} catch (JSONException e) {

				jsonObject = XML.toJSONObject(result);
				JSONArray newJsonArray = new JSONArray(jsonObject);
				return newJsonArray;

			}

		}
	}

	private void enhancePayload(HttpURLConnection urlConnection) throws UnsupportedEncodingException, IOException {
		if (this.file != null) {
			OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
			writer.write(Files.asCharSource(this.file, Charset.defaultCharset()).read());
			writer.close();
		}
	}

	private void enhanceBasicAuthentication(HttpURLConnection urlConnection) {

		if (url.getUserInfo() != null && url.getUserInfo().split(":").length == 2) {
			LOGGER.debug("Basic authentication usr/pwd >   " + url.getUserInfo());
			String name = url.getUserInfo().split(":")[0];
			String password = url.getUserInfo().split(":")[1];
			byte[] authEncBytes = Base64.getEncoder().encode((name + ":" + password).getBytes());
			urlConnection.setRequestProperty("Authorization", "Basic " + new String(authEncBytes));// NOSONAR
		}
	}

	@Override
	public String getId() {
		return id;
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

	}
	//
	// public void setUrlStr(String urlStr) {
	// this.urlStr = urlStr;
	// }
	// public void setMethod(String method) {
	// this.method = method;
	// }
	// public void setContentType(String contentType) {
	// this.contentType = contentType;
	// }

}
