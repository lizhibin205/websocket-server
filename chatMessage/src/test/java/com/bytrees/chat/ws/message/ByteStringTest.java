package com.bytrees.chat.ws.message;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;



public class ByteStringTest {
	private static final Logger logger = LoggerFactory.getLogger(ByteStringTest.class);

	@Test
	public void byteStringTest() {
		String str = "hello world!";
		ByteString byteString = ByteString.copyFromUtf8(str);
		logger.info("byteString:{}", byteString);
		Assert.assertEquals(str, byteString.toStringUtf8());
	}
}
