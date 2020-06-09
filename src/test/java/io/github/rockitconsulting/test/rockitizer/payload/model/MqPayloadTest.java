package io.github.rockitconsulting.test.rockitizer.payload.model;

import java.nio.file.Files;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.rockit.common.blackboxtester.connector.impl.MQPayloadBuilder;

public class MqPayloadTest {

	private String msg = "<mqPayload>"
			+ "<header>"
			+ "<codedCharSetId>1208</codedCharSetId>"
			+ "<correlId></correlId>"
			+ "<expiry>-1</expiry>"
			+ "<msgFormat>MQHRF2  </msgFormat>"			
			+ "<msgId>AMQ QM1</msgId>"
			+ "<msgType>8</msgType>"
			+ "<replyToQ></replyToQ>"
			+ "<replyToQMgr></replyToQMgr>"
			+ "\n\t<rfh2Headers>"
			+ "\n\t\t<rfh2Header><mcd><Msd>xmlnsc</Msd><Set>IT1V2F8002001</Set><Type>i0101_z_bapi_fi_confirmacion_pago</Type><Fmt>CwXML</Fmt></mcd></rfh2Header>"
			+ "<rfh2Header><jms><Dst>queue:///SAPCONNECTOR/REQUESTQUEUE</Dst><Rto>queue:///SAPCONNECTOR/RESPONSEQUEUE</Rto></jms></rfh2Header>"
			+ "<rfh2Header><usr><netSvc dt=\"string\">SNF</netSvc><ReceivedCommunicationId dt=\"string\">[yyy].INC.20200423131527.123287115</ReceivedCommunicationId>"
			+ "<SystemUserReference dt=\"string\">SUR1-CBAAPLA0XXX</SystemUserReference><messageType dt=\"string\">camt.998.001.03</messageType><BusinessSignDN dt=\"string\">CN=User1-CBAASAA0XXX,OU=Test Certificate,O=Deutsche Bundesbank,C=de</BusinessSignDN><ProcessType dt=\"string\">0</ProcessType>"
			+ "<EntryTimestamp dt=\"string\">2020-04-23-13.15.27.000000</EntryTimestamp><PartyTechnicalAddress dt=\"string\">ou=CBAASAA0CLM,ou=tssptest,o=markdeff,o=swift</PartyTechnicalAddress>"
			+ "<TechnicalServiceIdentification dt=\"string\">SWIFT.MSGSNF</TechnicalServiceIdentification><inboundQueue dt=\"string\">ESMIG</inboundQueue></usr></rfh2Header>"
			+ "</rfh2Headers>" + "</header>" + "\n\t\t<body>" + "<Input>" + "<Node1>content 1</Node1>" + "<Node2>content 2</Node2>" + "</Input></body>"
			+ "</mqPayload>";

	@Test
	public void testMsgWriteEscaped()  {


			MqPayload payload = MQPayloadBuilder.newMqPayloadFromXMLString( msg);
			String res = MQPayloadBuilder.toXmlString(payload);
			
			assertEquals( msg.replace("\r", "").replace("\n", "").replaceAll(">\\s+<", "><"), 
					res.replace("\r", "").replace("\n", "").replaceAll(">\\s+<", "><") 
			);

			


	}

}
