package com.winit.cloudlink.event;

import java.io.Serializable;

public class StatusEvent implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1841626948816151761L;

    private String            refEventId;

    private String            status;

    private String            operation;

    public StatusEvent(){

    }

    public StatusEvent(String status, String operation, String refEventId){
        this.status = status;
        this.operation = operation;
        this.refEventId = refEventId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRefEventId() {
        return refEventId;
    }

    public void setRefEventId(String refEventId) {
        this.refEventId = refEventId;
    }

    public static enum EventHandlerResult {
        SUCCESS, FAIL;
    }

    @Override
    public String toString() {
        return "StatusEvent [refEventId=" + refEventId + ", status=" + status + ", operation=" + operation + "]";
    }

}
