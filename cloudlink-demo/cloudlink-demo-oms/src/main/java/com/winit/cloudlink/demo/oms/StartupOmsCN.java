package com.winit.cloudlink.demo.oms;

import java.util.Properties;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.Configuration;
import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.demo.core.PickupInfo;
import com.winit.cloudlink.demo.oms.command.PickupCommandCallback;
import com.winit.cloudlink.demo.oms.command.PickupCommandExecutor;

public class StartupOmsCN {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("appId", "OMS1.OMS.ALL.CNR");
        properties.setProperty("appType", "OMS");
        properties.setProperty("zone", "CNR");
        properties.setProperty("mq", "admin:admin@172.16.3.184:5672");
        Cloudlink cloudlink = new Configuration().configure(properties).getCloudlink();

        // 注册指令回调函数
        cloudlink.registerCommandCallback(new PickupCommandCallback());

        cloudlink.registerCommandExecutor(new PickupCommandExecutor());

        // 构建一个指令对象（跨数据中心消息传递）

        PickupInfo payload = new PickupInfo();
        payload.setArea("OMS1.OMS.ALL.CNR");
        Command<PickupInfo> command2US = cloudlink.newCommandBuilder()
            .commandName(PickupInfo.COMMAND_NAME)
            .toAppId("OMS1.OMS.ALL.CNR")
            // 必须与目标系统的appId保持一致
            .payload(payload)
            .build(); // 发送指令 to US
        cloudlink.commitCommand(command2US);

        // 构建一个指令对象（数据中心内部消息传递）
        // payload = new PickupInfo();
        // payload.setArea("CN");
        // Command<PickupInfo> command2CN = cloudlink.newCommandBuilder()
        // .commandName(PickupInfo.COMMAND_NAME)
        // .toAppId("TMS.CN")
        // // 必须与目标系统的appId保持一致
        // .payload(payload)
        // .build();
        //
        // // 发送指令 to CN
        // cloudlink.commitCommand(command2CN);

        // payload.setArea("to AU");
        // Command<PickupInfo> command2AU = cloudlink.newCommandBuilder()
        // .commandName(PickupInfo.COMMAND_NAME)
        // .toAppId("TMS.AU")
        // // 必须与目标系统的appId保持一致
        // .payload(payload)
        // .build();
        // // 发送指令 to AU
        // cloudlink.commitCommand(command2AU);

        /*
         * OrderCreateInfo order = new OrderCreateInfo();
         * order.setOrderId("O1000001"); Message<OrderCreateInfo> message =
         * cloudlink.newMessageBuilder() .direct("TMS.US",
         * OrderCreateInfo.COMMAND_NAME, order) .build();
         * cloudlink.sendMessage(message);
         */

    }
}
