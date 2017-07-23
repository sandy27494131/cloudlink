package com.winit.robot.command;

import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandCallback;
import com.winit.cloudlink.command.CommandHeaders;
import com.winit.cloudlink.common.AppID;
import com.winit.cloudlink.message.annotation.Block;
import com.winit.robot.core.RobotInfo;
import com.winit.robot.core.RobotReturnValue;
import com.winit.robot.scheduler.CommandAlertScheduler;
import com.winit.robot.utils.DateTimes;
//import com.winit.robot.utils.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


/**
 * Created by stvli on 2015/11/10.
 */
@Block(false)
@Lazy(false)
@Component
public class RobotCommandCallback implements CommandCallback<Command<RobotReturnValue>> {
    @Autowired
    private CommandAlertScheduler commandAlertScheduler;

    @Override
    public void onCallback(Command<RobotReturnValue> command) {
        log(command);
        commandAlertScheduler.onReceive(command);
    }

    public void log(Command<RobotReturnValue> command) {
        CommandHeaders headers = command.getHeaders();
        RobotReturnValue payload = command.getPayload();
        Date now = new Date();
        long diffMillis = DateTimes.diffMillis(payload.getCreated(), now);
        String fromZone = new AppID(command.getHeaders().getFromAppId()).getArea();
        String msg = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                "1",
                fromZone,
                headers.getCommandName(),
                headers.getCommandId(),
                headers.getFromAppId(),
                headers.getToAppId(),
                DateTimes.format(headers.getTimestamp()),
                payload.getTraceNo(),
                payload.getRefTraceNo(),
                DateTimes.format(payload.getCreated()),
                DateTimes.format(payload.getRefCreated()),
                DateTimes.format(now),
                diffMillis);
        getLogger(fromZone).info(msg);
    }

    @Override
    public String getCommandName() {
        return RobotInfo.COMMAND_NAME;
    }

    private Logger getLogger(String fromZone) {
        return LoggerFactory.getLogger("robot-data-" + fromZone);
    }
}
