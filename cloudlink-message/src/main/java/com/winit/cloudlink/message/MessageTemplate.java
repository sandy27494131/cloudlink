package com.winit.cloudlink.message;

import com.winit.cloudlink.common.Lifecycle2;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

public interface MessageTemplate extends Lifecycle2 {

    void send(Message<?> message);

    ConnectionFactory getConnectionFactory();

    void setMessageReturnedListener(MessageReturnedListener listener);


}
