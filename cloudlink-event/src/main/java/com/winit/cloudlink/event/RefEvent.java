package com.winit.cloudlink.event;

/**
 * Created by stvli on 2016/6/3.
 */
public class RefEvent<T> extends Event<T> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4897111009422150785L;
    private String            refEventId;

    public String getRefEventId() {
        return refEventId;
    }

    public void setRefEventId(String refEventId) {
        this.refEventId = refEventId;
    }
}
