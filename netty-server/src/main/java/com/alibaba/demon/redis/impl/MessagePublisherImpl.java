package com.alibaba.demon.redis.impl;

import com.alibaba.demon.domain.MessageDTO;
import com.alibaba.demon.redis.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class MessagePublisherImpl implements MessagePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChannelTopic topic;

    public MessagePublisherImpl(RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(MessageDTO messageDTO) {
        redisTemplate.convertAndSend(topic.getTopic(), messageDTO);
    }
}
