package com.alibaba.demon.config;


import com.alibaba.demon.redis.MessagePublisher;
import com.alibaba.demon.redis.impl.MessagePublisherImpl;
import com.alibaba.demon.redis.MessageSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import javax.annotation.Resource;
import java.util.concurrent.Executors;

@Configuration
public class RedisConfig {

    @Resource
    JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        final ObjectMapper mapper = new ObjectMapper();
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer(mapper));
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        cacheManager.setDefaultExpiration(60 * 60 * 24);
        return cacheManager;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new MessageSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(messageListener(), topic());
        container.setTaskExecutor(Executors.newFixedThreadPool(4));
        return container;
    }

    @Bean
    MessagePublisher messagePublisher() {
        return new MessagePublisherImpl(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("icr_test");
    }
}
