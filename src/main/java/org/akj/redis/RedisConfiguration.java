package org.akj.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
public class RedisConfiguration {
    private String hostname = "3.112.120.15";
    private int port = 6379;
    private String password = "test-redis-2019";

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(20);
        jedisPoolConfig.setMinIdle(5);

        return jedisPoolConfig;
    }

    private JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostname);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);

        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jpcb.poolConfig(jedisPoolConfig());
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);

    }

    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
