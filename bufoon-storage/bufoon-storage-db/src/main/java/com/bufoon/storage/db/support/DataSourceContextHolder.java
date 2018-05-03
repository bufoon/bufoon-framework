package com.bufoon.storage.db.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/4/7 22:00
 * @Desc: as follows.
 */
public class DataSourceContextHolder {
    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    /**
     * 默认数据源
     */
    public static final DSEnum DEFAULT_DS = DSEnum.MASTER;

    private static final ThreadLocal<DSEnum> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDB(DSEnum dsEnum) {
        log.debug("切换到{}数据源", dsEnum);
        contextHolder.set(dsEnum);
    }

    // 获取数据源名
    public static DSEnum getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}
