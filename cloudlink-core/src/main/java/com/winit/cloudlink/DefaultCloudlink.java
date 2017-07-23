package com.winit.cloudlink;

import com.winit.cloudlink.command.*;
import com.winit.cloudlink.common.ConfigNode;
import com.winit.cloudlink.common.ConfigNode.NodeType;
import com.winit.cloudlink.common.Zone;
import com.winit.cloudlink.config.ApplicationOptions;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.event.*;
import com.winit.cloudlink.message.*;
import com.winit.cloudlink.message.MessageEngine.HandlerRegisterCallback;
import com.winit.cloudlink.message.handler.DefaultMessageHandler;
import com.winit.cloudlink.message.handler.MessageHandler;
import com.winit.cloudlink.message.handler.MessageHandler.HandlerType;
import com.winit.cloudlink.registry.NotifyListener;
import com.winit.cloudlink.registry.Registry;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by stvli on 2015/11/12.
 */
public class DefaultCloudlink implements Cloudlink {
    private Metadata metadata;
    private MessageEngine messageEngine;
    private CommandEngine commandEngine;
    private EventEngine eventEngine;
    private Registry registry;
    private final HandlerRegisterCallback callback = new DefaultRegisterCallback();

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private AtomicBoolean destroyed = new AtomicBoolean(false);
    private AtomicBoolean actived = new AtomicBoolean(false);

    public DefaultCloudlink(Metadata metadata, Registry registry) {
        this.metadata = metadata;
        this.registry = registry;
        messageEngine = new MessageEngineBuilder(metadata).build();
        commandEngine = new CommandEngineBuilder(metadata, messageEngine).build();
        eventEngine = new EventEngineBuilder(metadata, messageEngine).build();

    }

    @Override
    public void sendMessage(Message message) {
        try {
            lock.readLock().lock();
            messageEngine.send(message);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void commitCommand(Command command) {
        commandEngine.commitCommand(command);
    }

    @Override
    public void registerMessageHandler(MessageHandler messageHandler) {
        DefaultMessageHandler handler = new DefaultMessageHandler(messageHandler, this.messageEngine, metadata);
        messageEngine.register(handler, callback);
    }

    @Override
    public void unRegisterMessageHandler(MessageHandler messageHandler) {
        DefaultMessageHandler handler = new DefaultMessageHandler(messageHandler, this.messageEngine, metadata);
        messageEngine.unregister(handler);
    }

    @Override
    public void registerCommandExecutor(CommandExecutor commandExecutor) {
        commandEngine.registerCommandExecutor(commandExecutor, callback);
    }

    @Override
    public void unRegisterCommandExecutor(CommandExecutor commandExecutor) {
        commandEngine.unRegisterCommandExecutor(commandExecutor);
    }

    @Override
    public void registerCommandCallback(CommandCallback commandCallback) {
        commandEngine.registerCommandCallback(commandCallback, callback);
    }

    @Override
    public void unRegisterCommandCallback(CommandCallback commandCallback) {
        commandEngine.unRegisterCommandCallback(commandCallback);
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }

    @Override
    public MessageBuilder newMessageBuilder() {
        return new MessageBuilder(metadata);
    }

    @Override
    public <Payload> CommandBuilder<Payload> newCommandBuilder() {
        return commandEngine.newCommandBuilder();
    }

    @Override
    public void start() {
        // listenerRegisteredQueue();
        // commandEngine.start();
        // messageEngine.start();
        active();
    }

    @Override
    public void active() {
        if (!actived.getAndSet(true)) {
            try {
                lock.writeLock().lock();
                messageEngine.active();
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
                messageEngine.deactive();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }

    @Override
    public void shutdown() {
        if (!destroyed.getAndSet(true)) {
            try {
                lock.writeLock().lock();
                messageEngine.shutdown();
                commandEngine.shutdown();
                eventEngine.shutdown();
                if (null != registry) {
                    registry.destroy();
                }
            } finally {
                lock.writeLock().unlock();
            }

        }
    }

    public boolean isActived() {
        return actived.get();
    }

    @Override
    public void setMessageReturnedListener(MessageReturnedListener listener) {
        messageEngine.registerMessageReturnedListener(listener);
    }

    @Override
    public void publishEvent(Event event) {
        eventEngine.publishEvent(event);
    }

    @Override
    public void registerEventListener(EventListener eventListener) {
        eventEngine.registerEventListener(eventListener, callback);
    }

    @Override
    public void unRegisterEventListener(EventListener eventListener) {
        eventEngine.unRegisterEventListener(eventListener);
    }

    @Override
    public void registerEventMonitor(MessageMonitor messageMonitor) {

    }

    @Override
    public <Payload> EventBuilder<Payload> newEventBuilder() {
        return eventEngine.newEventBuilder();
    }

    private void listenerRegisteredQueue() {
        ConfigNode node = new ConfigNode(NodeType.ROOT, null, null, null, null);
        registry.subscribe(node, new NotifyListener() {
            @Override
            public void notify(List<ConfigNode> nodes) {

            }
        });
    }

    private class DefaultRegisterCallback implements HandlerRegisterCallback {
        @Override
        public void onCompleted(HandlerType handlerType, String messageType) {
            if (null != registry) {
                ConfigNode node = new ConfigNode();
                if (HandlerType.COMMAND.equals(handlerType)) {
                    node.setNodeType(NodeType.COMMAND);
                }
                if (HandlerType.MESSAGE.equals(handlerType)) {
                    node.setNodeType(NodeType.MESSAGE);
                }
                if (HandlerType.EVENT.equals(handlerType)) {
                    node.setNodeType(NodeType.EVENT);
                }
                ApplicationOptions options = metadata.getApplicationOptions();
                node.setZone(new Zone(options.getZone()));
                node.setOwner(options.getOwner());
                node.setAppId(options.getAppId2String());
                node.setNodeName(messageType);
                node.setDynamic(false);
                registry.register(node);
            }
        }
    }
}
