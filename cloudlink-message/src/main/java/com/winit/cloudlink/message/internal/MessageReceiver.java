package com.winit.cloudlink.message.internal;

import com.winit.cloudlink.common.Clearable;
import com.winit.cloudlink.message.handler.MessageHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by stvli on 2015/11/7.
 */
public class MessageReceiver implements Clearable {
    private Map<String, MessageHandler> messageHandlers = new ConcurrentHashMap<String, MessageHandler>();

    public void addMessageHandler(MessageHandler messageHandler) {
        messageHandlers.put(messageHandler.getMessageType(), messageHandler);
    }

    public MessageHandler getMessageHandler(String messageType) {
        return messageHandlers.get(messageType);
    }

    @Override
    public void clear() {
        messageHandlers.clear();
    }
}
