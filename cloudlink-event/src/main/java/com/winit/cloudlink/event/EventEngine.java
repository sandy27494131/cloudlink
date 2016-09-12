package com.winit.cloudlink.event;

import java.io.Serializable;

import com.winit.cloudlink.common.Lifecycle;
import com.winit.cloudlink.message.MessageEngine.HandlerRegisterCallback;

public interface EventEngine extends Lifecycle {

    <Payload extends Serializable> void publishEvent(Event<Payload> event);

    <Payload extends Serializable> void registerEventListener(EventListener<Event<Payload>> eventListener,final HandlerRegisterCallback callback);

    <Payload extends Serializable> void unRegisterEventListener(EventListener<Event<Payload>> eventListener);


    <Payload> EventBuilder<Payload> newEventBuilder();
}
