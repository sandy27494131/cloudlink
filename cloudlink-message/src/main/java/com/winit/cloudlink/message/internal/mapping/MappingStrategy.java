package com.winit.cloudlink.message.internal.mapping;

import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.handler.MessageHandler;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

/**
 * Created by stvli on 2016/6/4.
 */
public interface MappingStrategy {

    void setMetadata(Metadata metadata);

    void setConnectionFactory(ConnectionFactory connectionFactory);

    String getSendRoutingKey(Message message);

    String getSendExchangeName(Message message);

    void initalQueue(MessageHandler messageHandler);

    String getQueueName(MessageHandler<? extends Message> messageHandler);
}
