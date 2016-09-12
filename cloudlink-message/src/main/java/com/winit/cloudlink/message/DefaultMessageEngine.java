package com.winit.cloudlink.message;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.common.collect.Maps;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.handler.MessageHandler;
import com.winit.cloudlink.message.handler.MessageHandler.HandlerType;
import com.winit.cloudlink.message.internal.listener.ListenerContainer;
import com.winit.cloudlink.message.internal.rabbitmq.RabbitmqListenerContainer;
import com.winit.cloudlink.message.internal.rabbitmq.RabbitmqMessageTemplate;

/**
 * Created by stvli on 2015/11/10.
 */
public class DefaultMessageEngine implements MessageEngine {

    private final Metadata                       metadata;
    private final MessageTemplate                messageTemplate;

    private final Map<String, ListenerContainer> listenerContainers = Maps.newConcurrentMap();
    private ReadWriteLock                        lock               = new ReentrantReadWriteLock();

    public DefaultMessageEngine(Metadata metadata){
        this.metadata = metadata;
        this.messageTemplate = new RabbitmqMessageTemplate(metadata);
    }

    @Override
    public void send(Message message) {
        messageTemplate.send(message);
    }

    @Override
    public void register(MessageHandler<? extends Message> messageHandler, HandlerRegisterCallback callback) {
        if (messageHandler == null) {
            return;
        }
        lock.readLock().lock();
        RabbitmqListenerContainer container = new RabbitmqListenerContainer(metadata, this, messageHandler,messageTemplate.getConnectionFactory());
        listenerContainers.put(messageHandler.getMessageType(), container);
        container.start();

        if (callback != null) {
            callback.onCompleted(HandlerType.MESSAGE, messageHandler.getMessageType());
        }
        lock.readLock().lock();
    }

    @Override
    public void unregister(MessageHandler<? extends Message> messageHandler) {
        if (messageHandler == null) {
            return;
        }
        lock.readLock().lock();
        ListenerContainer container = listenerContainers.remove(messageHandler.getMessageType());
        if (null != container) {
            container.shutdown();
        }
        lock.readLock().lock();
    }

    @Override
    public void registerMonitor(MessageMonitor messageMonitor,ExchangeType messageTag) {
        if (messageMonitor == null) {
            return;
        }
    }

    @Override
    public MessageBuilder newMessageBuilder() {
        return new MessageBuilder(metadata);
    }

    @Override
    public void start() {
        lock.readLock().lock();
        Collection<ListenerContainer> containers = listenerContainers.values();

        for (ListenerContainer container : containers) {
            container.start();
        }
        lock.readLock().lock();
    }

    @Override
    public void shutdown() {
        lock.writeLock().lock();
        Collection<ListenerContainer> containers = listenerContainers.values();

        for (ListenerContainer container : containers) {
            container.shutdown();
        }
        containers.clear();
        lock.writeLock().unlock();
    }

    @Override
    public void registerMessageReturnedListener(MessageReturnedListener listener) {
        messageTemplate.setMessageReturnedListener(listener);
    }
}
