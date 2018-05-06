package com.bufoon.storage.redis.properties;

import com.bufoon.commons.utils.StringUtils;
import org.redisson.config.Config;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/3/19 17:31
 * @Desc: as follows.
 */
public class RedissonSingleServerConfig extends RedissonBaseConfig {
    /**
     * Redis server address
     * host:port
     */
    private String address;

    /**
     * Minimum idle subscription connection amount
     */
    private int subscriptionConnectionMinimumIdleSize = 1;

    /**
     * Redis subscription connection maximum pool size
     *
     */
    private int subscriptionConnectionPoolSize = 50;

    /**
     * Minimum idle Redis connection amount
     */
    private int connectionMinimumIdleSize = 32;

    /**
     * Redis connection maximum pool size
     */
    private int connectionPoolSize = 64;

    /**
     * Database index used for Redis connection
     */
    private int database = 0;

    /**
     * Should the server address be monitored for changes in DNS? Useful for
     * AWS ElastiCache where the client is pointed at the endpoint for a replication group
     * which is a DNS alias to the current master node.<br>
     * <em>NB: applications must ensure the JVM DNS cache TTL is low enough to support this.</em>
     * e.g., http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/java-dg-jvm-ttl.html
     */
    private boolean dnsMonitoring = true;

    /**
     * Interval in milliseconds to check DNS
     */
    private long dnsMonitoringInterval = 5000;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSubscriptionConnectionMinimumIdleSize() {
        return subscriptionConnectionMinimumIdleSize;
    }

    public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
        this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
    }

    public int getSubscriptionConnectionPoolSize() {
        return subscriptionConnectionPoolSize;
    }

    public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
        this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
    }

    public int getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
    }

    public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
    }

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public boolean getDnsMonitoring() {
        return dnsMonitoring;
    }

    public void setDnsMonitoring(boolean dnsMonitoring) {
        this.dnsMonitoring = dnsMonitoring;
    }

    public long getDnsMonitoringInterval() {
        return dnsMonitoringInterval;
    }

    public void setDnsMonitoringInterval(long dnsMonitoringInterval) {
        this.dnsMonitoringInterval = dnsMonitoringInterval;
    }

    public Config singleServerConfig(CustomRedisProperties properties){
        Config config = new Config();
        config.setCodec(loadCodec(properties.getRedisson().getCodec()))
            .setLockWatchdogTimeout(properties.getRedisson().getLockWatchdogTimeout())
            .setNettyThreads(properties.getRedisson().getNettyThreads())
            .setThreads(properties.getRedisson().getThreads())
            .setTransportMode(properties.getRedisson().getTransportMode())
                .useSingleServer().setClientName(properties.getRedisson().getSingle().getClientName())
                .setAddress(properties.getRedisson().getSingle().getAddress())
                .setConnectionMinimumIdleSize(properties.getRedisson().getSingle().getConnectionMinimumIdleSize())
                .setConnectionPoolSize(properties.getRedisson().getSingle().getConnectionPoolSize())
                .setDatabase(properties.getRedisson().getSingle().getDatabase())
                .setDnsMonitoring(properties.getRedisson().getSingle().getDnsMonitoring())
                .setDnsMonitoringInterval(properties.getRedisson().getSingle().getDnsMonitoringInterval())
                .setSubscriptionConnectionMinimumIdleSize(
                        properties.getRedisson().getSingle().getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(properties.getRedisson().getSingle().getSubscriptionConnectionPoolSize())
                .setConnectTimeout(properties.getRedisson().getSingle().getConnectTimeout())
                .setTimeout(properties.getRedisson().getSingle().getTimeout())
                .setTcpNoDelay(properties.getRedisson().getSingle().isTcpNoDelay())
                .setSubscriptionsPerConnection(properties.getRedisson().getSingle().getSubscriptionsPerConnection())
                .setSslTruststorePassword(properties.getRedisson().getSingle().getSslTruststorePassword())
                .setSslProvider(properties.getRedisson().getSingle().getSslProvider())
                .setSslKeystorePassword(properties.getRedisson().getSingle().getSslKeystorePassword())
                .setSslEnableEndpointIdentification(properties.getRedisson().getSingle().isSslEnableEndpointIdentification())
                .setRetryInterval(properties.getRedisson().getSingle().getRetryInterval())
                .setRetryAttempts(properties.getRedisson().getSingle().getRetryAttempts())
                .setPingTimeout(properties.getRedisson().getSingle().getPingTimeout())
                .setPingConnectionInterval(properties.getRedisson().getSingle().getPingConnectionInterval())
                .setPassword(StringUtils.isEmpty(properties.getRedisson().getSingle().getPassword())
                        ?null:properties.getRedisson().getSingle().getPassword())
                .setKeepAlive(properties.getRedisson().getSingle().isKeepAlive())
                .setIdleConnectionTimeout(properties.getRedisson().getSingle().getIdleConnectionTimeout());
        return config;
    }
}
