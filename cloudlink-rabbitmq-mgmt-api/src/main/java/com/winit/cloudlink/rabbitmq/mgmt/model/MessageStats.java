package com.winit.cloudlink.rabbitmq.mgmt.model;

/**
 * Created by xiangyu.liang on 2016/1/8.
 */
public class MessageStats {
    protected DeliverGetDetails deliver_get_details;

    public DeliverGetDetails getDeliverGetDetails() {
        return deliver_get_details;
    }

    public void setDeliverGetDetails(DeliverGetDetails deliver_get_details) {
        this.deliver_get_details = deliver_get_details;
    }
}
