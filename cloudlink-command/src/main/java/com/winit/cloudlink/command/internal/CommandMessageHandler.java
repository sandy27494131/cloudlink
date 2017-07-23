package com.winit.cloudlink.command.internal;

import com.winit.cloudlink.common.MessageCategory;
import com.winit.cloudlink.message.exception.RetryableHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winit.cloudlink.command.CommandExecutor;
import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandException;
import com.winit.cloudlink.command.Constants;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageEngine;
import com.winit.cloudlink.message.handler.AbstractMessageHandler;

/**
 * Created by stvli on 2015/11/11.
 */
public class CommandMessageHandler<C extends Command<?>> extends AbstractMessageHandler<Message<C>> {

    private static final Logger logger = LoggerFactory.getLogger(CommandMessageHandler.class);

    private CommandExecutor<C>  command;
    private MessageEngine       messageEngine;
    private Metadata            metadata;

    public CommandMessageHandler(CommandExecutor<C> command, MessageEngine messageEngine, Metadata metadata){
        super(command.getClass(), metadata, messageEngine);
        command.setMessageEngine(messageEngine);
        command.setMetadata(metadata);
        this.command = command;
        this.messageEngine = messageEngine;
        this.metadata = metadata;
    }

    public String getMessageType() {
        return command.getCommandName() + Constants.COMMAND_SUFFIX;
    }

    @Override
    public void process(Message<C> message) {
        try {
            command.onReceive(message.getPayload());
        } catch (Throwable e) {
            String errorMsg = "Call [" + command.getClass().getName() + "] failed";
            logger.error(errorMsg, e);

            if (!isIgnoreException()) {
                throw new CommandException(errorMsg, e);
            } else {
                if (e instanceof RetryableHandlerException) {
                    retryHandler(message, MessageCategory.COMMAND, e);
                }
            }
        }
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public MessageEngine getMessageEngine() {
        return messageEngine;
    }

    public Metadata getMetadata() {
        return metadata;
    }
}
