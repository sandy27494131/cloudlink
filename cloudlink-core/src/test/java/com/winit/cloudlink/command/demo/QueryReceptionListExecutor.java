package com.winit.cloudlink.command.demo;

import com.winit.cloudlink.command.CommandExecutor;
import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandException;

public class QueryReceptionListExecutor extends CommandExecutor<Command<QueryReceptionListParam>> {

    public static final String COMMAND_NAME = "queryReceptionList";

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void onReceive(Command<QueryReceptionListParam> param) throws CommandException {

        System.out.println(">>>>>>>>>>>>>>>>>>> commandExecutor start : " + param.getHeaders().getCommandName());
        QueryReceptionListResult data = new QueryReceptionListResult();

        // 去注释，测试指令执行器异常捕获及异常回调接收
        // if (null != result) {
        // throw new CommandException("test executor exception.");
        // }
        this.callback(param.getHeaders().getFromAppId(), data);
    }

}
