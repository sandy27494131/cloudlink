package com.winit.cloudlink.message;

import com.google.common.collect.Maps;
import com.winit.cloudlink.common.Clearable;
import com.winit.cloudlink.common.Lifecycle;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.handler.MessageHandler;
import com.winit.cloudlink.message.handler.MessageHandler.HandlerType;
import com.winit.cloudlink.message.internal.listener.ListenerContainer;
import com.winit.cloudlink.message.internal.rabbitmq.RabbitmqListenerContainer;
import com.winit.cloudlink.message.internal.rabbitmq.RabbitmqMessageTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by stvli on 2015/11/10.
 */
public class DefaultMessageEngine implements MessageEngine {
    private final Metadata metadata;
    private final ListenerContainerHolder listenerContainerHolder = new ListenerContainerHolder();
    private MessageTemplate messageTemplate;
    private MessageTemplate oldMessageTemplate;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private AtomicBoolean destroyed = new AtomicBoolean(false);
    private AtomicBoolean actived = new AtomicBoolean(false);

    public DefaultMessageEngine(Metadata metadata) {
        this.metadata = metadata;
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
        RabbitmqListenerContainer container = new RabbitmqListenerContainer(metadata, this, messageHandler, messageTemplate.getConnectionFactory());
        container.start();
        listenerContainerHolder.addListenerContainer(messageHandler.getMessageType(), container);

        if (callback != null) {
            callback.onCompleted(HandlerType.MESSAGE, messageHandler.getMessageType());
        }
        lock.readLock().unlock();
    }

    @Override
    public void unregister(MessageHandler<? extends Message> messageHandler) {
        if (messageHandler == null) {
            return;
        }
        lock.readLock().lock();
        ListenerContainer container = listenerContainerHolder.removeListenerContainer(messageHandler.getMessageType());
        if (null != container) {
            container.shutdown();
        }
        lock.readLock().unlock();
    }

    @Override
    public void registerMonitor(MessageMonitor messageMonitor, ExchangeType messageTag) {
        if (messageMonitor == null) {
            return;
        }
    }

    @Override
    public void registerMessageReturnedListener(MessageReturnedListener listener) {
        messageTemplate.setMessageReturnedListener(listener);
    }

    @Override
    public MessageBuilder newMessageBuilder() {
        return new MessageBuilder(metadata);
    }


    @Override
    public void start() {
        active();
    }


    @Override
    public void active() {
        if (!actived.getAndSet(true)) {
            try {
                lock.writeLock().lock();
                messageTemplate = new RabbitmqMessageTemplate(metadata);
                messageTemplate.active();
                if (oldMessageTemplate != null) {
                    oldMessageTemplate.deactive();
                }
                listenerContainerHolder.resetConnectionFactory(messageTemplate.getConnectionFactory());
                listenerContainerHolder.shutdown();
                listenerContainerHolder.start();

            } finally {
                lock.writeLock().unlock();
            }
        }
    }

    @Override
    public void deactive() {
        if (actived.getAndSet(false)) {
            try {
                lock.writeLock().lock();
                listenerContainerHolder.shutdown();
                oldMessageTemplate = messageTemplate;
            } finally {
                lock.writeLock().unlock();
            }
        }
    }

    @Override
    public void shutdown() {
        if (!destroyed.getAndSet(true)) {
            deactive();
            try {
                lock.writeLock().lock();
                messageTemplate.shutdown();
                listenerContainerHolder.clear();
            } finally {
                lock.writeLock().unlock();
            }

        }
    }

    public boolean isActived() {
        return actived.get();
    }

    private class ListenerContainerHolder implements Lifecycle, Clearable {
        private final Map<String, ListenerContainer> listenerContainers = Maps.newConcurrentMap();

        protected void addListenerContainer(String messageType, ListenerContainer listenerContainer) {
            listenerContainers.put(messageType, listenerContainer);
        }

        public ListenerContainer removeListenerContainer(String messageType) {
            return listenerContainers.remove(messageType);
        }

        public void resetConnectionFactory(ConnectionFactory connectionFactory) {
            Collection<ListenerContainer> containers = listenerContainers.values();
            for (ListenerContainer container : containers) {
                if (container instanceof RabbitmqListenerContainer) {
                    ((RabbitmqListenerContainer) container).resetConnectionFactory(connectionFactory);
                }
            }
        }

        public void start() {
            Collection<ListenerContainer> containers = listenerContainers.values();
            for (ListenerContainer container : containers) {
                container.start();
            }
        }

        @Override
        public void shutdown() {
            Collection<ListenerContainer> containers = listenerContainers.values();
            for (ListenerContainer container : containers) {
                container.shutdown();
            }
        }

        @Override
        public void clear() {
            listenerContainers.clear();
        }
    }

}
