package io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources;

import io.github.rockitconsulting.test.rockitizer.validation.Validatable;
import io.github.rockitconsulting.test.rockitizer.validation.ValidationUtils;
import io.github.rockitconsulting.test.rockitizer.validation.model.Context;
import io.github.rockitconsulting.test.rockitizer.validation.model.Message;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;



/*

  - type: mq
    name: QM1
    port: 1414
    host: localhost
    channel: SYSTEM.BKR.CONFIG
    username: admin  
    password: admin 
    id: defMQ
 */

public class MQDataSource implements Validatable {

	
    String type = "MQ"; 
	String qmgr = "@QMGR@";
	String port = "@1414@";
	String host  ="@localhost@";
	String channel = "@SYSTEM.BKR.CONFIG@";
	/** null = Optional **/
	String user = null;
	/** null = Optional **/
	String password = null;
	String id = "defaultMQ";


	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}


	@Override
	public String toString() {
		return "MQDataSource [type=" + type + ", qmgr=" + qmgr + ", port="
				+ port + ", host=" + host + ", channel=" + channel
				+ ", user=" + user + ", password=" + password + ", id="
				+ id + "]";
	}
	
	@Override
	public boolean isValid() {
		return validate().size()==0;
	}
	
	@Override
	public Map<Context, List<Message>> validate() {
		return ValidationUtils.checkValid(getContext(), (Map<String, String>) ImmutableMap.of(
				"id", id,
				"qmgr", qmgr, 
				"port", port,
				"host", host,
				 "channel", channel
				));
	}
	
	
	
	
	@Override
	public Context getContext() {
		return new Context(getId());
	}
	
	public String getQmgr() {
		return qmgr;
	}
	public void setQmgr(String qmgr) {
		this.qmgr = qmgr;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
}
