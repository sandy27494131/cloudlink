package com.winit.cloudlink.command;

import org.junit.Test;

import com.winit.cloudlink.command.internal.command.QueryReceptionListExecutor;
import com.winit.cloudlink.config.CloudlinkOptions;
import com.winit.cloudlink.config.Metadata;

public class CommandReceiveTest {

    public static void main(String[] args) {
        CloudlinkOptions options = new CloudlinkOptions();
        options.setZone("US");
        options.setOrganization("US");
        options.setAppId("tms-us");
        options.setAppType("tms");
        options.setAppVersion("1.0.0");
        options.setMq("mq://guest:guest@127.0.0.1:5672");
        options.setOwner("development");

        Metadata metadata = Metadata.build(options);

        CommandEngine commandEngine = new DefaultCommandEngine(metadata);

        commandEngine.registerCommandExecutor(new QueryReceptionListExecutor(), null);

        commandEngine.start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {

    }

}
