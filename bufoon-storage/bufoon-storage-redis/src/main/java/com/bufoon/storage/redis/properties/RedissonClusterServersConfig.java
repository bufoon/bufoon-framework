package com.bufoon.storage.redis.properties;

import com.bufoon.commons.utils.StringUtils;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/3/19 17:33
 * @Desc: as follows.
 */
public class RedissonClusterServersConfig extends RedissonBaseMultiConfig {
    /**
     * Redis cluster node urls list
     */
    private List<String> nodeAddresses = new ArrayList<String>();

    /**
     * Redis cluster scan interval in milliseconds
     */
    private int scanInterval = 1000;

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

    public Config clusterServersConfig(CustomRedisProperties properties){
        Config config = new Config();
        config.setCodec(loadCodec(properties.getRedisson().getCodec()))
              .setLockWatchdogTimeout(properties.getRedisson().getLockWatchdogTimeout())
              .setNettyThreads(properties.getRedisson().getNettyThreads())
              .setThreads(properties.getRedisson().getThreads())
              .setTransportMode(properties.getRedisson().getTransportMode())
              .useClusterServers().setTimeout(properties.getRedisson().getCluster().getTimeout())
              .setTcpNoDelay(properties.getRedisson().getCluster().isTcpNoDelay())
              .setSubscriptionsPerConnection(properties.getRedisson().getCluster().getSubscriptionsPerConnection())
              .setSubscriptionMode(properties.getRedisson().getCluster().getSubscriptionMode())
              .setSubscriptionConnectionPoolSize(properties.getRedisson().getCluster().getSubscriptionConnectionPoolSize())
              .setSubscriptionConnectionMinimumIdleSize(properties.getRedisson().getCluster().getSubscriptionConnectionMinimumIdleSize())
              .setSslTruststorePassword(properties.getRedisson().getCluster().getSslTruststorePassword())
              .setSslProvider(properties.getRedisson().getCluster().getSslProvider())
              .setSslKeystorePassword(properties.getRedisson().getCluster().getSslKeystorePassword())
              .setSslEnableEndpointIdentification(properties.getRedisson().getCluster().isSslEnableEndpointIdentification())
              .setSlaveConnectionPoolSize(properties.getRedisson().getCluster().getSlaveConnectionPoolSize())
              .setSlaveConnectionMinimumIdleSize(properties.getRedisson().getCluster().getSlaveConnectionMinimumIdleSize())
              .setRetryInterval(properties.getRedisson().getCluster().getRetryInterval())
              .setRetryAttempts(properties.getRedisson().getCluster().getRetryAttempts())
              .setReadMode(properties.getRedisson().getCluster().getReadMode())
              .setPingTimeout(properties.getRedisson().getCluster().getPingTimeout())
              .setPingConnectionInterval(properties.getRedisson().getCluster().getPingConnectionInterval())
              .setPassword(StringUtils.isEmpty(properties.getRedisson().getCluster().getPassword())?null:properties.getRedisson().getCluster().getPassword())
              .setMasterConnectionPoolSize(properties.getRedisson().getCluster().getMasterConnectionPoolSize())
              .setMasterConnectionMinimumIdleSize(properties.getRedisson().getCluster().getMasterConnectionMinimumIdleSize())
              .setLoadBalancer(loadLoadBalancer(properties.getRedisson().getCluster().getLoadBalancer()))
              .setKeepAlive(properties.getRedisson().getCluster().isKeepAlive())
              .setIdleConnectionTimeout(properties.getRedisson().getCluster().getIdleConnectionTimeout())
              .setFailedSlaveReconnectionInterval(properties.getRedisson().getCluster().getFailedSlaveReconnectionInterval())
              .setFailedSlaveCheckInterval(properties.getRedisson().getCluster().getFailedSlaveCheckInterval())
              .setDnsMonitoringInterval(properties.getRedisson().getCluster().getDnsMonitoringInterval())
              .setConnectTimeout(properties.getRedisson().getCluster().getConnectTimeout())
              .setClientName(properties.getRedisson().getCluster().getClientName())
              .addNodeAddress(properties.getRedisson().getCluster().getNodeAddresses().toArray(new String[0]))
              .setScanInterval(properties.getRedisson().getCluster().getScanInterval());
        return config;
    }

}
