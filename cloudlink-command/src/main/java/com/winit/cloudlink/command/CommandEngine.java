package com.winit.cloudlink.command;

import java.io.Serializable;

import com.winit.cloudlink.common.Lifecycle;
import com.winit.cloudlink.message.MessageEngine.HandlerRegisterCallback;

public interface CommandEngine extends Lifecycle {

    <Payload extends Serializable> void commitCommand(Command<Payload> command);

    <Payload extends Serializable> void registerCommandExecutor(CommandExecutor<Command<Payload>> commandExecutor,
                                                                final HandlerRegisterCallback callback);

    <Payload extends Serializable> void unRegisterCommandExecutor(CommandExecutor<Command<Payload>> commandExecutor);

    <Payload extends Serializable> void registerCommandCallback(CommandCallback<Command<Payload>> commandCallback,
                                                                final HandlerRegisterCallback callback);

    <Payload extends Serializable> void unRegisterCommandCallback(CommandCallback<Command<Payload>> commandCallback);

    <Payload> CommandBuilder<Payload> newCommandBuilder();
}
