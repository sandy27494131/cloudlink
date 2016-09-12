package com.winit.cloudlink.command;

import java.io.Serializable;

/**
 * Created by stvli on 2015/11/12.
 */
public class CommandHeaders implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -472631452104482760L;
    private String            commandName;
    private String            fromAppId;
    private String            toAppId;
    private String            commandId;
    private long              timestamp;

    public String getCommandName() {
        return this.commandName + Constants.COMMAND_SUFFIX;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getToAppId() {
        return toAppId;
    }

    public void setToAppId(String toAppId) {
        this.toAppId = toAppId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getFromAppId() {
        return fromAppId;
    }

    public void setFromAppId(String fromAppId) {
        this.fromAppId = fromAppId;
    }
}
