package com.winit.cloudlink.event;

import java.io.Serializable;

/**
 * Created by stvli on 2015/11/12.
 */
public abstract class Event<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3937830492118872201L;
    private EventHeaders      headers;
    private T                 payload;

    public EventHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(EventHeaders headers) {
        this.headers = headers;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
