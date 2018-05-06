package com.bufoon.storage.redis.config;

import com.bufoon.storage.redis.properties.CustomRedisProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/3/19 17:43
 * @Desc: as follows.
 */
@Configuration
@EnableConfigurationProperties(CustomRedisProperties.class)
public class RedisClientConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private CustomRedisProperties properties;

    public RedisClientConfig(CustomRedisProperties customRedisProperties){
        this.properties = customRedisProperties;
    }

    /**
     * redisson Client
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "bufoon.storage.redis", name = "choose", havingValue = "redisson", matchIfMissing = true)
    public RedissonClient redissonClient(){
        Config config;
        RedissonClient redisson = null;
        if (this.properties.getRedisson() == null){
            return null;
        }
        if (this.properties.getRedisson().getSingle() != null){
            // 单台服务模式
            config = this.properties.getRedisson().getSingle().singleServerConfig(properties);
            redisson = Redisson.create(config);
            return redisson;
        }
        if (this.properties.getRedisson().getMasterSlave() != null){
            // 主从服务模式
            config = this.properties.getRedisson().getMasterSlave().masterSlaveServersConfig(properties);
            redisson = Redisson.create(config);
            return redisson;
        }
        if (this.properties.getRedisson().getReplicated() != null){
            // 分片，云服务模式
            config = this.properties.getRedisson().getReplicated().replicatedServersConfig(properties);
            redisson = Redisson.create(config);
            return redisson;
        }
        if (this.properties.getRedisson().getSentinel() != null){
            // 哨兵服务模式
            config = this.properties.getRedisson().getSentinel().sentinelServersConfig(properties);
            redisson = Redisson.create(config);
            return redisson;
        }
        if (this.properties.getRedisson().getCluster() != null){
            // 集群服务模式
            config = this.properties.getRedisson().getCluster().clusterServersConfig(properties);
            redisson = Redisson.create(config);
            return redisson;
        }
        return redisson;
    }

    /**
     * springboot jedis默认客户端
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "bufoon.storage.redis", name = "choose", havingValue = "jedis", matchIfMissing = true)
    public RedisProperties redisProperties(){
        if (this.properties.getJedis() == null){
            return new RedisProperties();
        }
        return this.properties.getJedis();
    }
}
