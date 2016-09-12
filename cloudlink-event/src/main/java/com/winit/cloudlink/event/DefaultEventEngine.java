package com.winit.cloudlink.event;

import java.io.Serializable;

import com.winit.cloudlink.event.eventid.EventIdFactory;
import com.winit.cloudlink.event.internal.EventMessageHandler;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.DefaultMessageEngine;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageBuilder;
import com.winit.cloudlink.message.MessageEngine;
import com.winit.cloudlink.message.MessageEngine.HandlerRegisterCallback;
import com.winit.cloudlink.message.handler.MessageHandler.HandlerType;

public class DefaultEventEngine implements EventEngine {

    private Metadata      metadata;
    private MessageEngine messageEngine;

    public DefaultEventEngine(Metadata metadata){
        this(metadata, new DefaultMessageEngine(metadata));
    }

    public DefaultEventEngine(Metadata metadata, MessageEngine messageEngine){
        this.metadata = metadata;
        this.messageEngine = messageEngine;
        EventIdFactory.me().init(metadata);
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public MessageEngine getMessageEngine() {
        return messageEngine;
    }

    @Override
    public <Payload extends Serializable> void publishEvent(Event<Payload> event) {
        Message<?> message = new MessageBuilder(metadata).topic(event.getHeaders().getEventName(),
            event,
            event.getHeaders().getRoutingKey(),
            event.getHeaders().getZones()).build();
        messageEngine.send(message);
    }

    @Override
    public <Payload extends Serializable> void registerEventListener(EventListener<Event<Payload>> eventListener,
                                                                     final HandlerRegisterCallback callback) {
        EventMessageHandler<Event<Payload>> messageHandler = new EventMessageHandler<Event<Payload>>(eventListener,
            this,
            metadata);
        messageEngine.register(messageHandler, new HandlerRegisterCallback() {

            @Override
            public void onCompleted(HandlerType handlerType, String messageType) {
                if (null != callback) {
                    callback.onCompleted(HandlerType.EVENT, messageType);
                }
            }
        });
    }

    @Override
    public <Payload extends Serializable> void unRegisterEventListener(EventListener<Event<Payload>> eventListener) {
        EventMessageHandler<Event<Payload>> messageHandler = new EventMessageHandler<Event<Payload>>(eventListener,
            this,
            metadata);
        messageEngine.unregister(messageHandler);
    }

    @Override
    public <Param> EventBuilder<Param> newEventBuilder() {
        return new EventBuilder<Param>(metadata);
    }

    @Override
    public void start() {
        this.messageEngine.start();
    }

    @Override
    public void shutdown() {
        this.messageEngine.shutdown();
    }
}
