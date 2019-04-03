package com.alibaba.demon.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TcpClientHandler extends ChannelInitializer<SocketChannel> {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void initChannel(SocketChannel ch) {
         ch.pipeline().addLast(applicationContext.getBean(DataSendHandler.class));
    }

}
