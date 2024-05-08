package com.rockit.common.blackboxtester.connector.impl;

import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.HTTPConnectorCfg;
import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.KeyStore;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.rockit.common.blackboxtester.connector.ConnectorResponse;
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

	public static final Logger LOGGER = Logger.getLogger(HTTPConnector.class
			.getName());

	private ConnectorResponse response;
	private String id, urlStr, method, trustStore;

	private Map<String, String> headers = new HashMap<>();

	private Integer connectTimeOut;
	private File file;

	HTTPConnector(HTTPConnectorCfg cfg) {
		this.id = cfg.getId();
		this.urlStr = cfg.getUrl();
		this.method = cfg.getMethod();
		this.connectTimeOut = Integer.valueOf(cfg.getTimeout());
		this.headers = cfg.getHeaders();

		KeyStore ks = configuration().getKeyStoreByConnector(cfg);
		if (ks != null) {
			this.trustStore = ks.getPath();
		}

	}

	public HTTPConnector(String id) {
		this((HTTPConnectorCfg) configuration().getConnectorById(id));
	}

	static {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String s, SSLSession sslSession) {
				return true;// NOSONAR
			}
		});
	}

	@Override
	public void proceed() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {

			HttpRequestBase requestMethod = createRequestMethod(this.method,
					this.urlStr);

			// Manually add the Authorization header for Basic Authentication
			enhanceBasicAuthentication(requestMethod);

			// Set other headers...
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				requestMethod.addHeader(entry.getKey(), entry.getValue());
			}

			// Handling for PATCH and POST requests with body, and other
			// Special handling for PATCH and POST requests with a body
			if ((this.file != null)
					&& (Arrays.asList("POST", "PATCH", "PUT").contains(this.method.toUpperCase()))) {
				String content = new String(java.nio.file.Files.readAllBytes(this.file.toPath()),
						java.nio.charset.StandardCharsets.UTF_8);
				StringEntity entity;
				if (this.file.getName().endsWith(".js")	|| this.file.getName().endsWith(".json")) {
					entity = new StringEntity(content,	ContentType.APPLICATION_JSON);
				} else if (this.file.getName().endsWith(".xml")) {
					entity = new StringEntity(content,	ContentType.APPLICATION_XML);
				} else {
					entity = new StringEntity(content, ContentType.DEFAULT_TEXT);
				}
				if (requestMethod instanceof HttpEntityEnclosingRequestBase) {
					((HttpEntityEnclosingRequestBase) requestMethod).setEntity(entity);
				}
			}
			HttpResponse response = httpClient.execute(requestMethod);
			String result = EntityUtils.toString(response.getEntity());

			if ( response.getFirstHeader("Content-Type") != null && response.getFirstHeader("Content-Type").getValue().equalsIgnoreCase(CONTENT_TYPE_XML)) {
				setReponse( new ConnectorResponse("response",  result) );
			} else {
				if (!isJSONStringValid(result))  throw new ConnectorException("Invalid JSON: " + result);

				ResponseHeader newResonseHeader = new ResponseHeader( response.getAllHeaders() );
				JSONObject responseHeader = newResonseHeader.getResponseHeader();

				String responseString = buildPrettyPrintedJSON(responseHeader.toString().replace("null", "Code"), result);
				if (!isJSONStringValid(responseString)) throw new ConnectorException("Invalid JSON: " 	+ responseString);
				setReponse( new ConnectorResponse("response",  responseString) );
			}

		} catch (IOException e) {
			LOGGER.error("HTTP request execution error", e);
			throw new RuntimeException(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				LOGGER.error("Error closing HttpClient", e);
				// Handle closing error
			}
		}
	}

	private HttpRequestBase createRequestMethod(String method, String url) {
		switch (method.toUpperCase()) {
		case "GET":
			return new HttpGet(url);
		case "POST":
			return new HttpPost(url);
		case "PUT":
			return new HttpPut(url);
		case "DELETE":
			return new HttpDelete(url);
		case "PATCH":
			return new HttpPatch(url);
		default:
			throw new IllegalArgumentException("Unsupported HTTP method: "
					+ method);
		}
	}

	public boolean isJSONStringValid(String result) {
		try {
			new JSONObject(result);
		} catch (JSONException je) {
			try {
				new JSONArray(result);
			} catch (JSONException jex) {
				LOGGER.warn("Invalid JSON: " + result);
				return false;
			}
		}
		return true;
	}

	public String buildPrettyPrintedJSON(String responseHeader,	String responseBody) {
		StringBuilder responseString = new StringBuilder();
		responseString.append("{\"response\":{");
		responseString.append("\"header\":");
		responseString.append(responseHeader);
		responseString.append(",\"body\":");
		responseString.append(responseBody);
		responseString.append("}}");

		JSONObject jsonObject = new JSONObject(responseString.toString().replaceAll("\"","\\\""));
		return jsonObject.toString(4);
	}

	@SuppressWarnings("unused")
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

	private void enhanceBasicAuthentication(HttpRequestBase requestMethod)
			throws MalformedURLException {
		URL url = new URL(urlStr);

		if (url.getUserInfo() != null
				&& url.getUserInfo().split(":").length == 2) {
			LOGGER.debug("Basic authentication usr/pwd >   "
					+ url.getUserInfo());
			String name = url.getUserInfo().split(":")[0];
			String password = url.getUserInfo().split(":")[1];
			byte[] authEncBytes = Base64.getEncoder().encode(
					(name + ":" + password).getBytes());
			requestMethod.setHeader("Authorization", "Basic "
					+ new String(authEncBytes));// NOSONAR
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
	public ConnectorResponse getResponse() {
		return this.response;
	}

	@Override
	public void setReponse(ConnectorResponse response) {
		this.response = response;
	}

	@Override
	public void setRequest(final File requestFile) {
		file = PayloadReplacer.interpolate(requestFile, configuration()
				.getPayloadReplacements());
	}

	@Override
	public void setRequest(final String request) {

	}

}
