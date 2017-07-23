package com.winit.cloudlink.demo.oms;

import java.util.Properties;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.Configuration;
import com.winit.cloudlink.demo.oms.event.TestEvent;
import com.winit.cloudlink.demo.oms.event.TestEventListener;
import com.winit.cloudlink.event.Event;
import com.winit.cloudlink.event.EventOperation;

public class StartupOmsCN2Event {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("appId", "OMS1.OMS.ALL.CNR");
        properties.setProperty("appType", "OMS");
        properties.setProperty("zone", "CNR");
        properties.setProperty("mq", "admin:admin@172.16.3.184:5672");
        Cloudlink cloudlink = new Configuration().configure(properties).getCloudlink();

        cloudlink.registerEventListener(new TestEventListener());

        // 构建一个指令对象（跨数据中心消息传递）

        TestEvent payload = new TestEvent("你好");
        Event<TestEvent> event = cloudlink.newEventBuilder()
            .eventType("TEST")
            .eventOperation(EventOperation.Create)
            .toZones("CNR", "USR")
            .module("AA")
            .payload(payload)
            .build();
        cloudlink.publishEvent(event);

        payload = new TestEvent("你好2");
        event = cloudlink.newEventBuilder()
            .eventType("TEST")
            .eventOperation(EventOperation.Retry)
            .toZones("CNR")
            .payload(payload)
            .refEventId("11111111111111")
            .module("AA")
            .retryAppId("OMS1.OMS.ALL.CNR")
            .build();
        cloudlink.publishEvent(event);
//
//        payload = new TestEvent("你好3");
//        event = cloudlink.newEventBuilder()
//            .eventType("TEST")
//            .eventOperation(EventOperation.Success)
//            .toZones("CNR")
//            .payload(payload)
//            .rpcId("11111111111111")
//            .build();
//        cloudlink.publishEvent(event);
//
//        payload = new TestEvent("你好4");
//        event = cloudlink.newEventBuilder()
//            .eventType("TEST")
//            .eventOperation(EventOperation.Failure)
//            .toZones("CNR")
//            .payload(payload)
//            .rpcId("11111111111111")
//            .build();
//        cloudlink.publishEvent(event);

    }
}
