package com.rockit.common.blackboxtester.connector.impl;import static io.github.rockitconsulting.test.rockitizer.configuration.Configuration.configuration;import io.github.rockitconsulting.test.rockitizer.configuration.model.res.connectors.MQConnectorCfg;import io.github.rockitconsulting.test.rockitizer.configuration.model.res.datasources.MQDataSource;import io.github.rockitconsulting.test.rockitizer.payload.model.MqPayload;import java.io.IOException;import java.util.HashMap;import java.util.Hashtable;import java.util.Map;import org.apache.log4j.Logger;import org.bouncycastle.util.encoders.Hex;import com.google.common.base.Strings;import com.ibm.mq.MQException;import com.ibm.mq.MQGetMessageOptions;import com.ibm.mq.MQMessage;import com.ibm.mq.MQPutMessageOptions;import com.ibm.mq.MQQueue;import com.ibm.mq.MQQueueManager;import com.ibm.mq.constants.CMQC;import com.ibm.mq.constants.MQConstants;import com.ibm.mq.headers.MQDataException;import com.rockit.common.blackboxtester.exceptions.ConnectorException;/** * Test.Rockitizer - API regression testing framework Copyright (C) 2020 * rockit.consulting GmbH * * This program is free software: you can redistribute it and/or modify it under * the terms of the GNU General Public License as published by the Free Software * Foundation, either version 3 of the License, or (at your option) any later * version. * * This program is distributed in the hope that it will be useful, but WITHOUT * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more * details. * * You should have received a copy of the GNU General Public License along with * this program. If not, see http://www.gnu.org/licenses/. * */public abstract class MQAccessor {	public static final Logger LOGGER = Logger.getLogger(MQAccessor.class.getName());	 	static Map<String, MQQueueManager> cacheQmgr = new HashMap<>();	static Map<String, MQQueue> cacheQueue = new HashMap<>();	private HashMap<String, Object> mqEnv;	private MQConnectorCfg mqConnConfig;	private MQDataSource mqDataSource;	public MQAccessor(String id) {		this( (MQConnectorCfg) configuration().getConnectorById(id) );	}	public MQAccessor(MQConnectorCfg cfg) {		this( cfg, configuration().getMQDataSourceByConnector(cfg) );	}			public MQAccessor(MQConnectorCfg cfg , MQDataSource ds) {		this.mqConnConfig = cfg;		this.mqDataSource = ds;		mqEnv = new HashMap<String, Object>();		mqEnv.put(MQConstants.USE_MQCSP_AUTHENTICATION_PROPERTY, false);		mqEnv.put(CMQC.CHANNEL_PROPERTY, ds.getChannel());		mqEnv.put(CMQC.HOST_NAME_PROPERTY, ds.getHost());		mqEnv.put(CMQC.PORT_PROPERTY, new Integer(ds.getPort())); 		if (!Strings.isNullOrEmpty(ds.getUser()) && !Strings.isNullOrEmpty(ds.getPassword())) {			mqEnv.put(MQConstants.USE_MQCSP_AUTHENTICATION_PROPERTY, true);			mqEnv.put(CMQC.USER_ID_PROPERTY, ds.getUser());			mqEnv.put(CMQC.PASSWORD_PROPERTY, ds.getPassword());		}	}			public String get() {		try {			final MQMessage msg = new MQMessage();			final MQGetMessageOptions gmo = new MQGetMessageOptions();			gmo.options = CMQC.MQGMO_PROPERTIES_FORCE_MQRFH2; 					if(null!=mqConnConfig.getCorrelationId()) {				msg.correlationId = Hex.decode(mqConnConfig.getCorrelationId());				gmo.matchOptions = MQConstants.MQMO_MATCH_CORREL_ID;				gmo.waitInterval = MQConstants.MQWI_UNLIMITED;							} else if(null!=mqConnConfig.getMessageId()) {				msg.messageId = Hex.decode(mqConnConfig.getMessageId());				gmo.matchOptions = MQConstants.MQMO_MATCH_MSG_ID;				gmo.waitInterval = MQConstants.MQWI_UNLIMITED;			} else if(mqConnConfig.isGroupedMode()) {				//CMQC.MQGI_NONE;//000000000000000000000000000000000000000000000000				msg.groupId = Hex.decode(mqConnConfig.getGroupId());				gmo.options += CMQC.MQGMO_FAIL_IF_QUIESCING	| CMQC.MQGMO_NO_WAIT | CMQC.MQGMO_NO_SYNCPOINT | CMQC.MQGMO_LOGICAL_ORDER | CMQC.MQGMO_ALL_MSGS_AVAILABLE;				gmo.matchOptions = MQConstants.MQMO_MATCH_GROUP_ID;				gmo.waitInterval = MQConstants.MQWI_UNLIMITED;			}						int openOptions= 0; 			if (mqConnConfig.isGroupedMode()) { 				openOptions = CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_BROWSE | CMQC.MQOO_INPUT_AS_Q_DEF  ;			} else {				openOptions = CMQC.MQOO_INQUIRE + CMQC.MQOO_FAIL_IF_QUIESCING + CMQC.MQOO_INPUT_SHARED + CMQC.MQGMO_PROPERTIES_AS_Q_DEF + CMQC.MQOO_OUTPUT +  CMQC.MQOO_BROWSE;			}						final MQQueue queue = getQueue(mqConnConfig.getQueue() , openOptions);			queue.get(msg, gmo);			MqPayload payload = MQPayloadBuilder.newPayload(msg);						handleCloseQueue(mqConnConfig.getQueue() , payload.getHeader().getMessageFlags() );						return MQPayloadBuilder.toXmlString(payload);		} catch (MQException | IOException | MQDataException e) {						if(e instanceof MQException && ((MQException) e).getReason()==2033 ) {				return null; //no messages in queue			} else {				throw new ConnectorException("Can not read queue: " + mqConnConfig.getQueue(), e);			}		}	}	public void putMessage(final byte[] message ) {		try {			int openOptions = CMQC.MQOO_OUTPUT;// | CMQC.MQOO_SET_ALL_CONTEXT;//CMQC.MQOO_SET_IDENTITY_CONTEXT;			final MQQueue queue = getQueue(mqConnConfig.getQueue(), openOptions);			write(queue, message);// Writing message into the queue			handleCloseQueue(mqConnConfig.getQueue(), 0);		} catch (final MQException ex) {			throw new ConnectorException("can not write queue: " + mqConnConfig.getQueue(), ex);		}	}		private void write(final MQQueue queue,  final byte[] message) {		try {			MQMessage mQMsg = MQPayloadBuilder.newMqMessageFromXMLString(message);			final MQPutMessageOptions pmo = new MQPutMessageOptions();			pmo.options = CMQC.MQPMO_NO_SYNCPOINT | CMQC.MQPMO_FAIL_IF_QUIESCING;			queue.put(mQMsg, pmo);			LOGGER.debug("Message successfully written to " + queue.getName());		} catch (IOException | MQException ex) {			throw new ConnectorException("queue not be written: " + mqConnConfig.getQueue(), ex);		}	}	private MQQueue getQueue(String qName, int openOptions) throws MQException {		if(!cacheQueue.containsKey(qName)) {			cacheQueue.put(qName, getQmgr().accessQueue(qName, openOptions));					} 		return cacheQueue.get(qName);   }	private void handleCloseQueue(String qName, int messageFlags) throws MQException {		MQQueue mqQueue = cacheQueue.get(qName);		if (!mqConnConfig.isGroupedMode() || ( mqConnConfig.isGroupedMode() && messageFlags== (MQConstants.MQMF_LAST_MSG_IN_GROUP | MQConstants.MQMF_MSG_IN_GROUP)) ) {			cacheQueue.remove(qName);			mqQueue.close();		} 	}		private MQQueueManager getQmgr() {		if (null == cacheQmgr.get(mqDataSource.getQmgr())) {			LOGGER.debug("Connecting MQQueueManager " + mqDataSource.getQmgr());			try {				cacheQmgr.put(mqDataSource.getQmgr() , new MQQueueManager(mqDataSource.getQmgr(), new Hashtable<String, Object>(mqEnv)));			} catch (final MQException e) {				throw new ConnectorException("MQQueueManager not available: " + mqDataSource.getQmgr(), e);			}		}		return cacheQmgr.get(mqDataSource.getQmgr());	}			@Override	public String toString() {		return "WebsphereMQ(" + mqDataSource.toString() + ")";	}	public abstract String getId();	public abstract String getType();		public HashMap<String, Object> getMqEnv() {		return mqEnv;	}	public MQConnectorCfg getMqConnConfig() {		return mqConnConfig;	}	public void setMqConnConfig(MQConnectorCfg mqConnConfig) {		this.mqConnConfig = mqConnConfig;	}	public MQDataSource getMqDataSource() {		return mqDataSource;	}	public void setMqDataSource(MQDataSource mqDataSource) {		this.mqDataSource = mqDataSource;	}}