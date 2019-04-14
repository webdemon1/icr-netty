package com.alibaba.demon;

import com.alibaba.demon.handler.WsServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: Demon
 * @create: 2019-04-14
 **/
@Slf4j
public class WsServer {

    public static void main(final String[] args) throws Exception {
        new WsServer().run();
    }

    private Channel ch;

    private void run() throws Exception {
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        final EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            final ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WsServerInitializer());

            ch = b.bind("localhost", 8182).sync().channel();
            log.info("---- ws server start success ----");
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
