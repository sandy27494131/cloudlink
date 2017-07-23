package com.winit.cloudlink.spring;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.CloudlinkSwitcher;
import com.winit.cloudlink.Configuration;
import com.winit.cloudlink.message.Message;
import org.junit.Test;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by stvli on 2015/11/11.
 */
public class CloudlinkSenderTest2 {
    private Cloudlink cloudlink;

    @Test
    public void testSend() {
        Properties properties = new Properties();
        properties.setProperty("appId", "OMS1.OMS.ALL.CNR");
        properties.setProperty("appType", "OMS");
        properties.setProperty("zone", "CUR");
        properties.setProperty("mq", "admin:admin123@172.16.2.176:5672/ubarrier");
        cloudlink = new Configuration().configure(properties).getCloudlink();
        while (true) {
            sendMessage();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage() {
        OrderCreateInfo value = new OrderCreateInfo();
        value.setOrderId("OrderId_" + new Random().nextInt());
        Message message = cloudlink.newMessageBuilder().direct("OMS1.OMS.ALL.CNR", OrderCreateInfo.MESSAGE_TYPE, value).build();
        cloudlink.sendMessage(message);
    }
}
