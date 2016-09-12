package com.winit.cloudlink.event;

/**
 * Created by stvli on 2016/6/3.
 */
public class RetryEvent<T> extends RefEvent<T> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -523250097042414755L;

    private String            to;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "RetryEvent [to=" + to + ", getRefEventId()=" + getRefEventId() + ", getHeaders()=" + getHeaders()
               + ", getPayload()=" + getPayload() + "]";
    }

}
