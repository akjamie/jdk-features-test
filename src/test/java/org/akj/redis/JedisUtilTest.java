package org.akj.redis;

import org.akj.feign.service.model.Product;
import org.akj.jdbc.repository.ProductRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JedisUtilTest {
    private static Jedis jedis = null;

    private static ProductRepository repository = null;

    @BeforeAll
    public static void setupBeforeClass() {
        jedis = JedisUtil.getJedis();
        repository = new ProductRepository();
    }

    @AfterAll
    public static void teardownAfterClass() {
        JedisUtil.close(jedis);
    }

    @Test
    public void testConnection() {
        String echo = "setup connection successfully";
        assertEquals("PONG", jedis.ping());
    }

    @Test
    public void testStringType() {
        // set test helloworld
        String retCode = jedis.set("test", "hello world");
        assertEquals("OK", retCode);

        // get test
        String value = jedis.get("test");
        assertEquals("hello world", value);

        // exists test
        String cityName = null;
        List<String> hotCities = List.of("Xian", "Guangzhou", "Wuhan", "Chongqing", "Shanghai");
        final String key = "weather:hotestcity:name";
        if (jedis.exists(key)) {
            cityName = jedis.get(key);
            System.out.println("found the highest temperature city name from redis, which is " + cityName);
        } else {
            cityName = hotCities.get(0);
            System.out.println("found the highest temperature city name from real-time weather database, which is " + cityName);

            jedis.set(key, cityName);
            jedis.expire(key, 10);
            System.out.println("cache the highest temperature city name");
        }
    }

    @RepeatedTest(value = 3)
    public void testHashType() {
        String key = "product:2";
        if (jedis.exists(key)) {
            Map<String, String> map = jedis.hgetAll(key);
            System.out.println("obtained product info from Redis, details: " + map);
        } else {
            // simulate to retrieve data from RDS
            Map<String, String> map = new HashMap<>();
            map.put("id", UUID.randomUUID().toString());
            map.put("name", "Honor 20");
            map.put("description", "Honor 20 released to Europe market");
            map.put("price.amount", "3500");
            map.put("price.currency", "CNY");

            System.out.println("data returned from RDS,details:" + map);
            jedis.hmset(key, map);
            System.out.println("cached product info into Redis");
        }
    }

    @Test
    public void testHashType1() throws SQLException {
        String key = "product:44ddbe50-0135-4264-92dc-273784f61997";
        if (jedis.exists(key)) {
            Map<String, String> map = jedis.hgetAll(key);
            System.out.println("obtained product info from Redis, details: " + map);
        } else {
            // retrieve data from RDS
            Optional<Product> product = repository.findById("44ddbe50-0135-4264-92dc-273784f61997");

            if(product.isPresent()){
                Product object = product.get();
                System.out.println("data returned from RDS,details:" + object);

                // cache data into redis
                Map<String, String> map = new HashMap<>();
                map.put("id", object.getId());
                map.put("name", object.getName());
                map.put("description", object.getDescription());
                map.put("price.amount", object.getPrice().getAmount().toString());
                map.put("price.currency", object.getPrice().getCurrency());
                jedis.hmset(key, map);
                jedis.expire(key, 10);
                System.out.println("cached product info into Redis, and it will be expire in 10 seconds");
            }
        }

    }
}
