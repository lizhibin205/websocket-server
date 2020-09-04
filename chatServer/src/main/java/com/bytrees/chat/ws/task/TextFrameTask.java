package com.bytrees.chat.ws.task;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextFrameTask implements Runnable {
	private ChannelHandlerContext ctx;
	private String message;

	public TextFrameTask(ChannelHandlerContext ctx, String message) {
		this.ctx = ctx;
		this.message = message;
	}

	@Override
	public void run() {
		StringBuilder strBuilder = new StringBuilder("Server Received: ");
		strBuilder.append(message);
		ctx.channel().writeAndFlush(new TextWebSocketFrame(strBuilder.toString()));
	}
}
