package com.bufoon.protocol.rmq;

import com.bufoon.commons.base.ProjectInfo;
import com.bufoon.protocol.rmq.prop.OuterContext;
import com.bufoon.protocol.rmq.prop.RMQProperties;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.DirectRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.time.Duration;
import java.util.Map;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/5/12 13:48
 * @Desc: as follows.
 */
@Configuration
@EnableConfigurationProperties(RMQProperties.class)
public class RabbitMqConfig {

    private RMQProperties rmqProperties;
    private ProjectInfo projectInfo;
    private ObjectProvider<MessageConverter> messageConverter;
    private ObjectProvider<MessageRecoverer> messageRecoverer;
    private ObjectProvider<ConnectionNameStrategy> connectionNameStrategy;

    public RabbitMqConfig(RMQProperties rmqProperties,
                          ObjectProvider<ProjectInfo> projectInfoObjectProvider,
                          ObjectProvider<MessageConverter> messageConverterObjectProvider,
                          ObjectProvider<MessageRecoverer> messageRecoverer,
                          ObjectProvider<ConnectionNameStrategy> connectionNameStrategy){
        this.rmqProperties = rmqProperties;
        this.projectInfo = projectInfoObjectProvider.getIfAvailable();
        this.messageConverter = messageConverterObjectProvider;
        this.messageRecoverer = messageRecoverer;
        this.connectionNameStrategy = connectionNameStrategy;
    }

    @Bean
    @Primary
    public RabbitProperties rabbitProperties() {
        return this.rmqProperties.getInner();
    }

    @Bean
    public OuterContext outerContext() throws Exception {
        Map<String, RabbitProperties> outer = this.rmqProperties.getOuter();
        SimpleRabbitListenerContainerFactoryConfigurer simpleConfigurer = new SimpleRabbitListenerContainerFactoryConfigurer();
        DirectRabbitListenerContainerFactoryConfigurer directConfigurer = new DirectRabbitListenerContainerFactoryConfigurer();
        OuterContext outerContext = new OuterContext();
        for (Map.Entry<String, RabbitProperties> entry : outer.entrySet()){
            CachingConnectionFactory connectionFactory = rabbitConnectionFactory(entry.getValue());
            outerContext.getOuterRabbitTemplate().put(entry.getKey(), rabbitTemplate(connectionFactory, entry.getValue()));
            outerContext.getOuterAdmin().put(entry.getKey(), amqpAdmin(connectionFactory));
            outerContext.getOuterSimpleFactory().put(entry.getKey(), simpleRabbitListenerContainerFactory(simpleConfigurer, connectionFactory));
            outerContext.getOuterDirectFactory().put(entry.getKey(), directRabbitListenerContainerFactory(directConfigurer, connectionFactory));
        }
        return outerContext;
    }

    private CachingConnectionFactory rabbitConnectionFactory(
            RabbitProperties properties)
            throws Exception {
        PropertyMapper map = PropertyMapper.get();
        CachingConnectionFactory factory = new CachingConnectionFactory(
                getRabbitConnectionFactoryBean(properties).getObject());
        map.from(properties::determineAddresses).to(factory::setAddresses);
        map.from(properties::isPublisherConfirms).to(factory::setPublisherConfirms);
        map.from(properties::isPublisherReturns).to(factory::setPublisherReturns);
        RabbitProperties.Cache.Channel channel = properties.getCache().getChannel();
        map.from(channel::getSize).whenNonNull().to(factory::setChannelCacheSize);
        map.from(channel::getCheckoutTimeout).whenNonNull().as(Duration::toMillis)
                .to(factory::setChannelCheckoutTimeout);
        RabbitProperties.Cache.Connection connection = properties.getCache()
                .getConnection();
        map.from(connection::getMode).whenNonNull().to(factory::setCacheMode);
        map.from(connection::getSize).whenNonNull()
                .to(factory::setConnectionCacheSize);
        map.from(connectionNameStrategy::getIfUnique).whenNonNull()
                .to(factory::setConnectionNameStrategy);
        return factory;
    }

