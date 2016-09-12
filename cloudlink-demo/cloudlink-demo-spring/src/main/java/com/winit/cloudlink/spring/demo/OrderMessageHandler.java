package com.winit.cloudlink.spring.demo;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.winit.cloudlink.demo.core.OrderCreateInfo;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.MessageHeaders;
import com.winit.cloudlink.message.handler.MessageHandler;

@Service
@Lazy(false)
public class OrderMessageHandler implements MessageHandler<Message<OrderCreateInfo>> {

    @Override
    public String getMessageType() {
        return OrderCreateInfo.COMMAND_NAME;
    }

    @Override
    public void process(Message<OrderCreateInfo> message) {
        MessageHeaders headers = message.getHeaders(); // 消息头

        OrderCreateInfo orderCreateInfo = message.getPayload(); // 订单创建信息
    }

}
