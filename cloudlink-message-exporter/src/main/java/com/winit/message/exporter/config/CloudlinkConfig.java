package com.winit.message.exporter.config;

import com.winit.message.exporter.consumer.ExporterMessageListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by jianke.zhang on 2017/3/31.
 */
@Component
public class CloudlinkConfig {


    @Value("${spring.cloudlink.queue.name}")
    private String queueName;

    @Value("${spring.cloudlink.address}")
    private String address;

    @Value("${spring.cloudlink.virtualhost}")
    private String virtualHost;

    @Value("${spring.cloudlink.username}")
    private String username;

    @Value("${spring.cloudlink.password}")
    private String password;


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(address);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        if (StringUtils.isNotBlank(virtualHost)) {
            connectionFactory.setVirtualHost(virtualHost);
        } else {
            connectionFactory.setVirtualHost("/");
        }
        connectionFactory.setPublisherConfirms(true); //必须要设置
        return connectionFactory;
    }

    @Bean SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setConcurrentConsumers(1);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean MessageListenerAdapter listenerAdapter(ExporterMessageListener receiver) {
        return new MessageListenerAdapter(receiver);
    }
}
