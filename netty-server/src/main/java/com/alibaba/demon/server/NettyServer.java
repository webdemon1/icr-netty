package com.alibaba.demon.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Component("nettyServer")
@Slf4j
public class NettyServer {
    @Resource
    private ServerBootstrap serverBootstrap;

    @Resource
    private InetSocketAddress tcpPort;

    private ChannelFuture channelFuture;

    @PostConstruct
    public void start() {
        try {
            channelFuture = serverBootstrap.bind(tcpPort).sync();
            log.info("********  NettyServer start success at tcpPort:{} *****", tcpPort.getPort());
        }
        catch (Exception ex) {
            log.error("@NettyServer.start exception", ex);
        }
    }

    @PreDestroy
    public void stop() {
        try {
            channelFuture.channel().closeFuture().sync();
        }
        catch (Exception ex) {
            log.error("@NettyServer.stop exception", ex);
        }
    }


}
