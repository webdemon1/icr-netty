package com.alibaba.demon.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.InputStream;

public class AudioSendHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
     /*   InputStream ips = AudioSendHandler.class.getResourceAsStream("/game.pcm");
        byte[] data = new byte[ips.available()];
        ips.read(data);
        ips.close();

        ctx.channel().writeAndFlush(data);*/
    }


}
