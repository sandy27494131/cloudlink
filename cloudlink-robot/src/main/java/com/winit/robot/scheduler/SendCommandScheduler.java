package com.winit.robot.scheduler;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.common.AppID;
import com.winit.robot.core.RobotInfo;
import com.winit.robot.utils.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by stvli on 2017/3/28.
 */
@Component
public class SendCommandScheduler {
    @Resource
    private Cloudlink cloudlink;

    @Value("${spring.cloudlink.robot.toAppIds}")
    private String toAppIds;

    @Scheduled(fixedRate = 1000 * 30)//每30秒执行一次
    public void sendCommand() {
        String[] appIds = splitAppId(toAppIds);
        if (null == appIds && appIds.length == 0) {
            return;
        }
        for (String appId : appIds) {
            cloudlink.commitCommand(buildCommand(new AppID(appId)));
        }
    }

    private Command<RobotInfo> buildCommand(AppID appID) {
        RobotInfo payload = new RobotInfo();
        payload.setTraceNo(IdGenerator.nextId(appID.getArea()));
        payload.setCreated(new Date());
        Command<RobotInfo> command = cloudlink.newCommandBuilder()
                .commandName(payload.COMMAND_NAME)
                .toAppId(appID.toString()) // 必须与目标系统的appId保持一致
                .payload(payload)
                .build();
        return command;
    }

    private String[] splitAppId(String toAppIds) {
        return StringUtils.split(toAppIds, ",");
    }
}
