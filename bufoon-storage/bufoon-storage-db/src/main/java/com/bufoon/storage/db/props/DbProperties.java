package com.bufoon.storage.db.props;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/4/7 21:50
 * @Desc: as follows.
 * 数据库属性设置
 */
@ConfigurationProperties(prefix = "bufoon.storage.db")
public class DbProperties {
    private DruidDataSource master;
    private DruidDataSource slave;
    private MybatisPlusProperties mybatisPlus;

    public DruidDataSource getMaster() {
        return master;
    }

    public void setMaster(DruidDataSource master) {
        this.master = master;
    }

    public DruidDataSource getSlave() {
        return slave;
    }

    public void setSlave(DruidDataSource slave) {
        this.slave = slave;
    }

    public MybatisPlusProperties getMybatisPlus() {
        return mybatisPlus;
    }

    public void setMybatisPlus(MybatisPlusProperties mybatisPlus) {
        this.mybatisPlus = mybatisPlus;
    }
}
