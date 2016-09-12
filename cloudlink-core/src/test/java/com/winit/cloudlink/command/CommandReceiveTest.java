package com.winit.cloudlink.command;

import java.util.Properties;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.Configuration;
import com.winit.cloudlink.command.demo.QueryReceptionListExecutor;

public class CommandReceiveTest {

    private static Cloudlink cloudlink;

    private static void init() {
        Properties properties = new Properties();
        properties.setProperty("appId", "tms-us");
        properties.setProperty("appType", "TMS");
        properties.setProperty("zone", "IDC-GZ");
        properties.setProperty("mq", "guest:guest@127.0.0.1:5672");
        cloudlink = new Configuration().configure(properties).getCloudlink();
    }

    public static void main(String[] args) {

        init();

        // registerExecutor
        cloudlink.registerCommandExecutor(new QueryReceptionListExecutor());

        cloudlink.start();
        try {
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
