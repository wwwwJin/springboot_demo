package com.wwj.demo;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest 
{
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void add(){
        //存入数据没有过期时间
        redisTemplate.opsForValue().set("a","a1");
        //存入数据并设置过期时间
        redisTemplate.opsForValue().set("b","b1",10, TimeUnit.SECONDS);

        //存入数据并设置过期时间
        redisTemplate.opsForValue().set("c","d1",Duration.ofSeconds(5));

        //该方法是用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
        redisTemplate.opsForValue().set("d","d1");
        redisTemplate.opsForValue().set("d","d2",1);



    }

    @Test
    public void get(){

       // String  str =  stringRedisTemplate.opsForValue().get("a2");
        String  str2 =  (String)redisTemplate.opsForValue().get("a");
        String  str3 =  (String)redisTemplate.opsForValue().getAndSet("a","a4");

        // 存在更改，返回true；不存在返回false
        Boolean b1 =  redisTemplate.opsForValue().setIfPresent("a","a5");
        Boolean b2 =  redisTemplate.opsForValue().setIfPresent("f","a5");
    }
}
