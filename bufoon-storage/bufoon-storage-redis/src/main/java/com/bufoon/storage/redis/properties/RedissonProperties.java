package com.bufoon.storage.redis.properties;

import org.redisson.config.TransportMode;

import java.util.concurrent.ExecutorService;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/3/19 16:46
 * @Desc: as follows.
 */
public class RedissonProperties {

    private RedissonSentinelServersConfig sentinel;

    private RedissonMasterSlaveServersConfig masterSlave;

    private RedissonSingleServerConfig single;

    private RedissonClusterServersConfig cluster;

    private RedissonReplicatedServersConfig replicated;

    /**
     * Threads amount shared between all redis node clients
     */
    private int threads = 0; // 0 = current_processors_amount * 2

    private int nettyThreads = 0; // 0 = current_processors_amount * 2

    /**
     * Redis key/value codec. JsonJacksonCodec used by default
     */
    private String codec;

    private ExecutorService executor;

    /**
     * Config option for enabling Redisson Reference feature.
     * Default value is TRUE
     */
    private boolean referenceEnabled = true;

    private TransportMode transportMode = TransportMode.NIO;

    private long lockWatchdogTimeout = 30 * 1000;

    private boolean keepPubSubOrder = true;

    public RedissonSentinelServersConfig getSentinel() {
        return sentinel;
    }

    public void setSentinel(RedissonSentinelServersConfig sentinel) {
        this.sentinel = sentinel;
    }

    public RedissonMasterSlaveServersConfig getMasterSlave() {
        return masterSlave;
    }

    public void setMasterSlave(RedissonMasterSlaveServersConfig masterSlave) {
        this.masterSlave = masterSlave;
    }

    public RedissonSingleServerConfig getSingle() {
        return single;
    }

    public void setSingle(RedissonSingleServerConfig single) {
        this.single = single;
    }

    public RedissonClusterServersConfig getCluster() {
        return cluster;
    }

    public void setCluster(RedissonClusterServersConfig cluster) {
        this.cluster = cluster;
    }

    public RedissonReplicatedServersConfig getReplicated() {
        return replicated;
    }

    public void setReplicated(RedissonReplicatedServersConfig replicated) {
        this.replicated = replicated;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(int nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public boolean isReferenceEnabled() {
        return referenceEnabled;
    }

    public void setReferenceEnabled(boolean referenceEnabled) {
        this.referenceEnabled = referenceEnabled;
    }

    public TransportMode getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TransportMode transportMode) {
        this.transportMode = transportMode;
    }

    public long getLockWatchdogTimeout() {
        return lockWatchdogTimeout;
    }

    public void setLockWatchdogTimeout(long lockWatchdogTimeout) {
        this.lockWatchdogTimeout = lockWatchdogTimeout;
    }

    public boolean isKeepPubSubOrder() {
        return keepPubSubOrder;
    }

    public void setKeepPubSubOrder(boolean keepPubSubOrder) {
        this.keepPubSubOrder = keepPubSubOrder;
    }
}
