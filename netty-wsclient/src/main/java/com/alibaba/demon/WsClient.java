package com.alibaba.demon;

import com.alibaba.demon.handler.WsClientHandler;
import com.alibaba.demon.ws.Connection;
import com.alibaba.demon.ws.ConnectionListener;
import com.alibaba.demon.ws.WsConnection;
import com.alibaba.demon.ws.WsListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * @author: Demon
 * @create: 2019-04-10
 **/
@Slf4j
public class WsClient {

    public static void main(String[] args) throws Exception {
        WsListener wsListener = new WsListener();
        Connection connect = new WsClient("wss://localhost:8443/ws").connect("Taylor", wsListener, 100);
        connect.sendText("hello server");
    }

    private URI webSocketURI;
    private int port;
    private SslContext sslCtx;
    private EventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap bootstrap = new Bootstrap();

    private WsClient(final String uriStr) throws Exception {
        this.webSocketURI = new URI(uriStr);
        final boolean ssl = "wss".equalsIgnoreCase(webSocketURI.getScheme());
        port = webSocketURI.getPort();
        if (ssl) {
            sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        }

        final String isCompression = System.getProperty("icr.ws.compression", "false");

        bootstrap.option(ChannelOption.TCP_NODELAY, true)
                .group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new LoggingHandler(LogLevel.INFO));
                if (sslCtx != null) {
                    p.addLast(sslCtx.newHandler(ch.alloc(), webSocketURI.getHost(), 443));
                }

                if ("true".equalsIgnoreCase(isCompression)) {
                    p.addLast(new HttpClientCodec(), new HttpObjectAggregator(8192),
                            WebSocketClientCompressionHandler.INSTANCE);
                } else {
                    p.addLast(new HttpClientCodec(), new HttpObjectAggregator(8192));
                }

                p.addLast("wsClientHandler", new WsClientHandler());

            }
        });

    }

    public Connection connect(String token, ConnectionListener listener, int connectionTimeout) throws Exception {
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout);
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        httpHeaders.set("icr-token",token);

        WebSocketClientHandshaker handShaker = WebSocketClientHandshakerFactory
                .newHandshaker(webSocketURI, WebSocketVersion.V13, null, true, httpHeaders);

        Channel channel = bootstrap.connect(webSocketURI.getHost(), port).sync().channel();
        log.info("webSocket channel established after sync,connectionId:{}", channel.id());
        WsClientHandler handler = (WsClientHandler) channel.pipeline().get("wsClientHandler");
        handler.setListener(listener);
        handler.setHandShaker(handShaker);
        handShaker.handshake(channel);
        handler.handshakeFuture().sync();
        log.info("webSocket connection is established after handshake,connectionId:{}", channel.id());
        return new WsConnection(channel);
    }

    public void shutdown() {
        group.shutdownGracefully();
    }

}
