package com.alibaba.demon.redis;


import com.alibaba.demon.domain.MessageDTO;

public interface MessagePublisher {

    /**
     * 发布
     */
    void publish(MessageDTO messageDTO);
}
