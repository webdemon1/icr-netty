package com.alibaba.demon.config;

import com.alibaba.demon.handler.TcpClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class NettyConfig {

    @Value("${tcp.port}")
    private int tcpPort;

    @Bean
    public Bootstrap bootstrap(TcpClientHandler tcpClientHandler) {

        return new Bootstrap().group(workerGroup())
                .remoteAddress(remoteAddress())
                .channel(NioSocketChannel.class)
                .handler(tcpClientHandler);
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public InetSocketAddress remoteAddress() {
        return new InetSocketAddress("127.0.0.1", tcpPort);
    }


}
