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
    public void start() {
        try {
            channelFuture = bootstrap.connect().sync();
            if (channelFuture.isSuccess()) {
                log.info("******** NettyClient init success *****");
            }
        }
        catch (Exception ex) {
            log.error("@NettyClient.start exception", ex);
        }
    }

    @PreDestroy
    public void stop() {
        try {
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            log.error("@NettyServer.stop exception", ex);
        }
    }

}
