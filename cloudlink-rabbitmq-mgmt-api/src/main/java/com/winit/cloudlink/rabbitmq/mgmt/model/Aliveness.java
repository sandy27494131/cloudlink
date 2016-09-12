package com.winit.cloudlink.rabbitmq.mgmt.model;

public class Aliveness {

    protected String status;

    public Aliveness(){
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOk() {
        if (null != status && "ok".equals(status)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Aliveness [status=" + status + "]";
    }

}
