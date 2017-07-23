package com.winit.cloudlink.demo.oms.command;

import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandCallback;
import com.winit.cloudlink.command.CommandHeaders;
import com.winit.cloudlink.demo.core.PickupInfo;
import com.winit.cloudlink.demo.core.PickupReturnValue;

/**
 * Created by stvli on 2015/11/10.
 */
public class PickupCommandCallback implements CommandCallback<Command<PickupReturnValue>> {

    @Override
    public void onCallback(Command<PickupReturnValue> finishedResult) {
        CommandHeaders headers = finishedResult.getHeaders();
        PickupReturnValue value = finishedResult.getPayload();

        System.out.println(">>>>>>>>>>>>>>>>>>>start handler callback");
        System.out.println(">>>>>>>>>>>>>>>>>>>code : " + value.getCode());
        System.out.println(">>>>>>>>>>>>>>>>>>>desc : " + value.getDesc());
        System.out.println(">>>>>>>>>>>>>>>>>>>end handler callback");
    }

    @Override
    public String getCommandName() {
        return PickupInfo.COMMAND_NAME;
    }
}
