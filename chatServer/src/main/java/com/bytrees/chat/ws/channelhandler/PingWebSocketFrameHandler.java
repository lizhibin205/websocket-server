package com.bytrees.chat.ws.channelhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;

public class PingWebSocketFrameHandler extends SimpleChannelInboundHandler<PingWebSocketFrame> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PingWebSocketFrame msg) throws Exception {
		ctx.channel().write(new PongWebSocketFrame());
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
