package com.bytrees.chat.ws;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bytrees.chat.ws.task.TaskExecutors;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class WebSocketServer {
	private static final int SERVER_PORT = 9100;
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	private static final int PARENT_GROUP_THREAD_NUMBER = 2;
	private static final int CHILD_GROUP_THREAD_NUMBER = 5;

	public static void main(String[] args) {
		logger.info("Starting web socket server...");
		new WebSocketServer().run();
		logger.info("bye");
	}

	/**
	 * WebSocket Server启动
	 * @throws InterruptedException 
	 */
	public void run() {
		final EventLoopGroup group = new NioEventLoopGroup(PARENT_GROUP_THREAD_NUMBER, 
				new DefaultThreadFactory("Chat-Server-Boss", true));
		final EventLoopGroup childGroup = new NioEventLoopGroup(CHILD_GROUP_THREAD_NUMBER, 
				new DefaultThreadFactory("Chat-Server-Worker", true));
		//定义聊天的频道组
		final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
		//定义业务线程池
		TaskExecutors taskExecutors = new TaskExecutors(2, 5, 60L, TimeUnit.SECONDS);

		ServerBootstrap boot = new ServerBootstrap();
		boot.group(group, childGroup)
		.channel(NioServerSocketChannel.class)
		.childHandler(new ChatServerInitializer(channelGroup, taskExecutors));
		//绑定服务器端口
		ChannelFuture future = boot.bind(new InetSocketAddress(SERVER_PORT));
		//如果使用sync则要处理InterruptedException
		future.syncUninterruptibly();
		//结束channel
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (future.channel() != null) {
					future.channel().close();
				}
				channelGroup.close();
				group.shutdownGracefully();
				childGroup.shutdownGracefully();
			}
		});
		//启动服务，阻塞主进程
		logger.info("begin...");
		future.channel().closeFuture().syncUninterruptibly();
	}
}
