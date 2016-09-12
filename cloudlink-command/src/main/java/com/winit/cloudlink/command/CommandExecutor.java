package com.winit.cloudlink.command;

import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageBuilder;
import com.winit.cloudlink.message.MessageEngine;

public abstract class CommandExecutor<C extends Command<?>> {

    private MessageEngine messageEngine;

    private Metadata      metadata;

    /**
     * 不能为空；将通过该名称调用指令
     *
     * @return 指令名称
     */
    public abstract String getCommandName();

    /**
     * 指令处理
     *
     * @param command
     * @return
     * @throws Exception
     */
    public abstract void onReceive(C command) throws CommandException;

    @SuppressWarnings("unchecked")
    public <Payload> void callback(String toAppId, Payload payload) {
        Command<Payload> callbackCommand = new CommandBuilder<Payload>(getMetadata()).toAppId(toAppId)
            .commandName(getCallbackCommandType())
            .payload(payload)
            .build();

        Message<?> callbackMessage = new MessageBuilder(metadata).direct(callbackCommand.getHeaders().getToAppId(),
            getCallbackCommandType(),
            callbackCommand).build();

        messageEngine.send(callbackMessage);
    }

    public MessageEngine getMessageEngine() {
        return messageEngine;
    }

    public void setMessageEngine(MessageEngine messageEngine) {
        this.messageEngine = messageEngine;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    private String getCallbackCommandType() {
        return this.getCommandName() + Constants.COMMAND_CALLBACK_SUFFIX;
    }

}
