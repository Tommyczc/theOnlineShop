package com.theOnlineShop.webConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Configuration
public class RedisConfig {
    @Resource
    private RedisTemplate redisTemplate;

    @Bean
    public RedisTemplate redisTemplateInit(){
        //序列化key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //序列化value的实例化对象
        redisTemplate.setValueSerializer(
                new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
