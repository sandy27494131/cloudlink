package com.winit.cloudlink.spring;

import com.google.common.collect.Lists;
import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.CloudlinkSwitcher;
import com.winit.cloudlink.Configuration;
import com.winit.ubarrier.common.*;
import com.winit.ubarrier.common.status.Health;
import com.winit.ubarrier.common.status.Status;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by stvli on 2015/11/11.
 */
public class CloudlinkSwitcherTest {
    private final String messageType = "ORDER_CREATED";
    private Cloudlink cloudlink;
    private CloudlinkSwitcher cloudlinkSwitcher;
    private static AtomicInteger index = new AtomicInteger(1);

    @Before
    public void before() {
        Properties properties = new Properties();
        properties.setProperty("appId", "OMS1.OMS.ALL.CNR");
        properties.setProperty("appType", "OMS");
        properties.setProperty("zone", "CUR");
        properties.setProperty("mq", "admin:admin123@172.16.2.176:5672");
        cloudlink = new Configuration().configure(properties).getCloudlink();
        cloudlink.registerMessageHandler(new OrderCreateMessageHandler());
        cloudlinkSwitcher = new CloudlinkSwitcher(cloudlink);
    }

    @Test
    public void changeTest() throws InterruptedException {
        Thread.sleep(10000);
        Health status = cloudlinkSwitcher.health();
        System.out.println(status);
        SwitchCommand command = new SwitchCommand(GrayMode.GB);
        command.addSwitchTask(buildJmxResources());
        cloudlinkSwitcher.execute(command);
        Health status2 = cloudlinkSwitcher.health();
        System.out.println(status2);
        Thread.sleep(10000);
        command.setGrayMode(GrayMode.GNONE);
        cloudlinkSwitcher.execute(command);
        Thread.sleep(20000);
    }

    private List<URL> buildJmxResources() {
        List<URL> urls = Lists.newArrayList();
        urls.add(new URL("jmx", "10.99.26.221", 40087).addParameter(Constants.GROUP_KEY, Group.A.name()));
        urls.add(new URL("jmx", "172.16.2.176", 40287).addParameter(Constants.GROUP_KEY, Group.A.name()));
        urls.add(new URL("jmx", "172.16.3.157", 40287).addParameter(Constants.GROUP_KEY, Group.B.name()));
        return urls;
    }
}
