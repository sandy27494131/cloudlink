package com.winit.cloudlink.message.internal.mapping;

import com.winit.cloudlink.config.Metadata;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

/**
 * Created by stvli on 2016/6/4.
 */
public abstract class AbstractMappingStrategy implements MappingStrategy {

    protected Metadata    metadata;
    protected RabbitAdmin rabbitAdmin;

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        rabbitAdmin = new RabbitAdmin(connectionFactory);
    }

    @Override
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
