package com.rockit.common.blackboxtester.connector.impl;

import io.github.rockitconsulting.test.rockitizer.payload.model.MqPayload;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ibm.mq.MQMessage;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.MQRFH2;

public class MQPayloadBuilder {
	public static final Logger LOGGER = Logger.getLogger(MQPayloadBuilder.class.getName());

	public static MQMessage newMqMessageFromXMLString(byte[] message) throws IOException {

		final MQMessage mQMsg = new MQMessage();

		String msg = CLEAN_INVALID_CHARS.matcher(new String(message)).replaceAll("");
		msg = msg.replace("\r", "").replace("\n", "").replaceAll(">\\s+<", "><");

		MqPayload p = newMqPayloadFromXMLString(msg);

		if (p != null) {
			mQMsg.characterSet = p.getHeader().getCodedCharSetId();
			mQMsg.messageType = p.getHeader().getMsgType();
			mQMsg.messageId = Hex.decode(p.getHeader().getMsgId().getBytes());
			mQMsg.correlationId = Hex.decode(p.getHeader().getCorrelId().getBytes()); 
			mQMsg.groupId = Hex.decode(p.getHeader().getGroupId().getBytes());
			mQMsg.userId = p.getHeader().getUserId();
			mQMsg.expiry = p.getHeader().getExpiry();
			mQMsg.messageFlags = p.getHeader().getMessageFlags();
			if(Hex.decode(p.getHeader().getGroupId().getBytes()).length>0) {
				mQMsg.messageSequenceNumber = p.getHeader().getMessageSequenceNumber();
			}
			mQMsg.replyToQueueName = p.getHeader().getReplyToQ().trim();
			mQMsg.replyToQueueManagerName = p.getHeader().getReplyToQMgr().trim();

			if (p.getHeader().getRfh2Header() != null && !p.getHeader().getRfh2Header().isEmpty()) {
				MQRFH2 rfh2 = new MQRFH2();
				rfh2.setFolderStrings(p.getHeader().getRfh2Header().stream().toArray(n -> new String[n]));
				rfh2.write(mQMsg);

				mQMsg.format = CMQC.MQFMT_RF_HEADER_2;
			} else {
				mQMsg.format = p.getHeader().getMsgFormat();
			}
		} else {
			mQMsg.format = "MQSTR";
			mQMsg.characterSet = 1208;
			mQMsg.messageType = 8;
			mQMsg.expiry = -1;
		}
		String codepage = getcodePage(mQMsg);

		
		mQMsg.write( p != null ? ( 
				p.getBody64enc()!=null? Base64.getDecoder().decode(p.getBody64enc() ) :  p.getBody().getBytes(codepage) 
						) : msg.getBytes());

		return mQMsg;
	}

	public static MqPayload newMqPayloadFromXMLString(String msg) {
		MqPayload p = null;
		try {
			p = new MqPayload();
			JAXBContext context = JAXBContext.newInstance(MqPayload.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(msg);
			p = (MqPayload) unmarshaller.unmarshal(reader);

			if (p.getHeader().getRfh2Header() != null && !p.getHeader().getRfh2Header().isEmpty()) {
				p.getHeader().getRfh2Header().clear();
			}

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(msg)));

			XPath xPath = XPathFactory.newInstance().newXPath();
			XPathExpression exprHeader = xPath.compile("/mqPayload/header/rfh2Headers/rfh2Header");
			NodeList nodesHeader = (NodeList) exprHeader.evaluate(doc, XPathConstants.NODESET);

			for (int i = 0; i < nodesHeader.getLength(); i++) {
				p.getHeader().getRfh2Header().add(innerXml(nodesHeader.item(i)));
			}

			XPathExpression exprBody = xPath.compile("/mqPayload/body");
			NodeList nodesBody = (NodeList) exprBody.evaluate(doc, XPathConstants.NODESET);

			for (int i = 0; i < nodesBody.getLength(); i++) {
				p.setBody(innerXml(nodesBody.item(i)));
			}

		} catch (JAXBException | SAXException | XPathExpressionException | IOException | ParserConfigurationException e) {
			p = null;
			LOGGER.warn("no mqPayload envelope found, continue processing legacy message " + e.getMessage());
		}
		return p;
	}


	
	public static MqPayload newPayload(MQMessage msg) throws MQDataException, IOException {
		MqPayload mqPayload = new MqPayload();
		if (msg.format.contains("MQHRF2")) {
			msg.seek(0);
			MQRFH2 rfh2 = new MQRFH2(msg);
			if (rfh2.getFolders().length > 0) {
				mqPayload.getHeader().setRfh2Header(Arrays.asList(rfh2.getFolderStrings()));
			}
		}
		mqPayload.getHeader().setCodedCharSetId(msg.characterSet);
		mqPayload.getHeader().setMsgType(msg.messageType);
		mqPayload.getHeader().setMsgFormat(msg.format.trim());		
		mqPayload.getHeader().setCorrelId( new String(Hex.encode(msg.correlationId)) ); 
		mqPayload.getHeader().setMsgId(new String(Hex.encode(msg.messageId))); 
		mqPayload.getHeader().setUserId(msg.userId);
		mqPayload.getHeader().setGroupId(new String(Hex.encode(msg.groupId)));
		mqPayload.getHeader().setMessageSequenceNumber(msg.messageSequenceNumber);
		mqPayload.getHeader().setMessageFlags(msg.messageFlags);
		mqPayload.getHeader().setReplyToQ(msg.replyToQueueName.trim());
		mqPayload.getHeader().setReplyToQMgr(msg.replyToQueueManagerName.trim());
		
		byte data[] = new byte[msg.getDataLength()];
		msg.readFully(data);
  	    
		String codepage = getcodePage(msg);
		mqPayload.setBody(new String (data, codepage ).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "").trim());
		mqPayload.setBody64enc( Base64.getEncoder().encodeToString(data).trim() );
		
		
		return mqPayload;
	}

	private static String getcodePage(MQMessage msg) {
		String codepage;
		if(msg.characterSet == 1208) {
  	    	codepage = StandardCharsets.UTF_8.toString();
  	    } else {
  	    	codepage = String.valueOf(msg.characterSet);
  	    }
		return codepage;
	}

	private static final Pattern CLEAN_INVALID_CHARS = Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+");

	public static String toXmlString(MqPayload payload) {
		try (StringWriter writer = new StringWriter()) {
			JAXBContext context = JAXBContext.newInstance(MqPayload.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.marshal(payload, writer);
			String replaceAll = StringEscapeUtils
					.unescapeXml(writer.toString().replace("&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;", ""));
			return CLEAN_INVALID_CHARS.matcher(replaceAll).replaceAll("");
		} catch (final Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
	}

	private static String innerXml(Node node) {
		DOMImplementationLS lsImpl = (DOMImplementationLS) node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
		LSSerializer lsSerializer = lsImpl.createLSSerializer();
		lsSerializer.getDomConfig().setParameter("xml-declaration", false);
		NodeList childNodes = node.getChildNodes();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < childNodes.getLength(); i++) {
			sb.append(lsSerializer.writeToString(childNodes.item(i)));
		}
		return sb.toString();
	}

}
