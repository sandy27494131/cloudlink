package com.winit.cloudlink.message.handler;

import com.winit.cloudlink.common.MessageCategory;
import com.winit.cloudlink.message.MessageEngine;
import com.winit.cloudlink.message.exception.RetryableHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winit.cloudlink.common.CloudlinkException;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.Message;


public class DefaultMessageHandler<M extends Message<?>> extends AbstractMessageHandler<M> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageHandler.class);

    private MessageHandler<M>   messageHandler;

    public DefaultMessageHandler(MessageHandler<M> messageHandler, MessageEngine messageEngine, Metadata metadata){
        super(messageHandler.getClass(), metadata, messageEngine);
        this.messageHandler = messageHandler;
    }

    @Override
    public String getMessageType() {
        return messageHandler.getMessageType();
    }

    @Override
    public void process(M message) {
        try {
            messageHandler.process(message);
        } catch (Throwable e) {
            String errorMsg = "Call [" + messageHandler.getClass().getName() + "] failed.";
            logger.error(errorMsg, e);

            if (!isIgnoreException()) {
                throw new CloudlinkException(errorMsg, e);
            } else {
                if (e instanceof RetryableHandlerException) {
                    retryHandler(message, MessageCategory.MESSAGE, e);
                }
            }
        }
    }

}
