package com.winit.robot.command;

import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandException;
import com.winit.cloudlink.command.CommandExecutor;
import com.winit.cloudlink.command.CommandHeaders;
import com.winit.cloudlink.common.AppID;
import com.winit.cloudlink.message.annotation.Block;
import com.winit.robot.core.RobotInfo;
import com.winit.robot.core.RobotReturnValue;
import com.winit.robot.scheduler.CommandAlertScheduler;
import com.winit.robot.utils.DateTimes;
import com.winit.robot.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by stvli on 2015/11/10.
 */
@Block(false)
@Lazy(false)
@Component
public class RobotCommandExecutor extends CommandExecutor<Command<RobotInfo>> {
    @Autowired
    private CommandAlertScheduler commandAlertScheduler;

    @Override
    public String getCommandName() {
        return RobotInfo.COMMAND_NAME;
    }

    @Override
    public void onReceive(Command<RobotInfo> command) throws CommandException {
        log(command);
        callback(command.getHeaders().getFromAppId(), buildReturnValue(command));
        commandAlertScheduler.onReceive(command);
    }

    private RobotReturnValue buildReturnValue(Command<RobotInfo> command) {
        RobotReturnValue returnValue = new RobotReturnValue();
        returnValue.setTraceNo(IdGenerator.nextId(getMetadata().getApplicationOptions().getAppId().getArea()));
        returnValue.setCreated(new Date());
        returnValue.setRefTraceNo(command.getPayload().getTraceNo());
        returnValue.setRefCreated(command.getPayload().getCreated());
        return returnValue;
    }

    public void log(Command<RobotInfo> command) {
        CommandHeaders headers = command.getHeaders();
        RobotInfo payload = command.getPayload();
        Date now = new Date();
        long diffMillis = DateTimes.diffMillis(payload.getCreated(), now);
        String fromZone = new AppID(command.getHeaders().getFromAppId()).getArea();
        String msg = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                "0",
                fromZone,
                headers.getCommandName(),
                headers.getCommandId(),
                headers.getFromAppId(),
                headers.getToAppId(),
                DateTimes.format(headers.getTimestamp()),
                payload.getTraceNo(),
                "",
                DateTimes.format(payload.getCreated()),
                "",
                DateTimes.format(now),
                diffMillis);
        getLogger(fromZone).info(msg);

    }

    private Logger getLogger(String fromZone) {
        return LoggerFactory.getLogger("robot-data-"+fromZone);
    }
}
