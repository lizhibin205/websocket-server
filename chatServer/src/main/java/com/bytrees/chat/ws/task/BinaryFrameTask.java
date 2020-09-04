package com.bytrees.chat.ws.task;

import com.bytrees.chat.ws.message.WebSocketMessageIdl.WebSocketMessage;

import io.netty.channel.ChannelHandlerContext;

public class BinaryFrameTask extends AbstractBinaryFrameTask {
	public BinaryFrameTask(ChannelHandlerContext ctx, WebSocketMessage message) {
		super(ctx, message);
	}

	@Override
	public void run() {
		StringBuilder strBuilder = new StringBuilder("Server Received: ");
		strBuilder.append(message.getMessageContent());
		ctx.writeAndFlush(stringToBinaryFrame(strBuilder.toString()));
	}
}