    private RabbitConnectionFactoryBean getRabbitConnectionFactoryBean(
            RabbitProperties properties) throws Exception {
        PropertyMapper map = PropertyMapper.get();
        RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
        map.from(properties::determineHost).whenNonNull().to(factory::setHost);
        map.from(properties::determinePort).to(factory::setPort);
        map.from(properties::determineUsername).whenNonNull()
                .to(factory::setUsername);
        map.from(properties::determinePassword).whenNonNull()
                .to(factory::setPassword);
        map.from(properties::determineVirtualHost).whenNonNull()
                .to(factory::setVirtualHost);
        map.from(properties::getRequestedHeartbeat).whenNonNull()
                .asInt(Duration::getSeconds).to(factory::setRequestedHeartbeat);
        RabbitProperties.Ssl ssl = properties.getSsl();
        if (ssl.isEnabled()) {
            factory.setUseSSL(true);
            map.from(ssl::getAlgorithm).whenNonNull().to(factory::setSslAlgorithm);
            map.from(ssl::getKeyStoreType).to(factory::setKeyStoreType);
            map.from(ssl::getKeyStore).to(factory::setKeyStore);
            map.from(ssl::getKeyStorePassword).to(factory::setKeyStorePassphrase);
            map.from(ssl::getTrustStoreType).to(factory::setTrustStoreType);
            map.from(ssl::getTrustStore).to(factory::setTrustStore);
            map.from(ssl::getTrustStorePassword).to(factory::setTrustStorePassphrase);
        }
        map.from(properties::getConnectionTimeout).whenNonNull()
                .asInt(Duration::toMillis).to(factory::setConnectionTimeout);
        factory.afterPropertiesSet();
        return factory;
    }
    private RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, RabbitProperties rabbitProperties) {
        PropertyMapper map = PropertyMapper.get();
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        if (this.messageConverter != null) {
            template.setMessageConverter(messageConverter.getIfUnique());
        }
        template.setMandatory(determineMandatoryFlag(rabbitProperties));
        RabbitProperties.Template properties = rabbitProperties.getTemplate();
        if (properties.getRetry().isEnabled()) {
            template.setRetryTemplate(createRetryTemplate(properties.getRetry()));
        }
        map.from(properties::getReceiveTimeout).whenNonNull().as(Duration::toMillis)
                .to(template::setReceiveTimeout);
        map.from(properties::getReplyTimeout).whenNonNull().as(Duration::toMillis)
                .to(template::setReplyTimeout);
        map.from(properties::getExchange).to(template::setExchange);
        map.from(properties::getRoutingKey).to(template::setRoutingKey);
        return template;
    }

    private boolean determineMandatoryFlag(RabbitProperties properties) {
        Boolean mandatory = properties.getTemplate().getMandatory();
        return (mandatory != null ? mandatory : properties.isPublisherReturns());
    }

    private RetryTemplate createRetryTemplate(RabbitProperties.Retry properties) {
        PropertyMapper map = PropertyMapper.get();
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy policy = new SimpleRetryPolicy();
        map.from(properties::getMaxAttempts).to(policy::setMaxAttempts);
        template.setRetryPolicy(policy);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        map.from(properties::getInitialInterval).whenNonNull().as(Duration::toMillis)
                .to(backOffPolicy::setInitialInterval);
        map.from(properties::getMultiplier).to(backOffPolicy::setMultiplier);
        map.from(properties::getMaxInterval).whenNonNull().as(Duration::toMillis)
                .to(backOffPolicy::setMaxInterval);
        template.setBackOffPolicy(backOffPolicy);
        return template;
    }

    private AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    private SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    private DirectRabbitListenerContainerFactory directRabbitListenerContainerFactory(DirectRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

}
