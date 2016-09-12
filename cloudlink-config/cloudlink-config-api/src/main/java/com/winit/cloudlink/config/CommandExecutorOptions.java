package com.winit.cloudlink.config;

/**
 * CommandExecutorOptions
 *
 * @author Steven.Liu
 * @export
 */
public class CommandExecutorOptions extends Options {
    // 指令名
    private String commandName;
    //指令类名
    private String commandClassName;
    //指令处理器的appId
    private String workAppId;
    //是否不可用
    private boolean disabled = false;

    public CommandExecutorOptions(String commandName, String commandClassName) {
        this.commandName = commandName;
        this.commandClassName = commandClassName;
    }

    public CommandExecutorOptions(String commandName, String commandClassName, String workAppId) {
        this.commandName = commandName;
        this.commandClassName = commandClassName;
        this.workAppId = workAppId;
    }

    public CommandExecutorOptions(String commandName, String commandClassName, String workAppId, boolean disabled) {
        this.commandName = commandName;
        this.commandClassName = commandClassName;
        this.workAppId = workAppId;
        this.disabled = disabled;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandClassName() {
        return commandClassName;
    }

    public void setCommandClassName(String commandClassName) {
        this.commandClassName = commandClassName;
    }

    public String getWorkAppId() {
        return workAppId;
    }

    public void setWorkAppId(String workAppId) {
        this.workAppId = workAppId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}