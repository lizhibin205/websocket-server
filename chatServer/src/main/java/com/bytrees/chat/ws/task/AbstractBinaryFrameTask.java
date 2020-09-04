package com.bytrees.chat.ws.task;

import java.util.Date;

import com.bytrees.chat.ws.message.WebSocketMessageIdl;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public abstract class AbstractBinaryFrameTask implements Runnable {
	protected ChannelHandlerContext ctx;
	protected WebSocketMessageIdl.WebSocketMessage message;

	public AbstractBinaryFrameTask(ChannelHandlerContext ctx, WebSocketMessageIdl.WebSocketMessage message) {
		this.ctx = ctx;
		this.message = message;
	}

	public void run() {}

	/**
	 * 把返回内容包装成Binary帧
	 */
	protected BinaryWebSocketFrame stringToBinaryFrame(String str) {
		WebSocketMessageIdl.WebSocketMessage.Builder builder =  WebSocketMessageIdl.WebSocketMessage.newBuilder();
		builder.setClientId(0L);
		builder.setMessageType(WebSocketMessageIdl.MessageType.STRING);
		builder.setMessageContent(str);
		builder.setMessageTimestamp(new Date().getTime());
		return new BinaryWebSocketFrame(Unpooled.copiedBuffer(builder.build().toByteArray()));
	}
}
