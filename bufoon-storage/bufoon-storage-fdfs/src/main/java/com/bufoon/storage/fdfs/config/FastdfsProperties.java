package com.bufoon.storage.fdfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/2/27 10:43
 * @Desc: as follows.
 */
@ConfigurationProperties(prefix = "bufoon.storage.fdfs")
public class FastdfsProperties {
    /**
     * 连接超时，默认5秒
     */
    private Integer connectTimeoutInSeconds = 5;
    /**
     *网络超时，默认30秒
     */
    private Integer networkTimeoutInSeconds = 30;
    /**
     * 编码，默认UTF-8
     */
    private String charset = "UTF-8";
    /**
     * http token，默认false
     */
    private boolean httpAntiStealToken = false;
    /**
     * http 秘钥
     */
    private String httpSecretKey = "FastDFS_SZ_RTTX";
    /**
     * tracker http端口，默认80
     */
    private Integer httpTrackerHttpPort = 80;
    /**
     * tracker server连接串，多个以逗号隔开
     * 如：10.0.11.201:22122,10.0.11.202:22122,10.0.11.203:22122
     */
    private String trackerServers;

    /** 连接池默认最小连接数 */
    private long minPoolSize = 20;

    /** 连接池默认最大连接数 */
    private long maxPoolSize = 100;

    /** 默认等待时间（单位：秒） */
    private long waitTimes = 60;

    /**
     * 连接重试次数
     */
    private Integer retryConnectTimes = 3;

    public Integer getConnectTimeoutInSeconds() {
        return connectTimeoutInSeconds;
    }

    public void setConnectTimeoutInSeconds(Integer connectTimeoutInSeconds) {
        this.connectTimeoutInSeconds = connectTimeoutInSeconds;
    }

    public Integer getNetworkTimeoutInSeconds() {
        return networkTimeoutInSeconds;
    }

    public void setNetworkTimeoutInSeconds(Integer networkTimeoutInSeconds) {
        this.networkTimeoutInSeconds = networkTimeoutInSeconds;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isHttpAntiStealToken() {
        return httpAntiStealToken;
    }

    public void setHttpAntiStealToken(boolean httpAntiStealToken) {
        this.httpAntiStealToken = httpAntiStealToken;
    }

    public String getHttpSecretKey() {
        return httpSecretKey;
    }

    public void setHttpSecretKey(String httpSecretKey) {
        this.httpSecretKey = httpSecretKey;
    }

    public Integer getHttpTrackerHttpPort() {
        return httpTrackerHttpPort;
    }

    public void setHttpTrackerHttpPort(Integer httpTrackerHttpPort) {
        this.httpTrackerHttpPort = httpTrackerHttpPort;
    }

    public String getTrackerServers() {
        return trackerServers;
    }

    public void setTrackerServers(String trackerServers) {
        this.trackerServers = trackerServers;
    }

    public long getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(long minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public long getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(long maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public long getWaitTimes() {
        return waitTimes;
    }

    public void setWaitTimes(long waitTimes) {
        this.waitTimes = waitTimes;
    }

    public Integer getRetryConnectTimes() {
        return retryConnectTimes;
    }

    public void setRetryConnectTimes(Integer retryConnectTimes) {
        this.retryConnectTimes = retryConnectTimes;
    }
}
