package com.alibaba.demon.handler;

import com.alibaba.demon.ws.ConnectionListener;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: Demon
 * @create: 2019-04-10
 **/
@Slf4j
public class WsClientHandler extends SimpleChannelInboundHandler<Object> {

    private ConnectionListener listener;

    private WebSocketClientHandshaker handShaker;

    //TODO 如何使用
    private ChannelPromise handshakeFuture;

    public void setListener(ConnectionListener listener) {
        this.listener = listener;
    }

    public void setHandShaker(WebSocketClientHandshaker handShaker) {
        this.handShaker = handShaker;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.debug("@handler added channel id:{}", ctx.channel().id());
        handshakeFuture = ctx.newPromise();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        Channel ch = ctx.channel();
        if (!handShaker.isHandshakeComplete()) {
            try {
                FullHttpResponse response = (FullHttpResponse)msg;
                handShaker.finishHandshake(ch, response);
                handshakeFuture.setSuccess();
                log.info("WebSocket Client connected! response headers[sec-websocket-extensions]:{}",
                        response.headers().get("sec-websocket-extensions"));
            } catch (WebSocketHandshakeException e) {
                FullHttpResponse res = (FullHttpResponse)msg;
                String errorMsg = String.format("WebSocket Client failed to connect,status:%s,reason:%s", res.status(),
                        res.content().toString(CharsetUtil.UTF_8));
                log.error(errorMsg);
                handshakeFuture.setFailure(new Exception(errorMsg));
            }
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse)msg;
            listener.onFail(response.status().code(), response.content().toString(CharsetUtil.UTF_8));
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.status() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        WebSocketFrame frame = (WebSocketFrame)msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame)frame;
            listener.onMessage(textFrame.text());
        } else if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame binFrame = (BinaryWebSocketFrame)frame;
            listener.onMessage(binFrame.content().nioBuffer());
        } else if (frame instanceof PongWebSocketFrame) {
            log.debug("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            log.debug("receive close frame");
            listener.onClose(((CloseWebSocketFrame)frame).statusCode(), ((CloseWebSocketFrame)frame).reasonText());
            ch.close();
        }
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }
}
