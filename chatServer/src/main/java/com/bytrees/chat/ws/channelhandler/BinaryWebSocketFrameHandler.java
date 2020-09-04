package com.bytrees.chat.ws.channelhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.ws.message.WebSocketMessageIdl;
import com.bytrees.chat.ws.task.BinaryFrameTask;
import com.bytrees.chat.ws.task.TaskExecutors;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class BinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
	private static final Logger logger = LoggerFactory.getLogger(BinaryWebSocketFrameHandler.class);
	private final ChannelGroup group;
	private final TaskExecutors taskExecutors;

	public BinaryWebSocketFrameHandler(ChannelGroup group, TaskExecutors taskExecutors) {
		this.group = group;
		this.taskExecutors = taskExecutors;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
		ByteBuf byteBuf = msg.content();
		WebSocketMessageIdl.WebSocketMessage message = WebSocketMessageIdl.WebSocketMessage.parseFrom(byteBuf.nioBuffer());
		logger.info("client on channelRead0.Thread:{},  Client:{}, Message:{}", Thread.currentThread().getName(), 
			ctx.channel(), message.getMessageContent());

		//使用业务线程池执行任务
		taskExecutors.execute(new BinaryFrameTask(ctx, message));
	}

	/**
	 * 返回ChannelGroup
	 * @return
	 */
	public ChannelGroup getGroup() {
		return group;
	}
}
