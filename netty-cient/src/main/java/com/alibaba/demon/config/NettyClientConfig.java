package com.alibaba.demon.config;

import com.alibaba.demon.handler.TcpClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class NettyClientConfig {

    @Value("${worker.thread.count}")
    private int workerCount;

    @Value("${tcp.port}")
    private int tcpPort;

    @Value("${icr.keepalive}")
    private boolean keepAlive;

    @Value("${icr.backlog}")
    private int backlog;

    @Bean(name = "bootstrap")
    public Bootstrap bootstrap(NioEventLoopGroup workerGroup,
                               TcpClientHandler tcpClientHandler) {
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline().addLast(tcpClientHandler);
            }
        });
        return b;
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(tcpPort);
    }


}
