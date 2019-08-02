//package org.akj.redis;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import redis.clients.jedis.Jedis;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class RedisConnectionTest {
//    private static Jedis jedis = null;
//
//    @BeforeAll
//    public static void setupBeforeClass() {
//        jedis = new Jedis("3.112.120.15", 6379);
//        jedis.auth("test-redis-2019");
//    }
//
//    @AfterAll
//    public static void teardownAfterClass(){
//        if(jedis != null){
//            jedis.close();
//        }
//    }
//
//    @Test
//    public void testConnection() {
//        String echo = "setup connection successfully";
//       assertEquals("PONG",jedis.ping());
//    }
//
//    @Test
//    public void testStringType(){
//        // set test helloworld
//        String retCode = jedis.set("test", "hello world");
//        assertEquals("OK", retCode);
//
//        // get test
//        String value =  jedis.get("test");
//        assertEquals("hello world", value);
//
//        // exists test
//        String cityName = null;
//        List<String> hotCities = List.of("Xian", "Guangzhou", "Wuhan", "Chongqing", "Shanghai");
//        final String key = "weather:hotestcity:name";
//        if(jedis.exists(key)){
//            cityName = jedis.get(key);
//            System.out.println("found the highest temperature city name from redis, which is " + cityName);
//        }else{
//            cityName = hotCities.get(0);
//            System.out.println("found the highest temperature city name from real-time weather database, which is " + cityName);
//
//            jedis.set(key, cityName);
//            jedis.expire(key,10);
//            System.out.println("cache the highest temperature city name");
//        }
//    }
//}
