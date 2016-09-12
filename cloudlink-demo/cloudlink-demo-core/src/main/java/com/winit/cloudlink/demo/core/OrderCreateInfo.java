package com.winit.cloudlink.demo.core;

import java.io.Serializable;

public class OrderCreateInfo implements Serializable {

    public static final String COMMAND_NAME = "test_message_queue";

    private String             orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
