package com.alibaba.demon.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

@Slf4j
public class MessageSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("@MessageSubscriber.body:{},channel:{}", new String(message.getBody()), new String(message.getChannel()));
    }
}
