package com.winit.cloudlink.spring;

import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.annotation.Block;
import com.winit.cloudlink.message.handler.MessageHandler;

@Block(false)
public class OrderCreateMessageHandler implements MessageHandler<Message<OrderCreateInfo>> {

    @Override
    public String getMessageType() {
        return OrderCreateInfo.MESSAGE_TYPE;
    }

    @Override
    public void process(Message<OrderCreateInfo> message) {
        OrderCreateInfo order = message.getPayload();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + order.getOrderId());
        //throw new RuntimeException("orderCreate custom exception");
    }

}
