package com.winit.cloudlink.command.internal;

import com.winit.cloudlink.common.MessageCategory;
import com.winit.cloudlink.message.MessageEngine;
import com.winit.cloudlink.message.exception.RetryableHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandCallback;
import com.winit.cloudlink.command.CommandException;
import com.winit.cloudlink.command.Constants;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.handler.AbstractMessageHandler;

/**
 * Created by stvli on 2015/11/11.
 */
public class CommandCallbackMessageHandler<C extends Command<?>> extends AbstractMessageHandler<Message<C>> {

    private static final Logger logger = LoggerFactory.getLogger(CommandCallbackMessageHandler.class);

    private CommandCallback<C>  commandCallback;

    private Metadata            metadata;

    public CommandCallbackMessageHandler(CommandCallback<C> commandCallback, MessageEngine messageEngine,
                                         Metadata metadata){
        super(commandCallback.getClass(), metadata, messageEngine);
        this.commandCallback = commandCallback;
        this.metadata = metadata;
    }

    @Override
    public String getMessageType() {
        return commandCallback.getCommandName() + Constants.COMMAND_CALLBACK_SUFFIX;
    }

    @Override
    public void process(Message<C> message) {
        try {
            C object = message.getPayload();
            commandCallback.onCallback(object);
        } catch (Throwable e) {
            String errorMsg = "Call [" + commandCallback.getClass().getName() + "] failed";
            logger.error(errorMsg, e);
            if (!isIgnoreException()) {
                throw new CommandException(errorMsg, e);
            } else {
                if (e instanceof RetryableHandlerException) {
                    retryHandler(message, MessageCategory.COMMAND_CALLBACK, e);
                }
            }
        }

    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
