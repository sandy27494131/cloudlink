package com.winit.exporter.process;

import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jianke.zhang on 2017/3/31.
 */
public class TestCommandCallback implements CommandCallback<Command<TestPayload>> {

    private static final Logger logger = LoggerFactory.getLogger(TestCommandCallback.class);

    @Override public String getCommandName() {
        return TestPayload.MESSAGE_TYPE;
    }

    @Override public void onCallback(Command<TestPayload> testPayloadCommand) {
        logger.info("==================> command: " + testPayloadCommand.getPayload().getName() );

    }
}
