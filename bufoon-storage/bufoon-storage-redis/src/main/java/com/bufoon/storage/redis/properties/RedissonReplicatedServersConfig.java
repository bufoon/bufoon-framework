package com.bufoon.storage.redis.properties;

import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/3/19 17:32
 * @Desc: as follows.
 */
public class RedissonReplicatedServersConfig extends RedissonBaseMultiConfig {
    /**
     * Replication group node urls list
     */
    private List<String> nodeAddresses = new ArrayList<String>();

    /**
     * Replication group scan interval in milliseconds
     */
    private int scanInterval = 1000;

    /**
     * Database index used for Redis connection
     */
    private int database = 0;

    public List<String> getNodeAddresses() {
        return nodeAddresses;
    }

    public void setNodeAddresses(List<String> nodeAddresses) {
        this.nodeAddresses = nodeAddresses;
    }

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public Config replicatedServersConfig(CustomRedisProperties properties){
        Config config = new Config();
        config.setCodec(loadCodec(properties.getRedisson().getCodec()))
                .setLockWatchdogTimeout(properties.getRedisson().getLockWatchdogTimeout())
                .setNettyThreads(properties.getRedisson().getNettyThreads())
                .setThreads(properties.getRedisson().getThreads())
                .setTransportMode(properties.getRedisson().getTransportMode())
                .useReplicatedServers().setTimeout(properties.getRedisson().getReplicated().getTimeout())
                .setTcpNoDelay(properties.getRedisson().getReplicated().isTcpNoDelay())
                .setSubscriptionsPerConnection(properties.getRedisson().getReplicated().getSubscriptionsPerConnection())
                .setSubscriptionMode(properties.getRedisson().getReplicated().getSubscriptionMode())
                .setSubscriptionConnectionPoolSize(properties.getRedisson().getReplicated().getSubscriptionConnectionPoolSize())
                .setSubscriptionConnectionMinimumIdleSize(properties.getRedisson().getReplicated().getSubscriptionConnectionMinimumIdleSize())
                .setSslTruststorePassword(properties.getRedisson().getReplicated().getSslTruststorePassword())
                .setSslProvider(properties.getRedisson().getReplicated().getSslProvider())
                .setSslKeystorePassword(properties.getRedisson().getReplicated().getSslKeystorePassword())
                .setSslEnableEndpointIdentification(properties.getRedisson().getReplicated().isSslEnableEndpointIdentification())
                .setSlaveConnectionPoolSize(properties.getRedisson().getReplicated().getSlaveConnectionPoolSize())
                .setSlaveConnectionMinimumIdleSize(properties.getRedisson().getReplicated().getSlaveConnectionMinimumIdleSize())
                .setRetryInterval(properties.getRedisson().getReplicated().getRetryInterval())
                .setRetryAttempts(properties.getRedisson().getReplicated().getRetryAttempts())
                .setReadMode(properties.getRedisson().getReplicated().getReadMode())
                .setPingTimeout(properties.getRedisson().getReplicated().getPingTimeout())
                .setPingConnectionInterval(properties.getRedisson().getReplicated().getPingConnectionInterval())
                .setPassword(properties.getRedisson().getReplicated().getPassword())
                .setMasterConnectionPoolSize(properties.getRedisson().getReplicated().getMasterConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(properties.getRedisson().getReplicated().getMasterConnectionMinimumIdleSize())
                .setLoadBalancer(loadLoadBalancer(properties.getRedisson().getMasterSlave().getLoadBalancer()))
                .setKeepAlive(properties.getRedisson().getReplicated().isKeepAlive())
                .setIdleConnectionTimeout(properties.getRedisson().getReplicated().getIdleConnectionTimeout())
                .setFailedSlaveReconnectionInterval(properties.getRedisson().getReplicated().getFailedSlaveReconnectionInterval())
                .setFailedSlaveCheckInterval(properties.getRedisson().getReplicated().getFailedSlaveCheckInterval())
                .setDnsMonitoringInterval(properties.getRedisson().getReplicated().getDnsMonitoringInterval())
                .setConnectTimeout(properties.getRedisson().getReplicated().getConnectTimeout())
                .setClientName(properties.getRedisson().getReplicated().getClientName())
                .setDatabase(properties.getRedisson().getReplicated().getDatabase())
                .addNodeAddress(properties.getRedisson().getReplicated().getNodeAddresses().toArray(new String[0]))
                .setScanInterval(properties.getRedisson().getReplicated().getScanInterval());
        return config;
    }
}
