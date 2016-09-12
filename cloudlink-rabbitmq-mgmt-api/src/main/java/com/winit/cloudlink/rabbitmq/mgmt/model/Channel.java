package com.winit.cloudlink.rabbitmq.mgmt.model;

/**
 * Created by xiangyu.liang on 2016/1/7.
 */
public class Channel {
    private String name;
    private String state;
    private String node;
    private String running;
    public Channel(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }
    public boolean isRunning()
    {
        if(this.state!=null && "running".equals(this.running))
        {
            return true;
        }
        return false;
    }
}
