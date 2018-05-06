package com.bufoon.storage.zk.service.impl;

import com.alibaba.fastjson.JSON;
import com.bufoon.commons.base.ProjectInfo;
import com.bufoon.commons.utils.StringUtils;
import com.bufoon.storage.zk.prop.CuratorProperties;
import com.bufoon.storage.zk.service.ZkService;
import com.bufoon.storage.zk.support.NodeCacheGenericListener;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/1/16 11:31
 * @Desc: as follows.
 * zookeeper 操作服务类
 */
@Service
public class ZkServiceImpl implements ZkService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectInfo projectInfo;
    @Autowired
    private CuratorFramework curatorFramework;
    @Autowired
    private CuratorProperties curatorProperties;

    @Override
    public String getData(String path) {
        byte[] data = null;
        try {
            data = curatorFramework.getData().forPath(getPath(path));
            return new String(data, Charset.defaultCharset());
        } catch (Exception e) {
            if (StringUtils.isEmpty(path) || path.indexOf("commons") > 0){
                logger.error("zk getData is error {}", ExceptionUtils.getStackTrace(e));
            }
        } finally {
            // 如果当前路径没有配置，则从公共模块路径获取
            if (data == null && StringUtils.isNotEmpty(path) && path.indexOf("commons") != -1){
                return getData(curatorProperties.getCommonPath() + "/" + path);
            }
        }
        return "";
    }

    @Override
    public <V> V getData(String path, AtomicReference<V> v, final V defaultValue) {
        V value = null;
        try {
            Object data = getData(path);
            if (defaultValue instanceof Byte) {
                value = (V) (Byte.valueOf(String.valueOf(data)));
            } else if (defaultValue instanceof Boolean) {
                boolean bol = "1".equals(String.valueOf(data)) || "true".equalsIgnoreCase(String.valueOf(data));
                value = (V) (Boolean.valueOf(bol));
            } else if (defaultValue instanceof Short) {
                value = (V) (Short.valueOf(String.valueOf(data)));
            } else if (defaultValue instanceof Long) {
                value = (V) (Long.valueOf(String.valueOf(data)));
            } else if (defaultValue instanceof Float) {
                value = (V) (Float.valueOf(String.valueOf(data)));
            } else if (defaultValue instanceof Double) {
                value = (V) (Double.valueOf(String.valueOf(data)));
            } else if (defaultValue instanceof BigDecimal) {
                value = (V) (new BigDecimal(String.valueOf(data)));
            } else if (defaultValue instanceof Integer) {
                value = (V) (Integer.valueOf(String.valueOf(data)));
            } else if (defaultValue instanceof BigInteger) {
                value = (V) (new BigInteger(String.valueOf(data)));
            } else {
                value = (V) data;
            }
            NodeCache nodeCache = new NodeCache(curatorFramework, getPath(path));
            nodeCache.getListenable().addListener(new NodeCacheGenericListener<>(v, defaultValue, nodeCache));
            nodeCache.start();
        } catch (Exception e) {
            if (StringUtils.isEmpty(path) || path.indexOf("commons") > 0){
                logger.error("data change error. {}", ExceptionUtils.getStackTrace(e));
            }
        } finally {
            // 如果当前路径没有配置，则从公共模块路径获取
            if (StringUtils.isEmpty(value) && StringUtils.isNotEmpty(path) && path.indexOf("commons") < 0){
                return getData(curatorProperties.getCommonPath() + "/" + path, v, defaultValue);
            }
        }
        return value;
    }

    @Override
    public void setData(String path, String data) {
        try {
            Stat stat = curatorFramework.setData().forPath(getPath(path), data.getBytes(Charset.defaultCharset()));
            logger.debug("zk set data: {}", JSON.toJSONString(stat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <V> void nodeDataChange(String path, AtomicReference<V> v, final V defaultValue) {
        try {
            NodeCache nodeCache = new NodeCache(curatorFramework, getPath(path));
            nodeCache.getListenable().addListener(new NodeCacheGenericListener<>(v, defaultValue, nodeCache));
            nodeCache.start();
        } catch (Exception e) {
            logger.error("data change error. {}", ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public boolean exists(String path) {

        try {
            Stat stat = curatorFramework.checkExists().forPath(getPath(path));
            return stat != null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("exists error.\n{}", ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    @Override
    public String mkDir(String path) {
        try {
            if (exists(path)){
                return "";
            }
            return mkDir(path, "");
        } catch (Exception e) {
            logger.error("mkDir error. \n{}", ExceptionUtils.getStackTrace(e));
        }
        return "";
    }

    @Override
    public String mkDir(String path, String data) {
        try {
            if (exists(getPath(path))){
                return "";
            }
            return curatorFramework.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(getPath(path), data.getBytes(Charset.defaultCharset()));
        } catch (Exception e) {
            logger.error("mkDir error. \n{}", ExceptionUtils.getStackTrace(e));
        }
        return "";
    }

    @Override
    public void delete(String path) {
        try {
            curatorFramework.delete().forPath(getPath(path));
        } catch (Exception e) {
            logger.error("zk delete error. \n{}", ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public List<String> getChildrens(String path) {
        try {
            return curatorFramework.getChildren().forPath(getPath(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList();
    }
    @Override
    public String getPath(String path){
        if (path.indexOf("AppConfig") != -1){
            return path;
        }
        return curatorProperties.getConfigPath() + "/" + path;
    }
}
