package com.winit.cloudlink.message.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by stvli on 2015/11/7.
 */
public class MessageSender {
    private Map<String, List<String>> messageConsumers = new ConcurrentHashMap<String, List<String>>();

    public void addMessageConsumer(String messageType, String consumer) {
        List<String> consumers = getMessageConsumers(messageType);
        if (consumers != null) {
            consumers = new ArrayList<String>();
            messageConsumers.put(messageType, consumers);
        }
        messageConsumers.put(messageType, consumers);
    }

    public List<String> getMessageConsumers(String messageType) {
        return messageConsumers.get(messageType);
    }
}
