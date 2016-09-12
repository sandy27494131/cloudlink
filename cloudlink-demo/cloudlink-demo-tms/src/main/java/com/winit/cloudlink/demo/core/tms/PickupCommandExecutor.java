package com.winit.cloudlink.demo.core.tms;

import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandException;
import com.winit.cloudlink.command.CommandExecutor;
import com.winit.cloudlink.demo.core.PickupInfo;
import com.winit.cloudlink.demo.core.PickupReturnValue;

/**
 * Created by stvli on 2015/11/10.
 */
public class PickupCommandExecutor extends CommandExecutor<Command<PickupInfo>> {

    @Override
    public String getCommandName() {
        return PickupInfo.COMMAND_NAME;
    }

    @Override
    public void onReceive(Command<PickupInfo> command) throws CommandException {

        System.out.println(">>>>>>>>>>>>>>>>>>>execute commnadExecutor");
        PickupInfo pickupInfo = command.getPayload();

        System.out.println(">>>>>>>>>>>>>>>>>>>paramter[area] : " + pickupInfo.getArea());

        PickupReturnValue returnValue = new PickupReturnValue();
        returnValue.setCode("10000001");
        returnValue.setDesc("操作完成, from " + pickupInfo.getArea());

        this.callback(command.getHeaders().getFromAppId(), returnValue);

    }

}
