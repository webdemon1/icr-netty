package com.alibaba.demon.handler;

import com.alibaba.demon.ws.ConnectionListener;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;

/**
 *
 * @author: Demon
 * @create: 2019-04-10
 **/
public class WsClientHandler extends SimpleChannelInboundHandler<Object> {

    private ConnectionListener listener;

    private WebSocketClientHandshaker handShaker;

    //TODO 如何使用
    private ChannelPromise handshakeFuture;

    public void setListener(ConnectionListener listener) {
        this.listener = listener;
    }

    public void setHandShaker(WebSocketClientHandshaker handShaker) {
        this.handShaker = handShaker;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {

    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }
}
