package com.bufoon.storage.redis.properties;

import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/3/19 17:30
 * @Desc: as follows.
 */
public class RedissonSentinelServersConfig extends RedissonBaseMultiConfig {
    private List<String> sentinelAddresses = new ArrayList<String>();

    private String masterName;

    /**
     * Database index used for Redis connection
     */
    private int database = 0;

    /**
     * Sentinel scan interval in milliseconds
     */
    private int scanInterval = 1000;

    public List<String> getSentinelAddresses() {
        return sentinelAddresses;
    }

    public void setSentinelAddresses(List<String> sentinelAddresses) {
        this.sentinelAddresses = sentinelAddresses;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public Config sentinelServersConfig(CustomRedisProperties properties){
        Config config = new Config();
        config.setCodec(loadCodec(properties.getRedisson().getCodec()))
                .setLockWatchdogTimeout(properties.getRedisson().getLockWatchdogTimeout())
                .setNettyThreads(properties.getRedisson().getNettyThreads())
                .setThreads(properties.getRedisson().getThreads())
                .setTransportMode(properties.getRedisson().getTransportMode())
                .useSentinelServers().setTimeout(properties.getRedisson().getSentinel().getTimeout())
                .setTcpNoDelay(properties.getRedisson().getSentinel().isTcpNoDelay())
                .setSubscriptionsPerConnection(properties.getRedisson().getSentinel().getSubscriptionsPerConnection())
                .setSubscriptionMode(properties.getRedisson().getSentinel().getSubscriptionMode())
                .setSubscriptionConnectionPoolSize(properties.getRedisson().getSentinel().getSubscriptionConnectionPoolSize())
                .setSubscriptionConnectionMinimumIdleSize(properties.getRedisson().getSentinel().getSubscriptionConnectionMinimumIdleSize())
                .setSslTruststorePassword(properties.getRedisson().getSentinel().getSslTruststorePassword())
                .setSslProvider(properties.getRedisson().getSentinel().getSslProvider())
                .setSslKeystorePassword(properties.getRedisson().getSentinel().getSslKeystorePassword())
                .setSslEnableEndpointIdentification(properties.getRedisson().getSentinel().isSslEnableEndpointIdentification())
                .setSlaveConnectionPoolSize(properties.getRedisson().getSentinel().getSlaveConnectionPoolSize())
                .setSlaveConnectionMinimumIdleSize(properties.getRedisson().getSentinel().getSlaveConnectionMinimumIdleSize())
                .setRetryInterval(properties.getRedisson().getSentinel().getRetryInterval())
                .setRetryAttempts(properties.getRedisson().getSentinel().getRetryAttempts())
                .setReadMode(properties.getRedisson().getSentinel().getReadMode())
                .setPingTimeout(properties.getRedisson().getSentinel().getPingTimeout())
                .setPingConnectionInterval(properties.getRedisson().getSentinel().getPingConnectionInterval())
                .setPassword(properties.getRedisson().getSentinel().getPassword())
                .setMasterConnectionPoolSize(properties.getRedisson().getSentinel().getMasterConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(properties.getRedisson().getSentinel().getMasterConnectionMinimumIdleSize())
                .setLoadBalancer(loadLoadBalancer(properties.getRedisson().getSentinel().getLoadBalancer()))
                .setKeepAlive(properties.getRedisson().getSentinel().isKeepAlive())
                .setIdleConnectionTimeout(properties.getRedisson().getSentinel().getIdleConnectionTimeout())
                .setFailedSlaveReconnectionInterval(properties.getRedisson().getSentinel().getFailedSlaveReconnectionInterval())
                .setFailedSlaveCheckInterval(properties.getRedisson().getSentinel().getFailedSlaveCheckInterval())
                .setDnsMonitoringInterval(properties.getRedisson().getSentinel().getDnsMonitoringInterval())
                .setConnectTimeout(properties.getRedisson().getSentinel().getConnectTimeout())
                .setClientName(properties.getRedisson().getSentinel().getClientName())
                .setDatabase(properties.getRedisson().getSentinel().getDatabase())
                .setMasterName(properties.getRedisson().getSentinel().getMasterName())
                .addSentinelAddress(properties.getRedisson().getSentinel().getSentinelAddresses().toArray(new String[0]))
                .setScanInterval(properties.getRedisson().getSentinel().getScanInterval());
        return config;
    }
}
