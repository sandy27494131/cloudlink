package com.winit.cloudlink;

import com.winit.cloudlink.message.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by stvli on 2015/11/11.
 */
public class DefaultCloudlinkTest {
    private final String messageType = "ORDER_CREATED";
    private Cloudlink cloudlink;

    @Before
    public void before() {
        Properties properties = new Properties();
        properties.setProperty("appId", "OMS1.OMS.ALL.CNR");
        properties.setProperty("appType", "OMS");
        properties.setProperty("zone", "CUR");
        properties.setProperty("mq", "guest:guest@172.16.3.164:5672");
        cloudlink = new Configuration().configure(properties).getCloudlink();
    }

    @Test
    public void sendMessageToCnTest() {
        Product product = new Product();
        product.setName("test");
        String toApp = "OMS1.OMS.ALL.CNR";
        Message message = cloudlink.newMessageBuilder().direct(toApp, messageType, product).build();
        cloudlink.sendMessage(message);
    }

    @Test
    public void sendMessageToUsTest() {
        Product product = new Product();
        product.setName("test");
        String toApp = "OMS1.OMS.ALL.CNR";
        Message message = cloudlink.newMessageBuilder().direct(toApp, messageType, product).build();
        cloudlink.sendMessage(message);
    }

    @Test
    public void sendMessageToAuTest() {
        Product product = new Product();
        product.setName("test");
        String toApp = "OMS1.OMS.ALL.CNR";
        Message message = cloudlink.newMessageBuilder().direct(toApp, messageType, product).build();
        cloudlink.sendMessage(message);
    }

}
