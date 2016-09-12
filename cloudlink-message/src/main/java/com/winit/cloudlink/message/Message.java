package com.winit.cloudlink.message;

import org.springframework.messaging.support.GenericMessage;

/**
 * Created by stvli on 2015/11/3.
 */
public class Message<T> extends GenericMessage<T> {
    private final MessageHeaders headers;

    public Message(T payload) {
        super(payload);
        headers = new MessageHeaders(null);
    }

    public Message(GenericMessage genericMessage) {
        super((T) genericMessage.getPayload());
        headers = new MessageHeaders(genericMessage.getHeaders());
    }

    @Override
    public MessageHeaders getHeaders() {
        return headers;
    }

    public Message(T payload, MessageHeaders headers) {
        super(payload, headers);
        this.headers = headers;
    }
}
