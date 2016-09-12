package com.winit.cloudlink.command;

import java.io.Serializable;

/**
 * Created by stvli on 2015/11/12.
 */
public class Command<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3937830492118872201L;
    private CommandHeaders headers;
    private T              payload;

    public CommandHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(CommandHeaders headers) {
        this.headers = headers;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
