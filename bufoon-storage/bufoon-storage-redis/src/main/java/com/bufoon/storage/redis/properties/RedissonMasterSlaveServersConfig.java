package com.bufoon.storage.redis.properties;

import org.redisson.config.Config;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/3/19 17:34
 * @Desc: as follows.
 */
public class RedissonMasterSlaveServersConfig extends RedissonBaseMultiConfig {

    /**
     * Redis slave servers addresses host:port
     */
    private Set<String> slaveAddresses = new HashSet<String>();

    /**
     * Redis master server address host:port
     */
    private String masterAddress;

    /**
     * Database index used for Redis connection
     */
    private int database = 0;

    public Set<String> getSlaveAddresses() {
        return slaveAddresses;
    }

    public void setSlaveAddresses(Set<String> slaveAddresses) {
        this.slaveAddresses = slaveAddresses;
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public Config masterSlaveServersConfig(CustomRedisProperties properties){
        Config config = new Config();
        config.setCodec(loadCodec(properties.getRedisson().getCodec()))
                .setLockWatchdogTimeout(properties.getRedisson().getLockWatchdogTimeout())
                .setNettyThreads(properties.getRedisson().getNettyThreads())
                .setThreads(properties.getRedisson().getThreads())
                .setTransportMode(properties.getRedisson().getTransportMode())
                .useMasterSlaveServers().setTimeout(properties.getRedisson().getMasterSlave().getTimeout())
                .setTcpNoDelay(properties.getRedisson().getMasterSlave().isTcpNoDelay())
                .setSubscriptionsPerConnection(properties.getRedisson().getMasterSlave().getSubscriptionsPerConnection())
                .setSubscriptionMode(properties.getRedisson().getMasterSlave().getSubscriptionMode())
                .setSubscriptionConnectionPoolSize(properties.getRedisson().getMasterSlave().getSubscriptionConnectionPoolSize())
                .setSubscriptionConnectionMinimumIdleSize(properties.getRedisson().getMasterSlave().getSubscriptionConnectionMinimumIdleSize())
                .setSslTruststorePassword(properties.getRedisson().getMasterSlave().getSslTruststorePassword())
                .setSslProvider(properties.getRedisson().getMasterSlave().getSslProvider())
                .setSslKeystorePassword(properties.getRedisson().getMasterSlave().getSslKeystorePassword())
                .setSslEnableEndpointIdentification(properties.getRedisson().getMasterSlave().isSslEnableEndpointIdentification())
                .setSlaveConnectionPoolSize(properties.getRedisson().getMasterSlave().getSlaveConnectionPoolSize())
                .setSlaveConnectionMinimumIdleSize(properties.getRedisson().getMasterSlave().getSlaveConnectionMinimumIdleSize())
                .setRetryInterval(properties.getRedisson().getMasterSlave().getRetryInterval())
                .setRetryAttempts(properties.getRedisson().getMasterSlave().getRetryAttempts())
                .setReadMode(properties.getRedisson().getMasterSlave().getReadMode())
                .setPingTimeout(properties.getRedisson().getMasterSlave().getPingTimeout())
                .setPingConnectionInterval(properties.getRedisson().getMasterSlave().getPingConnectionInterval())
                .setPassword(properties.getRedisson().getMasterSlave().getPassword())
                .setMasterConnectionPoolSize(properties.getRedisson().getMasterSlave().getMasterConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(properties.getRedisson().getMasterSlave().getMasterConnectionMinimumIdleSize())
                .setLoadBalancer(loadLoadBalancer(properties.getRedisson().getMasterSlave().getLoadBalancer()))
                .setKeepAlive(properties.getRedisson().getMasterSlave().isKeepAlive())
                .setIdleConnectionTimeout(properties.getRedisson().getMasterSlave().getIdleConnectionTimeout())
                .setFailedSlaveReconnectionInterval(properties.getRedisson().getMasterSlave().getFailedSlaveReconnectionInterval())
                .setFailedSlaveCheckInterval(properties.getRedisson().getMasterSlave().getFailedSlaveCheckInterval())
                .setDnsMonitoringInterval(properties.getRedisson().getMasterSlave().getDnsMonitoringInterval())
                .setConnectTimeout(properties.getRedisson().getMasterSlave().getConnectTimeout())
                .setClientName(properties.getRedisson().getMasterSlave().getClientName())
                .setMasterAddress(properties.getRedisson().getMasterSlave().getMasterAddress())
                .setDatabase(properties.getRedisson().getMasterSlave().getDatabase())
                .addSlaveAddress(properties.getRedisson().getMasterSlave().getSlaveAddresses().toArray(new String[0]));
        return config;
    }

}
