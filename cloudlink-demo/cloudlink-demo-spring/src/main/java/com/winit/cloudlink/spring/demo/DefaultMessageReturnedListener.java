package com.winit.cloudlink.spring.demo;

import org.springframework.amqp.core.Message;

import com.winit.cloudlink.message.MessageReturnedListener;


public class DefaultMessageReturnedListener implements MessageReturnedListener {

    @Override
    public void onReturned(Message message) {
        System.out.println("" + message.getBody());

    }

}
