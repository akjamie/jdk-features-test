package org.akj.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
public class JedisUtil {
    private static final String host = "3.112.120.15";
    private static final int port = 6379;
    private static final String auth = "test-redis-2019";
    private static JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(20);
        jedisPoolConfig.setMinIdle(5);

        jedisPool = new JedisPool(jedisPoolConfig, host, port);
    }

    public static Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        jedis.auth(auth);
        return jedis;
    }

    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
