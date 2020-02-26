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
import com.rockit.common.blackboxtester.exceptions.GenericException;
import com.rockit.common.blackboxtester.suite.configuration.Constants;
import com.rockit.common.blackboxtester.suite.configuration.PayloadReplacer;

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
				return true;
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

			LOGGER.error("can not open connection: " + url, e);
			throw new ConnectorException(e);

		} catch (JSONException je) {
			// ( [TODO] eigene Exception werfen!!!!!!!!!
			LOGGER.error("JSON deserialization Exception", je);
			throw new ConnectorException(je);

		} finally {
			try {
				if (urlConnection != null) {
					urlConnection.disconnect();
				}
			} catch (Exception e) {
				// TODO: handle exception
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
			urlConnection.setRequestProperty("Authorization", "Basic " + new String(authEncBytes));
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
		LOGGER.error("Not supported: ");
		throw new GenericException("not yet supported");

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
