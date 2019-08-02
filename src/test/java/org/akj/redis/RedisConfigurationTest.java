package org.akj.redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RedisConfigurationTest {

    private RedisTemplate template;

    @BeforeEach
    void setUp() {
        template = new RedisConfiguration().redisTemplate();
    }

    @Test
    void redisTemplate() {
        assertNotNull(template);
    }

    @Test
//    @Disabled
    public void testWithStringType(){
        String key = "test";
        ValueOperations valueOperations = template.opsForValue();
        if(template.hasKey(key)){
            System.out.println("get value from cache, key:" + key + " value:" + valueOperations.get(key));
        }else{
            valueOperations.set(key, "hello world");
            System.out.println(key + "does not exists, save data for cache");
        }
    }
}