package com.winit.cloudlink.message.handler;

import com.winit.cloudlink.message.Message;

public interface MessageHandler<M extends Message> {

    String getMessageType();

    void process(M message);

    public static enum HandlerType {
        MESSAGE, COMMAND, EVENT;
    }
}
