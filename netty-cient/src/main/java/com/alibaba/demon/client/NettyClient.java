package com.alibaba.demon.client;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Objects;

@Component("nettyClient")
@Slf4j
public class NettyClient {

    @Resource
    private Bootstrap bootstrap;

    @Resource
    private InetSocketAddress inetSocketAddress;

    private ChannelFuture channelFuture;

    @PostConstruct
    public void start() {
        try {
            channelFuture = bootstrap.connect(inetSocketAddress).sync();
            if (Objects.nonNull(channelFuture) && channelFuture.isSuccess()) {
                log.info("******** NettyClient init success at tcpPort:{} *****", inetSocketAddress.getPort());
            } else {
                log.error("@NettyClient init error. channelFuture:{}", JSON.toJSONString(channelFuture));
            }
        } catch (Exception ex) {
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
