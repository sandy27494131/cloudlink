package com.winit.cloudlink.command.internal.command;

import com.winit.cloudlink.command.CommandExecutor;
import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandException;

public class QueryReceptionListExecutor extends CommandExecutor<Command<QueryReceptionListParam>> {

    public static final String COMMAND_NAME = "queryReceptionListCmd";

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void onReceive(Command<QueryReceptionListParam> param) throws CommandException {

        System.out.println(">>>>>>>>>>>>>>>>>>> commandExecutor start : " + param.getHeaders().getCommandName());
        QueryReceptionListResult data = new QueryReceptionListResult();

    }

}
