package com.alibaba.demon.ws;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * webSocket data send
 * @author: Demon
 * @create: 2019-04-10
 */
@Slf4j
public class WsConnection implements Connection {

    private Channel channel;

    public WsConnection(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String getId() {
        if (channel != null) {
            return channel.id().toString();
        }
        return null;

    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public void sendText(final String payload) {
        if (channel != null && channel.isActive()) {
            log.info("@WsConnection.sendText.thread:{},send:{}", Thread.currentThread().getId(), payload);
            TextWebSocketFrame frame = new TextWebSocketFrame(payload);
            channel.writeAndFlush(frame);
        }

    }

    @Override
    public void sendBinary(byte[] payload) {
        if (channel != null && channel.isActive()) {
            BinaryWebSocketFrame frame = new BinaryWebSocketFrame(Unpooled.wrappedBuffer(payload));
            channel.writeAndFlush(frame);
        }
    }
}
