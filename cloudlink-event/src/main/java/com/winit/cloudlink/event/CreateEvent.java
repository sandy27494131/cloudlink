package com.winit.cloudlink.event;

/**
 * Created by stvli on 2016/6/3.
 */
public class CreateEvent<T> extends Event<T> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 947287804459714137L;

    @Override
    public String toString() {
        return "CreateEvent [getHeaders()=" + getHeaders() + ", getPayload()=" + getPayload() + "]";
    }

}
