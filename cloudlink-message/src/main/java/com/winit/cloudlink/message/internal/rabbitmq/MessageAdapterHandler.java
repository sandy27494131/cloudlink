package com.winit.cloudlink.message.internal.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.messaging.support.GenericMessage;

import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageEngine;
import com.winit.cloudlink.message.handler.MessageHandler;

/**
 * Created by stvli on 2015/11/10.
 */
@SuppressWarnings("rawtypes")
public class MessageAdapterHandler implements MessageListener {

    private final Logger         logger = LoggerFactory.getLogger(MessageAdapterHandler.class);
    private final MessageEngine  messageEngine;
    private final MessageHandler messageHandler;

    private MessageConverter     messageConverter;                                             // 直接指定

    public MessageAdapterHandler(MessageEngine messageEngine, MessageHandler messageHandler,
                                 MessageConverter messageConverter){
        this.messageEngine = messageEngine;
        this.messageHandler = messageHandler;
        this.messageConverter = messageConverter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(org.springframework.amqp.core.Message message) {
        Message targetMessage = buildMessage(message);
        messageHandler.process(targetMessage);

    }

    private Message buildMessage(org.springframework.amqp.core.Message originalMessage) {
        GenericMessage genericMessage = (GenericMessage) messageConverter.fromMessage(originalMessage);
        Message message = new Message(genericMessage);
        return message;
    }
}
