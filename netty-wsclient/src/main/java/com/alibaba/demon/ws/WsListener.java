package com.alibaba.demon.ws;

import lombok.extern.slf4j.Slf4j;
import java.nio.ByteBuffer;

/**
 * @author: Demon
 * @create: 2019-04-14
 **/
@Slf4j
public class WsListener implements ConnectionListener {
    @Override
    public void onOpen() {

    }

    @Override
    public void onClose(int closeCode, String reason) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onFail(int status, String reason) {

    }

    @Override
    public void onMessage(String message) {

        log.info("@WsListener.onMessage msg:{}", message);
    }

    @Override
    public void onMessage(ByteBuffer message) {

    }
}
