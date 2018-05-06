package com.bufoon.storage.zk.support;

import com.bufoon.commons.utils.StringUtils;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/1/16 13:36
 * @Desc: as follows.
 * 泛型节点值改变监听器
 */
public class NodeCacheGenericListener<V> implements NodeCacheListener {

    private V defaultValue;// 默认值
    private NodeCache nodeCache; // nodeCache
    private AtomicReference<V> v; // 原子引用
    private V lastValue;

    public NodeCacheGenericListener(AtomicReference<V> v,  V defaultValue, NodeCache nodeCache){
        this.v = v;
        this.defaultValue = defaultValue;
        this.nodeCache = nodeCache;
    }

    @Override
    public void nodeChanged() throws Exception {
        V argObj = (V) new String(nodeCache.getCurrentData().getData(), Charset.defaultCharset());
        if (StringUtils.isEmpty(argObj)){
            argObj = defaultValue;
        }

        V o = null;
        if (defaultValue instanceof Byte) {
            o = (V) (Byte.valueOf(String.valueOf(argObj)));
        } else if (defaultValue instanceof Boolean) {
            boolean bol = "1".equals(String.valueOf(argObj)) || "true".equalsIgnoreCase(String.valueOf(argObj));
            o = (V) (Boolean.valueOf(bol));
        } else if (defaultValue instanceof Short) {
            o = (V) (Short.valueOf(String.valueOf(argObj)));
        } else if (defaultValue instanceof Integer) {
            o = (V) (Integer.valueOf(String.valueOf(argObj)));
        } else if (defaultValue instanceof Long) {
            o = (V) (Long.valueOf(String.valueOf(argObj)));
        } else if (defaultValue instanceof Float) {
            o = (V) (Float.valueOf(String.valueOf(argObj)));
        } else if (defaultValue instanceof Double) {
            o = (V) (Double.valueOf(String.valueOf(argObj)));
        } else if (defaultValue instanceof BigInteger) {
            o = (V) (new BigInteger(String.valueOf(argObj)));
        } else if (defaultValue instanceof BigDecimal) {
            o = (V) (new BigDecimal(String.valueOf(argObj)));
        } else {
            o = (V) argObj;
        }
        lastValue = o;
        v.set(o);
    }

    public V getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    public NodeCache getNodeCache() {
        return nodeCache;
    }

    public void setNodeCache(NodeCache nodeCache) {
        this.nodeCache = nodeCache;
    }

    public AtomicReference<V> getV() {
        return v;
    }

    public void setV(AtomicReference<V> v) {
        this.v = v;
    }

    public V getLastValue() {
        return lastValue;
    }

    public void setLastValue(V lastValue) {
        this.lastValue = lastValue;
    }
}
