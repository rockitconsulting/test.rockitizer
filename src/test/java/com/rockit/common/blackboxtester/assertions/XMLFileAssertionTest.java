package com.rockit.common.blackboxtester.assertions;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.ElementSelectors;

import com.google.common.collect.ImmutableList;

/**
*  Test.Rockitizer - API regression testing framework 
*   Copyright (C) 2020  rockit.consulting GmbH
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see http://www.gnu.org/licenses/.
*
*/

public class XMLFileAssertionTest {
	public static Logger LOGGER = Logger.getLogger(XMLFileAssertionTest.class.getName());

	XMLFileAssertion xmlFileAssertion;

	@Before
	public void setUp() {
		xmlFileAssertion = new XMLFileAssertion(null);
	}

	
	@Test
	public void testXMLFileAssertionNull() {
		Assert.assertNotNull(new XMLFileAssertion(null).ignoreAttrs(null).ignore(null).toString());
		
	}
	
	@Test
	public void testXMLFileAssertionNullNull() {
		Assert.assertNotNull(new XMLFileAssertion(null,null).ignoreAttrs(null).ignore(null).toString());
		
	}
	
	
	@Test
	public void testIgnoreSuccess() {
		String control = "<root><test>2</test><ignore>cvc</ignore><sim>4</sim></root>";
		String test = "<root><test>4bxbcxcbx</test><ignore>cvkkkkc</ignore><sim>4</sim></root>";

		xmlFileAssertion.ignore(ImmutableList.of("test", "ignore"))
				.compare(Input.fromString(control), Input.fromString(test)).checkForSimilar()
				.withNodeMatcher(ElementSelectors.byNameAndText).build();

	}

	@Test
	public void testIncludeSuccess() {
		String control = "<root><test>2</test><ignore>cvc</ignore><sim>4</sim></root>";
		String test = "<root><test>4bxbcxcbx</test><ignore>cvkkkkc</ignore><sim>4</sim></root>";

		xmlFileAssertion.include(ImmutableList.of("sim"))
				.compare(Input.fromString(control), Input.fromString(test)).checkForSimilar()
				.withNodeMatcher(ElementSelectors.byNameAndText).build();

	}
	
	
	@Test(expected = AssertionError.class)
	public void testIgnoreException() {
		String control = "<root><test>2</test><ignore>cvc</ignore><sim>4</sim></root>";
		String test = "<root><test>4bxbcxcbx</test><ignore>cvkkkkc</ignore><sim>4</sim></root>";

		xmlFileAssertion.ignore(ImmutableList.of("test")).compare(Input.fromString(control), Input.fromString(test))
				.checkForSimilar().withNodeMatcher(ElementSelectors.byNameAndText).build();
	}

	@Test
	public void checkLogStructureWithIgnore() {
		String control = "<root><_shards><total>3</total><failed>0</failed><successful>3</successful></_shards><hits><hits><_index>log_idx</_index><_type>runtime</_type><_source><dateTime>2016-11-16 15:12:40.872</dateTime><bulkSize>349630</bulkSize><create_ms>219</create_ms><clientName>ROCKIT3</clientName><total_ms>649</total_ms><elastic_ms>430</elastic_ms></_source><_id>AVhte4MPWQ1XWbkTBg0f</_id><sort>1479309160872</sort><_score>null</_score></hits><total>791</total><max_score>null</max_score></hits><took>2</took><timed_out>false</timed_out></root>";
		String test = "<root><_shards><total>3</total><failed>0</failed><successful>3</successful></_shards><hits><hits><_index>log_idx</_index><_type>runtime</_type><_source><dateTime>2016-11-16 16:04:16.048</dateTime><bulkSize>349630</bulkSize><create_ms>229</create_ms><clientName>ROCKIT3</clientName><total_ms>715</total_ms><elastic_ms>486</elastic_ms></_source><_id>AVhtqr3YWQ1XWbkTBg09</_id><sort>1479312256048</sort><_score>null</_score></hits><total>809</total><max_score>null</max_score></hits><took>1</took><timed_out>false</timed_out></root>";
		
		xmlFileAssertion.ignore(ImmutableList.of("dateTime", "create_ms", "total_ms", "elastic_ms", "_id", "sort", "total","took"))
				.compare(Input.fromString(control), Input.fromString(test))
				.withNodeMatcher(ElementSelectors.byNameAndText).checkForSimilar().build();
	}

