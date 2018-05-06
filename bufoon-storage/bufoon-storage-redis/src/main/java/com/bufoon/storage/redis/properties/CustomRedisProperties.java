package com.bufoon.storage.redis.properties;


import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/3/19 14:30
 * @Desc: as follows.
 */
@ConfigurationProperties(prefix = "bufoon.storage.redis")
public class CustomRedisProperties {
    private RedisProperties jedis;
    private RedissonProperties redisson;

    public RedisProperties getJedis() {
        return jedis;
    }

    public void setJedis(RedisProperties jedis) {
        this.jedis = jedis;
    }

    public RedissonProperties getRedisson() {
        return redisson;
    }

    public void setRedisson(RedissonProperties redisson) {
        this.redisson = redisson;
    }

}
