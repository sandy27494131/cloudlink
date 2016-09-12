package com.winit.cloudlink.command;

import java.util.UUID;

import com.winit.cloudlink.common.Builder;
import com.winit.cloudlink.common.utils.Assert;
import com.winit.cloudlink.config.Metadata;

/**
 * Created by stvli on 2015/11/16.
 */
public class CommandBuilder<Payload> implements Builder<Command<Payload>> {

    private Metadata metadata;
    private String   commandName;
    private String   toAppId;
    private Payload  payload;

    public CommandBuilder(Metadata metadata){
        this.metadata = metadata;
    }

    public CommandBuilder<Payload> commandName(String commnadName) {
        Assert.notBlank(commnadName, "'commandName' cannot be blank.");
        this.commandName = commnadName;
        return this;
    }

    public CommandBuilder<Payload> toAppId(String toAppId) {
        Assert.notBlank(toAppId, "'toAppId' cannot be blank.");
        this.toAppId = toAppId;
        return this;
    }

    public CommandBuilder<Payload> payload(Payload payload) {
        this.payload = payload;
        return this;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Command build() {
        CommandHeaders headers = new CommandHeaders();
        headers.setCommandName(this.commandName);
        headers.setCommandId(generateCommandId());
        headers.setFromAppId(metadata.getApplicationOptions().getAppId2String());
        headers.setToAppId(null != this.toAppId ? this.toAppId.toUpperCase() : this.toAppId);
        headers.setTimestamp(System.currentTimeMillis());
        Command<Payload> command = new Command<Payload>();
        command.setPayload(this.payload);
        command.setHeaders(headers);
        return command;
    }

    private String generateCommandId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
