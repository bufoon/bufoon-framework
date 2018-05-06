package com.bufoon.commons.base;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/5/5 23:29
 * @Desc: as follows.
 */
@ConfigurationProperties(prefix = "bufoon.proInfo")
public class ProjectInfo implements Serializable {
    private static final long serialVersionUID = 3406638820019105121L;
    /**
     * 项目ID
     */
    private String id;
    /**
     * 项目名
     */
    private String name;
    /**
     * 项目描述
     */
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
