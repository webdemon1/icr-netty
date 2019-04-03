package com.alibaba.demon.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
@Slf4j
public class NettyClient {

    @Resource
    private Bootstrap bootstrap;

    private ChannelFuture channelFuture;

    @PostConstruct
    public void start() throws InterruptedException {
        channelFuture = bootstrap.connect().sync();
        if (channelFuture.isSuccess()) {
            log.info("******** NettyClient init success *****");
        }
        else {
            log.error("NettyClient init fail");
        }
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        channelFuture.channel().closeFuture().sync();

    }

}
