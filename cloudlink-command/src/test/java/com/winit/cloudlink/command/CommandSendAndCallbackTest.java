package com.winit.cloudlink.command;

import org.junit.Test;

import com.winit.cloudlink.command.internal.command.QueryReceptionListCallback;
import com.winit.cloudlink.command.internal.command.QueryReceptionListExecutor;
import com.winit.cloudlink.command.internal.command.QueryReceptionListParam;
import com.winit.cloudlink.config.CloudlinkOptions;
import com.winit.cloudlink.config.Metadata;

public class CommandSendAndCallbackTest {

    public static void main(String[] args) {
        CloudlinkOptions options = new CloudlinkOptions();
        options.setZone("CN");
        options.setOrganization("CN");
        options.setAppId("oms-cn");
        options.setAppType("oms");
        options.setAppVersion("1.0.0");
        options.setMq("mq://guest:guest@127.0.0.1:5672");
        options.setOwner("development");

        Metadata metadata = Metadata.build(options);

        CommandEngine commandEngine = new DefaultCommandEngine(metadata);

        // registerCallback
        commandEngine.registerCommandCallback(new QueryReceptionListCallback(), null);

        QueryReceptionListParam param = new QueryReceptionListParam();
        CommandHeaders headers = new CommandHeaders();
        headers.setCommandName(QueryReceptionListExecutor.COMMAND_NAME);
        headers.setToAppId("tms-us");
        headers.setTimestamp(System.currentTimeMillis());
        headers.setCommandId("123");
        Command<QueryReceptionListParam> command = new Command<QueryReceptionListParam>();
        command.setPayload(param);
        command.setHeaders(headers);

        commandEngine.start();

        try {
            while (true) {
                commandEngine.commitCommand(command);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {

    }

}
