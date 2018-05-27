package com.bufoon.protocol.rmq.prop;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/5/12 14:01
 * @Desc: as follows.
 */
@ConfigurationProperties(prefix = "bufoon.protocol.rmq")
public class RMQProperties {

    /**
     * 内部应用配置
     */
    private RabbitProperties inner;

    /**
     * 外部接入应用配置
     */
    private Map<String, RabbitProperties> outer;

    public RabbitProperties getInner() {
        return inner;
    }

    public void setInner(RabbitProperties inner) {
        this.inner = inner;
    }

    public Map<String, RabbitProperties> getOuter() {
        return outer;
    }

    public void setOuter(Map<String, RabbitProperties> outer) {
        this.outer = outer;
    }
}
