package com.winit.cloudlink.message.messageevent;

import java.io.Serializable;

public class MessageEvent implements Serializable {
    private String eventType;
    private Object target;

    public MessageEvent() {
    }

    public MessageEvent(String eventType, Object target) {
        this.eventType = eventType;
        this.target = target;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

}
