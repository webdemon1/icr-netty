package com.alibaba.demon.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author: Demon
 * @create: 2019-04-14
 **/
public class WsHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) {
        final String x = textWebSocketFrame.text();
        final String[] y = x.split(":");
        ctx.writeAndFlush(new TextWebSocketFrame(y[0] + ":" + y[1].toUpperCase()));
    }
}