	@Test
	public void checkMonStructureWithIgnore() {
		String control = "<root><_shards><total>3</total><failed>0</failed><successful>3</successful></_shards><hits><hits><_index>cfg_idx</_index><_type>broker</_type><_source><date>2016-11-16 15:12:40.438</date><app>elasticSearchBulk</app><running>true</running><eg>RuntimeMonitoring</eg><modified>false</modified><type>flow</type><broker>IB9NODE</broker><flow>RuntimeMonitoringFlow</flow><group>zahoorapp</group><attrs>DeploytimePropertyFolder/barFileName=C:/Temp/gradle_build/elasticSearchBulk.zahoor/bar.target/elasticSearchBulk.bar</attrs><attrs>DeploytimePropertyFolder/deployTime=2016-11-11 16:49:10.555 +0100</attrs><attrs>DeploytimePropertyFolder/modifyTime=2016-11-11 16:49:08.000 +0100</attrs><attrs>MessageFlowRuntimeProperty/This/additionalInstances=0</attrs><attrs>MessageFlowRuntimeProperty/This/commitCount=1</attrs><attrs>MessageFlowRuntimeProperty/This/commitInterval=0</attrs><attrs>MessageFlowRuntimeProperty/This/coordinatedTransaction=no</attrs><attrs>MessageFlowRuntimeProperty/This/label=RuntimeMonitoringFlow</attrs><attrs>MessageFlowRuntimeProperty/This/runMode=running</attrs><attrs>MessageFlowRuntimeProperty/This/startMode=Maintained</attrs><attrs>MessageFlowRuntimeProperty/This/traceLevel=none</attrs><attrs>MessageFlowRuntimeProperty/This/userTraceLevel=none</attrs><attrs>MessageFlowRuntimeProperty/This/uuid=11101454-5801-0000-0080-8af24ab77390</attrs><attrs>deployed.as.source=true</attrs><attrs>lastupdate.user=rockit3lp</attrs><attrs>messageflow.additionalinstances=0</attrs><attrs>messageflow.commitcount=1</attrs><attrs>messageflow.commitinterval=0</attrs><attrs>messageflow.coordinatedtransaction=no</attrs><attrs>messageflow.deploytime=2016-11-11 16:49:10.555 +0100</attrs><attrs>messageflow.keywords=$MQSIBAR=C:/Temp/gradle_build/elasticSearchBulk.zahoor/bar.target/elasticSearchBulk.barMQSI$</attrs><attrs>messageflow.modifytime=2016-11-11 16:49:08.000 +0100</attrs><attrs>messageflow.node.1=&lt;ComIbmMQInputNode uuid=RuntimeMonitoringFlow#FCMComposite_1_1 userTraceLevel=none traceLevel=none label=MQ Input messageDomainProperty= messageSetProperty= messageTypeProperty= messageFormatProperty= messageEncodingProperty=0 messageCodedCharSetIdProperty=0 topicProperty= validate=no rootParserClassName=MQROOT additionalInstances=0 queueName=RUNTIMEMONITORING.TRIGGER.ZH transactionMode=yes orderMode=default logicalOrder=yes allMsgsAvailable=no matchMsgId=no matchCorrelId=no browse=no resetBrowseTimeout=-1 convert=no convertEncoding=546 convertCodedCharSetId=0 commitByMessageGroup=no tempDynamicQueue=no/&gt;</attrs><attrs>messageflow.node.2=&lt;ComIbmJavaComputeNode uuid=RuntimeMonitoringFlow#FCMComposite_1_2 userTraceLevel=none traceLevel=none label=BuildPerformanceStat javaClass=RuntimeMonitoringFlow_BuildPerformanceStat javaClassLoader=/&gt;</attrs><attrs>messageflow.node.3=&lt;ComIbmJavaComputeNode uuid=RuntimeMonitoringFlow#FCMComposite_1_3 userTraceLevel=none traceLevel=none label=BuildBulk javaClass=RuntimeConfigReader_BuildBulk javaClassLoader=/&gt;</attrs><attrs>messageflow.node.4=&lt;ComIbmWSRequestNode uuid=RuntimeMonitoringFlow#FCMComposite_1_4 userTraceLevel=none traceLevel=none label=SavePerformance messageDomainProperty= messageSetProperty= messageTypeProperty= messageFormatProperty= messageEncodingProperty=0 messageCodedCharSetIdProperty=0 topicProperty= URLSpecifier=http://linux-wmnh:9200/log_idx/runtime/ timeoutForServer=120 useWholeInputMsgAsRequest=no requestMsgLocationInTree=InputRoot.JSON.Data replaceInputMsgWithWSResponse=yes responseMsgLocationInTree=OutputRoot generateDefaultHttpHeaders=yes replaceInputMsgWithHTTPError=yes errorMsgLocationInTree=OutputRoot httpProxyLocation= followRedirection=no/&gt;</attrs><attrs>messageflow.node.5=&lt;ComIbmWSRequestNode uuid=RuntimeMonitoringFlow#FCMComposite_1_5 userTraceLevel=none traceLevel=none label=SaveBulk messageDomainProperty=BLOB messageSetProperty= messageTypeProperty= messageFormatProperty= messageEncodingProperty=0 messageCodedCharSetIdProperty=0 topicProperty= URLSpecifier=http://linux-wmnh:9200/cfg_idx/broker/_bulk timeoutForServer=120 useWholeInputMsgAsRequest=no requestMsgLocationInTree=InputRoot.BLOB replaceInputMsgWithWSResponse=yes responseMsgLocationInTree=OutputRoot generateDefaultHttpHeaders=yes replaceInputMsgWithHTTPError=yes errorMsgLocationInTree=OutputRoot httpProxyLocation= followRedirection=no protocol=TLS httpVersion=1.1 acceptCompressedResponses=yes/&gt;</attrs><attrs>messageflow.node.6=&lt;ComIbmTimeoutNotificationNode uuid=RuntimeMonitoringFlow#FCMComposite_1_7 userTraceLevel=none traceLevel=none label=Timeout Notification uniqueIdentifier=TriggerMon transactionMode=automatic operationMode=automatic timeoutInterval=600/&gt;</attrs><attrs>messageflow.node.last=6</attrs><attrs>messageflow.nodeconnection.1=RuntimeMonitoringFlow#FCMComposite_1_1,out,RuntimeMonitoringFlow#FCMComposite_1_3,in</attrs><attrs>messageflow.nodeconnection.2=RuntimeMonitoringFlow#FCMComposite_1_2,out,RuntimeMonitoringFlow#FCMComposite_1_4,in</attrs><attrs>messageflow.nodeconnection.3=RuntimeMonitoringFlow#FCMComposite_1_3,out,RuntimeMonitoringFlow#FCMComposite_1_5,in</attrs><attrs>messageflow.nodeconnection.4=RuntimeMonitoringFlow#FCMComposite_1_5,out,RuntimeMonitoringFlow#FCMComposite_1_2,in</attrs><attrs>messageflow.nodeconnection.5=RuntimeMonitoringFlow#FCMComposite_1_7,out,RuntimeMonitoringFlow#FCMComposite_1_3,in</attrs><attrs>messageflow.nodeconnection.last=5</attrs><attrs>messageflow.nodetypes=ComIbmMQInputNode,ComIbmJavaComputeNode,ComIbmWSRequestNode,ComIbmTimeoutNotificationNode</attrs><attrs>messageflow.queuenames=RUNTIMEMONITORING.TRIGGER.ZH</attrs><attrs>messageflow.usertrace=none</attrs><attrs>name=RuntimeMonitoringFlow</attrs><attrs>object.runstate=running</attrs><attrs>parent.type=Application</attrs><attrs>parent.uuid=370f1454-5801-0000-0080-8af24ab77390</attrs><attrs>type=MessageProcessingNodeType</attrs><attrs>uuid=11101454-5801-0000-0080-8af24ab77390</attrs></_source><_id>IB9NODE-RuntimeMonitoring-elasticSearchBulk-RuntimeMonitoringFlow</_id><sort>1479309160438</sort><_score>null</_score></hits><total>42</total><max_score>null</max_score></hits><took>2</took><timed_out>false</timed_out></root>";
		String test = "<root><_shards><total>3</total><failed>0</failed><successful>3</successful></_shards><hits><hits><_index>cfg_idx</_index><_type>broker</_type><_source><date>2016-11-16 15:57:17.952</date><app>elasticSearchBulk</app><running>true</running><eg>RuntimeMonitoring</eg><modified>false</modified><type>flow</type><broker>IB9NODE</broker><flow>RuntimeMonitoringFlow</flow><group>zahoorapp</group><attrs>DeploytimePropertyFolder/barFileName=C:/Temp/gradle_build/elasticSearchBulk.zahoor/bar.target/elasticSearchBulk.bar</attrs><attrs>DeploytimePropertyFolder/deployTime=2016-11-11 16:49:10.555 +0100</attrs><attrs>DeploytimePropertyFolder/modifyTime=2016-11-11 16:49:08.000 +0100</attrs><attrs>MessageFlowRuntimeProperty/This/additionalInstances=0</attrs><attrs>MessageFlowRuntimeProperty/This/commitCount=1</attrs><attrs>MessageFlowRuntimeProperty/This/commitInterval=0</attrs><attrs>MessageFlowRuntimeProperty/This/coordinatedTransaction=no</attrs><attrs>MessageFlowRuntimeProperty/This/label=RuntimeMonitoringFlow</attrs><attrs>MessageFlowRuntimeProperty/This/runMode=running</attrs><attrs>MessageFlowRuntimeProperty/This/startMode=Maintained</attrs><attrs>MessageFlowRuntimeProperty/This/traceLevel=none</attrs><attrs>MessageFlowRuntimeProperty/This/userTraceLevel=none</attrs><attrs>MessageFlowRuntimeProperty/This/uuid=11101454-5801-0000-0080-8af24ab77390</attrs><attrs>deployed.as.source=true</attrs><attrs>lastupdate.user=rockit3lp</attrs><attrs>messageflow.additionalinstances=0</attrs><attrs>messageflow.commitcount=1</attrs><attrs>messageflow.commitinterval=0</attrs><attrs>messageflow.coordinatedtransaction=no</attrs><attrs>messageflow.deploytime=2016-11-11 16:49:10.555 +0100</attrs><attrs>messageflow.keywords=$MQSIBAR=C:/Temp/gradle_build/elasticSearchBulk.zahoor/bar.target/elasticSearchBulk.barMQSI$</attrs><attrs>messageflow.modifytime=2016-11-11 16:49:08.000 +0100</attrs><attrs>messageflow.node.1=&lt;ComIbmMQInputNode uuid=RuntimeMonitoringFlow#FCMComposite_1_1 userTraceLevel=none traceLevel=none label=MQ Input messageDomainProperty= messageSetProperty= messageTypeProperty= messageFormatProperty= messageEncodingProperty=0 messageCodedCharSetIdProperty=0 topicProperty= validate=no rootParserClassName=MQROOT additionalInstances=0 queueName=RUNTIMEMONITORING.TRIGGER.ZH transactionMode=yes orderMode=default logicalOrder=yes allMsgsAvailable=no matchMsgId=no matchCorrelId=no browse=no resetBrowseTimeout=-1 convert=no convertEncoding=546 convertCodedCharSetId=0 commitByMessageGroup=no tempDynamicQueue=no/&gt;</attrs><attrs>messageflow.node.2=&lt;ComIbmJavaComputeNode uuid=RuntimeMonitoringFlow#FCMComposite_1_2 userTraceLevel=none traceLevel=none label=BuildPerformanceStat javaClass=RuntimeMonitoringFlow_BuildPerformanceStat javaClassLoader=/&gt;</attrs><attrs>messageflow.node.3=&lt;ComIbmJavaComputeNode uuid=RuntimeMonitoringFlow#FCMComposite_1_3 userTraceLevel=none traceLevel=none label=BuildBulk javaClass=RuntimeConfigReader_BuildBulk javaClassLoader=/&gt;</attrs><attrs>messageflow.node.4=&lt;ComIbmWSRequestNode uuid=RuntimeMonitoringFlow#FCMComposite_1_4 userTraceLevel=none traceLevel=none label=SavePerformance messageDomainProperty= messageSetProperty= messageTypeProperty= messageFormatProperty= messageEncodingProperty=0 messageCodedCharSetIdProperty=0 topicProperty= URLSpecifier=http://linux-wmnh:9200/log_idx/runtime/ timeoutForServer=120 useWholeInputMsgAsRequest=no requestMsgLocationInTree=InputRoot.JSON.Data replaceInputMsgWithWSResponse=yes responseMsgLocationInTree=OutputRoot generateDefaultHttpHeaders=yes replaceInputMsgWithHTTPError=yes errorMsgLocationInTree=OutputRoot httpProxyLocation= followRedirection=no/&gt;</attrs><attrs>messageflow.node.5=&lt;ComIbmWSRequestNode uuid=RuntimeMonitoringFlow#FCMComposite_1_5 userTraceLevel=none traceLevel=none label=SaveBulk messageDomainProperty=BLOB messageSetProperty= messageTypeProperty= messageFormatProperty= messageEncodingProperty=0 messageCodedCharSetIdProperty=0 topicProperty= URLSpecifier=http://linux-wmnh:9200/cfg_idx/broker/_bulk timeoutForServer=120 useWholeInputMsgAsRequest=no requestMsgLocationInTree=InputRoot.BLOB replaceInputMsgWithWSResponse=yes responseMsgLocationInTree=OutputRoot generateDefaultHttpHeaders=yes replaceInputMsgWithHTTPError=yes errorMsgLocationInTree=OutputRoot httpProxyLocation= followRedirection=no protocol=TLS httpVersion=1.1 acceptCompressedResponses=yes/&gt;</attrs><attrs>messageflow.node.6=&lt;ComIbmTimeoutNotificationNode uuid=RuntimeMonitoringFlow#FCMComposite_1_7 userTraceLevel=none traceLevel=none label=Timeout Notification uniqueIdentifier=TriggerMon transactionMode=automatic operationMode=automatic timeoutInterval=600/&gt;</attrs><attrs>messageflow.node.last=6</attrs><attrs>messageflow.nodeconnection.1=RuntimeMonitoringFlow#FCMComposite_1_1,out,RuntimeMonitoringFlow#FCMComposite_1_3,in</attrs><attrs>messageflow.nodeconnection.2=RuntimeMonitoringFlow#FCMComposite_1_2,out,RuntimeMonitoringFlow#FCMComposite_1_4,in</attrs><attrs>messageflow.nodeconnection.3=RuntimeMonitoringFlow#FCMComposite_1_3,out,RuntimeMonitoringFlow#FCMComposite_1_5,in</attrs><attrs>messageflow.nodeconnection.4=RuntimeMonitoringFlow#FCMComposite_1_5,out,RuntimeMonitoringFlow#FCMComposite_1_2,in</attrs><attrs>messageflow.nodeconnection.5=RuntimeMonitoringFlow#FCMComposite_1_7,out,RuntimeMonitoringFlow#FCMComposite_1_3,in</attrs><attrs>messageflow.nodeconnection.last=5</attrs><attrs>messageflow.nodetypes=ComIbmMQInputNode,ComIbmJavaComputeNode,ComIbmWSRequestNode,ComIbmTimeoutNotificationNode</attrs><attrs>messageflow.queuenames=RUNTIMEMONITORING.TRIGGER.ZH</attrs><attrs>messageflow.usertrace=none</attrs><attrs>name=RuntimeMonitoringFlow</attrs><attrs>object.runstate=running</attrs><attrs>parent.type=Application</attrs><attrs>parent.uuid=370f1454-5801-0000-0080-8af24ab77390</attrs><attrs>type=MessageProcessingNodeType</attrs><attrs>uuid=11101454-5801-0000-0080-8af24ab77390</attrs></_source><_id>IB9NODE-RuntimeMonitoring-elasticSearchBulk-RuntimeMonitoringFlow</_id><sort>1479311837952</sort><_score>null</_score></hits><total>42</total><max_score>null</max_score></hits><took>2</took><timed_out>false</timed_out></root>";
		
		xmlFileAssertion.ignore(ImmutableList.of("hits"))
				.compare(Input.fromString(control), Input.fromString(test))
				.withNodeMatcher(ElementSelectors.byNameAndText).checkForSimilar().build();
	}

	
	@Test
	public void checkIgnoreAttributes() {
		String control = "<root><took>2</took><timed_out ignore=\"fdfdf\">false</timed_out></root>";
		String test = "<root><took>2</took><timed_out ignore=\"xdf\">false</timed_out></root>";
		
		xmlFileAssertion.ignore(ImmutableList.of("hits")).ignoreAttrs(ImmutableList.of("ignore"))
				.compare(Input.fromString(control), Input.fromString(test))
				.withNodeMatcher(ElementSelectors.byNameAndText).checkForSimilar().build();
	}
	
	
	@Test(expected = AssertionError.class)
	public void checkIgnoreAttributesFail() {
		String control = "<root><took>2</took><timed_out ignore=\"fdfdf\">false</timed_out></root>";
		String test = "<root><took>2</took><timed_out ignore=\"xdf\">false</timed_out></root>";
		
		xmlFileAssertion.ignore(ImmutableList.of("hits")).ignoreAttrs(ImmutableList.of("ignore2"))
				.compare(Input.fromString(control), Input.fromString(test))
				.withNodeMatcher(ElementSelectors.byNameAndText).checkForSimilar().build();
	}

	
	@Test
	public void checkMultilineEqualsToOnelineFromString() {
		String control = "<root>" + System.lineSeparator() + "<shards></shards></root>";
		String test = "<root><shards></shards></root>";
		
		xmlFileAssertion.ignore(ImmutableList.of("dateTime", "create_ms", "total_ms", "elastic_ms", "_id", "sort", "total","took"))
				.compare(Input.fromString(control), Input.fromString(test))
				.withNodeMatcher(ElementSelectors.byNameAndText).checkForSimilar().build();
	}
	
	@Test
	public void checkMultilineEqualsToOnelineFromFile() throws URISyntaxException {
		
		Path recSrcSingle = Paths.get(ClassLoader.getSystemResource("assertions/XMLFileAssertion/mutiline/single.xml").toURI());
		File single = new File(recSrcSingle.toString());
		assertTrue(single.exists());
		
		Path recSrcMulti = Paths.get(ClassLoader.getSystemResource("assertions/XMLFileAssertion/mutiline/multi.xml").toURI());
		File multi = new File(recSrcMulti.toString());
		assertTrue(multi.exists());
		
		xmlFileAssertion.ignore(ImmutableList.of("dateTime", "create_ms", "total_ms", "elastic_ms", "_id", "sort", "total","took"))
				.compare(Input.fromFile(multi), Input.fromFile(single))
				.withNodeMatcher(ElementSelectors.byNameAndText).checkForIdentical().build();
	}

}
