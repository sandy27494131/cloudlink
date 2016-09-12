package com.winit.cloudlink.command;

import java.util.Properties;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.Configuration;
import com.winit.cloudlink.command.demo.QueryReceptionListCallback;
import com.winit.cloudlink.command.demo.QueryReceptionListExecutor;
import com.winit.cloudlink.command.demo.QueryReceptionListParam;

public class CommandSendTest {

    private static Cloudlink cloudlink;

    private static void init() {
        Properties properties = new Properties();
        properties.setProperty("appId", "oms-cn");
        properties.setProperty("appType", "OMS");
        properties.setProperty("zone", "IDC-GZ");
        properties.setProperty("mq", "guest:guest@127.0.0.1:5672");
        cloudlink = new Configuration().configure(properties).getCloudlink();
    }

    public static void main(String[] args) {

        init();

        // registerCallback
        cloudlink.registerCommandCallback(new QueryReceptionListCallback());

        QueryReceptionListParam param = new QueryReceptionListParam();
        Command<QueryReceptionListParam> command = cloudlink.newCommandBuilder()
            .commandName(QueryReceptionListExecutor.COMMAND_NAME)
            .toAppId("tms-us")
            .payload(param)
            .build();

        cloudlink.start();
        try {
            int i = 10;
            while (i-- > 0) {
                cloudlink.commitCommand(command);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
