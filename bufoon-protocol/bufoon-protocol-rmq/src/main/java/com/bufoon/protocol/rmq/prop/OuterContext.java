package com.bufoon.protocol.rmq.prop;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/5/12 14:24
 * @Desc: as follows.
 * 外部配置上下文信息
 */
public class OuterContext {

    private Map<String, RabbitTemplate> outerRabbitTemplate = new HashMap<>();

    private Map<String, SimpleRabbitListenerContainerFactory> outerSimpleFactory = new HashMap<>();

    private Map<String, DirectRabbitListenerContainerFactory> outerDirectFactory = new HashMap<>();

    private Map<String, AmqpAdmin> outerAdmin = new HashMap<>();

    public Map<String, RabbitTemplate> getOuterRabbitTemplate() {
        return outerRabbitTemplate;
    }

    public void setOuterRabbitTemplate(Map<String, RabbitTemplate> outerRabbitTemplate) {
        this.outerRabbitTemplate = outerRabbitTemplate;
    }

    public Map<String, SimpleRabbitListenerContainerFactory> getOuterSimpleFactory() {
        return outerSimpleFactory;
    }

    public void setOuterSimpleFactory(Map<String, SimpleRabbitListenerContainerFactory> outerSimpleFactory) {
        this.outerSimpleFactory = outerSimpleFactory;
    }

    public Map<String, DirectRabbitListenerContainerFactory> getOuterDirectFactory() {
        return outerDirectFactory;
    }

    public void setOuterDirectFactory(Map<String, DirectRabbitListenerContainerFactory> outerDirectFactory) {
        this.outerDirectFactory = outerDirectFactory;
    }

    public Map<String, AmqpAdmin> getOuterAdmin() {
        return outerAdmin;
    }

    public void setOuterAdmin(Map<String, AmqpAdmin> outerAdmin) {
        this.outerAdmin = outerAdmin;
    }
}
