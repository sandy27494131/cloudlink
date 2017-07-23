package com.winit.cloudlink.command;

import java.io.Serializable;

import com.winit.cloudlink.command.internal.CommandCallbackMessageHandler;
import com.winit.cloudlink.command.internal.CommandMessageHandler;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.DefaultMessageEngine;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageBuilder;
import com.winit.cloudlink.message.MessageEngine;
import com.winit.cloudlink.message.MessageEngine.HandlerRegisterCallback;
import com.winit.cloudlink.message.handler.MessageHandler.HandlerType;

public class DefaultCommandEngine implements CommandEngine {

    private Metadata      metadata;
    private MessageEngine messageEngine;

    public DefaultCommandEngine(Metadata metadata){
        this(metadata, new DefaultMessageEngine(metadata));
    }

    public DefaultCommandEngine(Metadata metadata, MessageEngine messageEngine){
        this.metadata = metadata;
        this.messageEngine = messageEngine;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public MessageEngine getMessageEngine() {
        return messageEngine;
    }

    @Override
    public <Payload extends Serializable> void commitCommand(Command<Payload> command) {
        CommandHeaders commandHeaders = command.getHeaders();
        Message<?> message = new MessageBuilder(metadata).direct(commandHeaders.getToAppId(),
            commandHeaders.getCommandName(),
            command).build();
        messageEngine.send(message);
    }

    @Override
    public <Payload extends Serializable> void registerCommandExecutor(CommandExecutor<Command<Payload>> commandExecutor,
                                                                       final HandlerRegisterCallback callback) {
        CommandMessageHandler<Command<Payload>> messageHandler = new CommandMessageHandler<Command<Payload>>(commandExecutor,
            this.messageEngine,
            metadata);
        messageEngine.register(messageHandler, new HandlerRegisterCallback() {

            @Override
            public void onCompleted(HandlerType handlerType, String messageType) {
                if (null != callback) {
                    callback.onCompleted(HandlerType.COMMAND, messageType);
                }
            }
        });
    }

    @Override
    public <Payload extends Serializable> void unRegisterCommandExecutor(CommandExecutor<Command<Payload>> commandExecutor) {
        CommandMessageHandler<Command<Payload>> messageHandler = new CommandMessageHandler<Command<Payload>>(commandExecutor,
            this.messageEngine,
            metadata);
        messageEngine.unregister(messageHandler);
    }

    @Override
    public <Payload extends Serializable> void registerCommandCallback(CommandCallback<Command<Payload>> commandCallback,
                                                                       final HandlerRegisterCallback callback) {
        CommandCallbackMessageHandler<Command<Payload>> messageHandler = new CommandCallbackMessageHandler<Command<Payload>>(commandCallback,
            this.messageEngine, metadata);
        messageEngine.register(messageHandler, new HandlerRegisterCallback() {

            @Override
            public void onCompleted(HandlerType handlerType, String messageType) {
                if (null != callback) {
                    callback.onCompleted(HandlerType.COMMAND, messageType);
                }
            }
        });
    }

    @Override
    public <Payload extends Serializable> void unRegisterCommandCallback(CommandCallback<Command<Payload>> commandCallback) {
        CommandCallbackMessageHandler<Command<Payload>> messageHandler = new CommandCallbackMessageHandler<Command<Payload>>(commandCallback,
                this.messageEngine, metadata);
        messageEngine.unregister(messageHandler);
    }

    @Override
    public <Param> CommandBuilder<Param> newCommandBuilder() {
        return new CommandBuilder<Param>(metadata);
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
