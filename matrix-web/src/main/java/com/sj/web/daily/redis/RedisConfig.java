package com.sj.web.daily.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    private RedisConnectionFactory connectionFactory = null;

    public RedisConnectionFactory initRedisConnectionFactory() {

        if (connectionFactory != null) {
            return connectionFactory;
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大闲置数
        poolConfig.setMaxIdle(30);
        //最大连接数
        poolConfig.setMaxTotal(50);
        //最大等待毫秒数
        poolConfig.setMaxWaitMillis(2000);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
        RedisStandaloneConfiguration rsCfg = connectionFactory.getStandaloneConfiguration();
        connectionFactory.setHostName("192.168.11.131" );
        connectionFactory.setPort(6379);
        connectionFactory.setPassword("123456");
        this.connectionFactory = connectionFactory;
        return connectionFactory;
    }
}
