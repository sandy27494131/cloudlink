package com.winit.exporter;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.Configuration;
import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.message.Message;
import com.winit.exporter.process.TestCommandCallback;
import com.winit.exporter.process.TestCommandExecutor;
import com.winit.exporter.process.TestMessageHandler;
import com.winit.exporter.process.TestPayload;

import java.util.Properties;

/**
 * Created by jianke.zhang on 2017/3/31.
 */
public class StartupAUR {
    public static final void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("appId", "EXPORTER_AU.EXPORTER.ALL.AUR");
        properties.setProperty("appType", "EXPORTER");
        properties.setProperty("zone", "AUR");
        properties.setProperty("mq", "admin:admin123@172.16.3.207:5672/au");
        properties.setProperty("valid.destination.queue", "false");
        Cloudlink cloudlink = new Configuration().configure(properties).getCloudlink();

        cloudlink.registerMessageHandler(new TestMessageHandler());

        cloudlink.registerCommandExecutor(new TestCommandExecutor());

        cloudlink.registerCommandCallback(new TestCommandCallback());

        TestPayload payload = new TestPayload("00000000");
        Message<TestPayload> message = cloudlink.newMessageBuilder()
                .direct("EXPORTER_CN.EXPORTER.ALL.CNR", TestPayload.MESSAGE_TYPE, payload)
                .build();
        cloudlink.sendMessage(message);

        Command<TestPayload> command2US = cloudlink.newCommandBuilder()
                .commandName(TestPayload.MESSAGE_TYPE)
                .toAppId("EXPORTER_CN.EXPORTER.ALL.CNR")
                .payload(payload)
                .build();
        cloudlink.commitCommand(command2US);
    }
}
