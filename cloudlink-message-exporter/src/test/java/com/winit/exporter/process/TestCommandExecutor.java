package com.winit.exporter.process;

import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandException;
import com.winit.cloudlink.command.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jianke.zhang on 2017/3/31.
 */
public class TestCommandExecutor extends CommandExecutor<Command<TestPayload>> {

    private static final Logger logger = LoggerFactory.getLogger(TestCommandExecutor.class);

    @Override public String getCommandName() {
        return TestPayload.MESSAGE_TYPE;
    }

    @Override public void onReceive(Command<TestPayload> testPayloadCommand) throws CommandException {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> messageHandler: " + testPayloadCommand.getHeaders().getCommandId());
        logger.info("==================> command: " + testPayloadCommand.getPayload().getName() );
        this.callback(testPayloadCommand.getHeaders().getFromAppId(), testPayloadCommand.getPayload());
    }
}
