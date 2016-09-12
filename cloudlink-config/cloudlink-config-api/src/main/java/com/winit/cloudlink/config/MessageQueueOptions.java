package com.winit.cloudlink.config;

/**
 * Created by stvli on 2015/11/4.
 */
public class MessageQueueOptions {
    private String name;
    private String routingKey;
    private String messageType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
