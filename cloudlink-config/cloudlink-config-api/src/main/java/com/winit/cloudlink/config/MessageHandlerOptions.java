package com.winit.cloudlink.config;

/**
 * Created by stvli on 2015/11/4.
 */
public class MessageHandlerOptions {
    private String messageType;
    private Class<?> clazz;
    public MessageHandlerOptions() {
    }

    public MessageHandlerOptions(String messageType, Class<?> clazz) {
        this.messageType = messageType;
        this.clazz = clazz;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
