package com.bytrees.chat.ws;

import com.bytrees.chat.ws.channelhandler.BinaryWebSocketFrameHandler;
import com.bytrees.chat.ws.channelhandler.HttpRequestHandler;
import com.bytrees.chat.ws.channelhandler.PingWebSocketFrameHandler;
import com.bytrees.chat.ws.channelhandler.TextWebSocketFrameHandler;
import com.bytrees.chat.ws.task.TaskExecutors;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
	private final ChannelGroup group;
	private final TaskExecutors taskExecutors;

	public ChatServerInitializer(ChannelGroup group, TaskExecutors taskExecutors) {
		this.group = group;
		this.taskExecutors = taskExecutors;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		//请求和应答HTTP消息
		pipeline.addLast("http-codec", new HttpServerCodec());
		//将HTTP消息的多个部分组合成一条完整的消息
		pipeline.addLast("aggregator", new HttpObjectAggregator(64 * 1024));
		//支持浏览器和服务端进行WebSocket通信
		pipeline.addLast("http-chunked", new ChunkedWriteHandler());
		//处理HTTP请求
		pipeline.addLast(new HttpRequestHandler("/ws"));
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		//处理WebSocket文本帧
		pipeline.addLast(new TextWebSocketFrameHandler(group, taskExecutors));
		//处理二进制帧
		pipeline.addLast(new BinaryWebSocketFrameHandler(group, taskExecutors));
		//处理WebSocket心跳
		pipeline.addLast(new PingWebSocketFrameHandler());
	}
}
