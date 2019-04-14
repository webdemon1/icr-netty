package com.alibaba.demon.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Demon
 * @create: 2019-04-14
 **/
@Slf4j
public class WsHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) {
        log.info("@WsHandler.text:{}", textWebSocketFrame.text());
        ctx.writeAndFlush(new TextWebSocketFrame("hello client"));
    }
}
