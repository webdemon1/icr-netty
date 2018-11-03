package com.alibaba.demon.config;

import com.alibaba.demon.handler.TcpChannelInitializer;
import com.google.common.collect.Maps;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.Map;

@Configuration
public class NettyServerConfig {

    @Value("${boss.thread.count}")
    private int bossCount;

    @Value("${worker.thread.count}")
    private int workerCount;

    @Value("${tcp.port}")
    private int tcpPort;

    @Value("${icr.keepalive}")
    private boolean keepAlive;

    @Value("${icr.backlog}")
    private int backlog;

    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap(NioEventLoopGroup bossGroup, NioEventLoopGroup workerGroup,
                                     TcpChannelInitializer tcpChannelInitializer) {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(tcpChannelInitializer);
        Map<ChannelOption, Object> tcpOptionMap = tcpChannelOptions();
        tcpOptionMap.forEach(b::option);
        return b;
    }

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(tcpPort);
    }

    @Bean(name = "tcpChannelOptions")
    public Map<ChannelOption, Object> tcpChannelOptions() {
        Map<ChannelOption, Object> options = Maps.newHashMap();
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        options.put(ChannelOption.SO_BACKLOG, backlog);
        return options;
    }

}
