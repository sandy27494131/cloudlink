package com.winit.cloudlink.message.internal.listener;

import com.winit.cloudlink.config.Metadata;
import org.springframework.context.ApplicationContext;

import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageEngine;
import com.winit.cloudlink.message.handler.MessageHandler;

/**
 * Created by stvli on 2015/11/10.
 */
public abstract class AbstractListenerContainer implements ListenerContainer {

    protected final Metadata metadata;
    protected final MessageEngine messageEngine;
    protected ApplicationContext applicationContext;
    protected final MessageHandler<? extends Message> messageHandler;

    public AbstractListenerContainer(Metadata metadata, MessageEngine messageEngine,
                                     MessageHandler<? extends Message> messageHandler) {
        this.metadata = metadata;
        this.messageEngine = messageEngine;
        this.messageHandler = messageHandler;
    }

    public MessageEngine getMessageEngine() {
        return messageEngine;
    }

    public MessageHandler<? extends Message> getMessageHandler() {
        return messageHandler;
    }

    // rabbitMQ msg listener container
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public abstract void shutdown();

}
