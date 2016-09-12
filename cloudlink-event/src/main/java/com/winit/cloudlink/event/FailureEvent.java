package com.winit.cloudlink.event;

/**
 * Created by stvli on 2016/6/3.
 */
public class FailureEvent<T> extends RefEvent<T> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7690705981288875832L;

    @Override
    public String toString() {
        return "FailureEvent [getRefEventId()=" + getRefEventId() + ", getHeaders()=" + getHeaders()
               + ", getPayload()=" + getPayload() + "]";
    }

}
