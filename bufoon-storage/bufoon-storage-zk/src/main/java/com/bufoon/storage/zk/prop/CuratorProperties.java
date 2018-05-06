package com.bufoon.storage.zk.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Zookeeper 客户端连接属性
 */
@ConfigurationProperties(prefix = "bufoon.storage.zookeeper")
public class CuratorProperties {
    private String zkAddress; //zk连接地址，多个用逗号隔开
    private int retryCount = 5; // 连接重试次数
    private int sleepTimes = 3000; // 重试间隔 毫秒
    private int sessionTimeout = 60*1000; // 会话超时时间，毫秒，默认60秒
    private int connectionTimeout = 30*1000; //建立连接超时时间，毫秒，默认15秒
    private String configPath; // 项目配置路径
    private String commonPath = "app-config/commons"; // 公共配置路径

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getSleepTimes() {
        return sleepTimes;
    }

    public void setSleepTimes(int sleepTimes) {
        this.sleepTimes = sleepTimes;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public String getCommonPath() {
        return commonPath;
    }

    public void setCommonPath(String commonPath) {
        this.commonPath = commonPath;
    }
}
