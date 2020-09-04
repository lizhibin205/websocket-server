package com.bytrees.chat.ws.room;

public class SimpleMessage {
	private static final String WELCOME = "连接服务器成功";
	private SimpleMessage() {}

	/**
	 * 服务器欢迎语
	 * @return
	 */
	public static String welcome() {
		return WELCOME;
	}
}
