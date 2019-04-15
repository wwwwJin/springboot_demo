package com.wwj.demo.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 *Redis单机配置
 *
 * @author WWJ
 */

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host ;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxActive;


    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxActive);
        return jedisPoolConfig;
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration redisSentinelConfiguration = new RedisStandaloneConfiguration();
        redisSentinelConfiguration.setHostName(host);
        redisSentinelConfiguration.setPort(port);
        return redisSentinelConfiguration;
    }

    @Bean
    public JedisClientConfiguration jedisClientConfiguration(){
        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder jedisClientConfigurationBuilder = 	(JedisClientConfiguration.DefaultJedisClientConfigurationBuilder)JedisClientConfiguration.builder();
        jedisClientConfigurationBuilder.poolConfig(jedisPoolConfig());
        jedisClientConfigurationBuilder.usePooling();
        return jedisClientConfigurationBuilder.build();
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory(redisStandaloneConfiguration(),jedisClientConfiguration());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        //使用fastjson序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);

        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);

        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        return template;
    }
}



