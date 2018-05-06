package com.bufoon.storage.zk.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/1/16 15:03 
 * @Desc: as follows. 
 *  ZK 服务操作接口
 */
public interface ZkService {

    /**
     * 根据路径获取数据
     * @param path
     * @return
     */
    String getData(String path);

    /**
     * 获取数据，并设置监听路径数据变化
     * @param path
     * @param v
     * @param defaultValue
     * @param <V>
     * @return
     */
    <V> V getData(String path, AtomicReference<V> v, final V defaultValue);

    void setData(String path, String data);

    <V> void nodeDataChange(String path, AtomicReference<V> v, final V defaultValue);

    boolean exists(String path);

    String mkDir(String path);

    String mkDir(String path, String data);

    void delete(String path);

    List<String> getChildrens(String path);

    /**
     * 路径前缀+应用+path
     * @param path 要以斜杠 / 开头
     * @return
     */
    String getPath(String path);
}
