package com.winit.cloudlink.demo.core.tms;

import java.util.Properties;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.Configuration;

public class StartupTmsUS {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("appId", "TMS1.TMS.ALL.USR");
        properties.setProperty("appType", "TMS");
        properties.setProperty("zone", "USR");
        properties.setProperty("mq", "guest:guest@172.16.3.165:5672");
        Cloudlink cloudlink = new Configuration().configure(properties).getCloudlink();

        // 注册指令执行函数
        cloudlink.registerCommandExecutor(new PickupCommandExecutor());

        // 注册消息执行函数
        cloudlink.registerMessageHandler(new OrderCreateMessageHandler());

    }
}
