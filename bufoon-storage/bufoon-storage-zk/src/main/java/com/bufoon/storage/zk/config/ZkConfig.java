package com.bufoon.storage.zk.config;

import com.bufoon.commons.base.BufoonException;
import com.bufoon.commons.utils.StringUtils;
import com.bufoon.storage.zk.prop.CuratorProperties;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CuratorProperties.class)
public class ZkConfig {

    private CuratorProperties curatorProperties;

    public ZkConfig(CuratorProperties curatorProperties){
        this.curatorProperties = curatorProperties;
        String confPath = curatorProperties.getConfigPath();
        if (StringUtils.isNotEmpty(confPath)){
            confPath = confPath.substring(0, confPath.lastIndexOf("/"));
            this.curatorProperties.setCommonPath(confPath);
        }
    }

    @Bean
    public CuratorFramework curatorFramework() throws Exception {
        if (curatorProperties == null){
            throw new BufoonException("zookeeper properties not config");
        }
        RetryPolicy retryPolicy = new RetryNTimes(curatorProperties.getRetryCount(), curatorProperties.getSleepTimes());
        CuratorFramework client = CuratorFrameworkFactory.newClient(curatorProperties.getZkAddress(),
                curatorProperties.getSessionTimeout(),curatorProperties.getConnectionTimeout(),
                retryPolicy);
        client.start();
        return client;
    }

    public static void main(String[] args) {
        String t = "/AppConfig/RTTX/RISK/commons/aaa";
        System.out.println(t.substring(0, t.lastIndexOf("/")));
    }
}
