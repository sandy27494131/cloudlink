package com.winit.cloudlink.message.internal.support;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageTemplate;
import com.winit.cloudlink.message.exception.MessageSendException;
import com.winit.cloudlink.message.internal.MessageReceiver;
import com.winit.cloudlink.message.internal.MessageSender;

public abstract class AbstractMessageTemplate implements MessageTemplate {
    private static final Logger logger = LoggerFactory
            .getLogger(AbstractMessageTemplate.class);
    protected Metadata metadata;
    protected MessageSender messageSender;
    protected MessageReceiver messageReceiver;

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public MessageReceiver getMessageReceiver() {
        return messageReceiver;
    }

    public AbstractMessageTemplate(Metadata metadata) {
        this.metadata = metadata;
        init();
    }

    protected void init() {
        try {
            messageSender = buildMessageSender(metadata);
            messageReceiver = buildMessageReceiver(metadata);
        } catch (Exception e) {
            logger.error("MessageTemplate initialized error.", e);
            throw new MessageSendException("MessageTemplate initialized error.", e);
        }
    }

    protected MessageSender buildMessageSender(Metadata metadata) {
        MessageSender messageSender = new MessageSender();
        return messageSender;
    }

    private MessageReceiver buildMessageReceiver(Metadata metadata) {
        MessageReceiver messageReceiver = new MessageReceiver();
        return messageReceiver;
    }


    @Override
    public void send(Message message) {
        preHandle(message);
        try {
            sendInternal(message);
        } finally {
            postHandle(message);
        }
    }


    protected abstract void preHandle(Message message);

    protected abstract void postHandle(Message message);

    protected abstract void sendInternal(Message eventMessage);


}