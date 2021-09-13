package com.sj.web.controller;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShengJie.Wang
 * @date 2021-09-07 15:38
 */
@RestController
public class RedisController {

  public static final Logger LOGGER = LoggerFactory.getLogger(RedisController.class);

  @Autowired
  StringRedisTemplate redisTemplate;

  @GetMapping("/redis")
  public String  testRedis() {
    redisTemplate.opsForValue().set("sj", "test");
    redisTemplate.opsForValue().set("sj", "1", Duration.ofDays(1));
    redisTemplate.delete("sj") ;
    System.out.println(redisTemplate.opsForValue().get("sj"));
    return "OK" ;
  }
}
