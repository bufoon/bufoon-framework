package com.bufoon.storage.mongo.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/1/17 15:38
 * @Desc: as follows.
 */
@Configuration
public class MongoDbConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "bufoon.storage.mongo")
    public MongoProperties mongoProperties(){
        return new MongoProperties();
    }

}
