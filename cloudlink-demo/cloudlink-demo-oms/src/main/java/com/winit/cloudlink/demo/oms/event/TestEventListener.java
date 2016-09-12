package com.winit.cloudlink.demo.oms.event;

import com.winit.cloudlink.event.CreateEvent;
import com.winit.cloudlink.event.EventException;
import com.winit.cloudlink.event.EventListener;
import com.winit.cloudlink.event.RetryEvent;

public class TestEventListener extends EventListener<TestEvent> {

    @Override
    public String getEventName() {
        return "TEST";
    }

    @Override
    public void onCreate(CreateEvent<TestEvent> event) throws EventException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>craete...." + event.getPayload().getName());
        throw new RuntimeException("处理失败");

    }

    @Override
    public void onRetry(RetryEvent<TestEvent> event) throws EventException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>retry...." + event.getPayload().getName());
        throw new RuntimeException("处理失败2");

    }

}
