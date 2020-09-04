package com.bytrees.chat.ws.message;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.TextFormat;
import com.google.protobuf.TextFormat.ParseException;

public class MessageTest {
	private static final Logger logger = LoggerFactory.getLogger(MessageTest.class);

	@Test
	public void messageTest() throws ParseException {
		String str = "你好吗";
		WebSocketMessageIdl.WebSocketMessage message = WebSocketMessageIdl.WebSocketMessage.newBuilder()
				.setMessageTypeValue(1)
				.setMessageContent(str)
				.build();
		logger.info("message: {}", message);

		String encodeStr = "messageType: STRING\r\n" + 
				"messageContent: \"\\347\\262\\276\\351\\200\\211300+\\346\\254\\276\\345\\223\\201\\350\\264\\250\\345\\245\\275\\347\\211\\251\\357\\274\\214\\344\\274\\230\\346\\203\\240\\344\\275\\216\\350\\207\\2632\\346\\212\\230\\350\\265\\267\"";
		WebSocketMessageIdl.WebSocketMessage message2 = TextFormat.parse(encodeStr, 
				WebSocketMessageIdl.WebSocketMessage.class);
		logger.info("message.messageContent: {}", message2.getMessageContent());
	}
}
