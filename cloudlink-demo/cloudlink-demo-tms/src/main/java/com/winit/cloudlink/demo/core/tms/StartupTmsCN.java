package com.winit.cloudlink.demo.core.tms;

import java.util.Properties;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.Configuration;

public class StartupTmsCN {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("appId", "TMS1.TMS.ALL.CNR");
        properties.setProperty("appType", "TMS");
        properties.setProperty("zone", "CNR");
        properties.setProperty("mq", "guest:guest@172.16.3.164:5672");
        Cloudlink cloudlink = new Configuration().configure(properties).getCloudlink();

        // 注册指令执行函数
        cloudlink.registerCommandExecutor(new PickupCommandExecutor());

    }
}
