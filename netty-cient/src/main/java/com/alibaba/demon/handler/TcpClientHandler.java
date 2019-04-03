package com.alibaba.demon.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TcpClientHandler extends ChannelInitializer<SocketChannel> {

    @Resource
    private DataSendHandler dataSendHandler;

    @Override
    protected void initChannel(SocketChannel ch) {
         ch.pipeline().addLast(dataSendHandler);
    }

}
