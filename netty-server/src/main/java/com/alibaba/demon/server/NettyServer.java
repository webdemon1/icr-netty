package com.alibaba.demon.server;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Objects;

@Component
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
            if (Objects.nonNull(channelFuture) && channelFuture.isSuccess()) {
                log.info("********  NettyServer start success at tcpPort:{} *****", tcpPort.getPort());
            } else {
                log.error("@NettyServer iint error. channelFuture:{}", JSON.toJSONString(channelFuture));
            }
        } catch (Exception ex) {
            log.error("@NettyServer.start exception", ex);
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
