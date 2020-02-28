package io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors;

import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

public class HTTPConnector extends BaseConnector {

	public enum Types {
		HTTP
	}

	private String url = "@https://default@";

	private String method = "@POST|GET|PUT@";

	private String contentType = "@application/xml@";

	private String userAgent = "@GENERATED!!!! Mozilla/5.0@";

	private String timeout = "@500000@";

	private Types type = Types.HTTP;

	private String dsRefId = "defaultKS";

	public HTTPConnector() {
		super();
	}

	public HTTPConnector(File location) {
		super(location);
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDsRefId() {
		return dsRefId;
	}

	public void setDsRefId(String dsRefId) {
		this.dsRefId = dsRefId;
	}

	@Override
	public String toString() {
		return type + ":{" + (Strings.isNullOrEmpty(getId()) ? "" : "id=" + getId() + ", ") + (Strings.isNullOrEmpty(url) ? "" : "url=" + url + ", ")
				+ (Strings.isNullOrEmpty(method) ? "" : "method=" + method + ", ") + (Strings.isNullOrEmpty(userAgent) ? "" : "userAgent=" + userAgent + ", ")
				+ (Strings.isNullOrEmpty(String.valueOf(timeout)) ? "" : "timeout=" + timeout + ", ")
				+ (Strings.isNullOrEmpty(dsRefId) ? "" : "dsRefId=" + dsRefId + ", ") + "}";
	}

	@Override
	public Map<Context, List<Message>> validate() {
		return ValidationUtils.checkFieldsValid(getContext(), getFieldsAsOrderedMap());
	}

	@Override
	public Map<String, String> getFieldsAsOrderedMap() {
		return ImmutableMap.<String, String> builder().put("id", getId()).put("type", getType().toString()).put("url", url)
				.put("method", method).put("contentType", contentType).put("userAgent", userAgent).put("timeout", timeout).put("dsRefId", dsRefId).build();

	}

}
