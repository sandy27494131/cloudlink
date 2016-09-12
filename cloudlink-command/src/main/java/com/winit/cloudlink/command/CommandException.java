package com.winit.cloudlink.command;

/**
 * Created by stvli on 2015/11/10.
 */
public class CommandException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6363776243770995711L;
    private String            commandId;

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public CommandException(){
        super();
    }

    public CommandException(String message){
        super(message);
    }

    public CommandException(String message, Throwable cause){
        super(message, cause);
    }

    public CommandException(Throwable cause){
        super(cause);
    }
}
