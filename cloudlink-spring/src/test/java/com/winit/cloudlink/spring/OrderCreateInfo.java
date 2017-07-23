package com.winit.cloudlink.spring;

import java.io.Serializable;

public class OrderCreateInfo implements Serializable {

    public static final String MESSAGE_TYPE = "OrderCreateInfo";

    private String             orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
