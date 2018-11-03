package com.alibaba.demon.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("messageSender")
@Slf4j
public class MessageSender implements ApplicationListener<ContextRefreshedEvent> {


    @Resource
    private MessagePublisher messagePublisher;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
