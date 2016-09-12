package com.winit.cloudlink.command.internal.command;

import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandCallback;

public class QueryReceptionListCallback implements CommandCallback<Command<QueryReceptionListResult>> {

    @Override
    public String getCommandName() {
        return QueryReceptionListExecutor.COMMAND_NAME;
    }

    @Override
    public void onCallback(Command<QueryReceptionListResult> result) {

        if (result != null) {
            QueryReceptionListResult data = result.getPayload();
            System.out.println("=================>call QueryReceptionListCallback");
        } else {
            throw new NullPointerException();
        }
    }
}
