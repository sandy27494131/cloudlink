package com.winit.cloudlink.event;

/**
 * Created by stvli on 2016/6/3.
 */
public class SuccessEvent<T> extends RefEvent<T> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1690912763108702784L;

    @Override
    public String toString() {
        return "SuccessEvent [getRefEventId()=" + getRefEventId() + ", getHeaders()=" + getHeaders()
               + ", getPayload()=" + getPayload() + "]";
    }

}
