package com.winit.cloudlink.message;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

public interface MessageTemplate {

    void send(Message<?> message);

    ConnectionFactory getConnectionFactory();

    void setMessageReturnedListener(MessageReturnedListener listener);

}
