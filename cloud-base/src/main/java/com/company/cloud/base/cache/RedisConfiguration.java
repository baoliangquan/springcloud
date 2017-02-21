package com.company.cloud.base.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yoara on 2017/2/17.
 */
@Configuration
public class RedisConfiguration {
    @Resource
    private Environment env;

    @Bean(name="redisCache")
    public RedisCache redisCache(){
        RedisCacheImpl redisCache = new RedisCacheImpl();
        Map<String,RedisTemplate<String, Object>> redisMap = new HashMap<>();
        redisMap.put("SAVE",redisTemplate());
        redisMap.put("QUERY",redisTemplateQuery());
        redisCache.setRedis(redisMap);
        return redisCache;
    }

    @Bean(name="stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(){
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name="redisTemplate")
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name="redisTemplateQuery")
    public RedisTemplate redisTemplateQuery(){
        RedisTemplate queryRedisTemplate = new RedisTemplate();
        queryRedisTemplate.setConnectionFactory(jedisConnectionFactoryQuery());
        queryRedisTemplate.setKeySerializer(new StringRedisSerializer());
        queryRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return queryRedisTemplate;
    }

    @Bean(name="jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(env.getProperty("basic.redis.save.url"));
        factory.setPort(env.getProperty("basic.redis.save.port",Integer.class));
        factory.setPoolConfig(jedisPoolConfig());
        return factory;
    }

    @Bean(name="jedisConnectionFactoryQuery")
    public JedisConnectionFactory jedisConnectionFactoryQuery(){
        JedisConnectionFactory queryFactory = new JedisConnectionFactory();
        queryFactory.setHostName(env.getProperty("basic.redis.query.url"));
        queryFactory.setPort(env.getProperty("basic.redis.query.port",Integer.class));
        queryFactory.setPoolConfig(jedisPoolConfig());
        return queryFactory;
    }

    @Bean(name="jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(20000);
        return config;
    }
}
