package com.winit.cloudlink.config;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Steven.Liu on 2015/11/7.
 */
public class Metadata implements Serializable {
    public static final String KEY_CURRENT_ZONE = "currentZone";
    public static final String KEY_APPLICATION_OPTIONS = "applicationOptions";
    public static final String KEY_COMMAND_EXECUTOR_OPTIONSES = "commandExecutorOptionses";
    public static final String KEY_MESSAGE_HANDLER_OPTIONSES = "messageHandlerOptionses";
    public static final String KEY_AVAILABLE_ZONES = "availableZones";


    private Properties properties = new Properties();

    public Metadata(Properties properties) {
        this.properties = properties;
    }

    public ZoneOptions getCurrentZone() {
        return (ZoneOptions) properties.get(KEY_CURRENT_ZONE);
    }

    public void setCurrentZone(ZoneOptions currentZone) {
        properties.put(KEY_CURRENT_ZONE, currentZone);
    }

    public ApplicationOptions getApplicationOptions() {
        return (ApplicationOptions) properties.get(KEY_APPLICATION_OPTIONS);
    }

    public void setApplicationOptions(ApplicationOptions application) {
        properties.put(KEY_APPLICATION_OPTIONS, application);
    }

    // begin CommandExecutorOptionse's method
    public Map<String, CommandExecutorOptions> getCommandExecutorOptionses() {
        return (Map<String, CommandExecutorOptions>) properties.get(KEY_COMMAND_EXECUTOR_OPTIONSES);
    }

    public void setCommandExecutorOptionses(Map<String, CommandExecutorOptions> commandExecutorOptionses) {
        properties.put(KEY_COMMAND_EXECUTOR_OPTIONSES, commandExecutorOptionses);
    }

    public CommandExecutorOptions getCommandExecutorOptions(String commandName) {
        return getCommandExecutorOptionses().get(commandName);
    }

    public void addCommandExecutorOptions(CommandExecutorOptions commandExecutorOptions) {
        getCommandExecutorOptionses().put(commandExecutorOptions.getCommandName(), commandExecutorOptions);
    }

    public void removeCommandExecutorOptions(String commandName) {
        getCommandExecutorOptionses().remove(commandName);
    }// end CommandExecutorOptionse's method

    //begin MessageHandlerOptions's method
    public Map<String, MessageHandlerOptions> getMessageHandlerOptionses() {
        return (Map<String, MessageHandlerOptions>) properties.get(KEY_MESSAGE_HANDLER_OPTIONSES);
    }

    public void setMessageHandlerOptionses(Map<String, MessageHandlerOptions> messageHandlerOptionses) {
        properties.put(KEY_MESSAGE_HANDLER_OPTIONSES, messageHandlerOptionses);
    }

    public MessageHandlerOptions getMessageHandlerOptions(String messageType) {
        return getMessageHandlerOptionses().get(messageType);
    }

    public void addMessageHandlerOptions(MessageHandlerOptions messageHandlerOptions) {
        getMessageHandlerOptionses().put(messageHandlerOptions.getMessageType(), messageHandlerOptions);
    }

    public void removeMessageHandlerOptions(String messageType) {
        getMessageHandlerOptionses().remove(messageType);
    }//end MessageHandlerOptions's method

    //begin ZoneOptions's method
    public Map<String, ZoneOptions> getAvailableZones() {
        return (Map<String, ZoneOptions>) properties.get(KEY_AVAILABLE_ZONES);
    }

    public void setAvailableZones(Map<String, ZoneOptions> availableZones) {
        properties.put(KEY_MESSAGE_HANDLER_OPTIONSES, availableZones);
    }

    public ZoneOptions getZoneOptions(String zoneName) {
        return getAvailableZones().get(zoneName);
    }

    public void addZoneOptions(ZoneOptions zoneOptions) {
        getAvailableZones().put(zoneOptions.getName(), zoneOptions);
    }

    public void removeZoneOptions(String zoneName) {
        getAvailableZones().remove(zoneName);
    }//end ZoneOptions's method

    public static Metadata build(CloudlinkOptions options) {
        Metadata metadata = new Metadata(options.getProperties());
        metadata.setCurrentZone(ZoneOptions.build(options));
        metadata.setApplicationOptions(ApplicationOptions.build(options));
        return metadata;
    }
}
