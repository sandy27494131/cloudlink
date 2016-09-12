package com.winit.cloudlink.rabbitmq.mgmt.model.shovel;

public class ShovelLink {

    protected String vhost;
    protected String component;
    protected String name;
    ShovelOptions    value;

    public ShovelLink(){
    }

    public String getVhost() {
        return vhost;
    }

    public void setVhost(String vhost) {
        this.vhost = vhost;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShovelOptions getValue() {
        return value;
    }

    public void setValue(ShovelOptions value) {
        this.value = value;
    }

    public boolean isOnConfirm() {
        if (null != value && "on-confirm".equals(value.getAckMode())) {
            return true;
        }
        return false;
    }

    public boolean isNeverAutoDelete() {
        if (null != value && "never".equals(value.getAutoDelete())) {
            return true;
        }
        return false;
    }

}
