package com.winit.cloudlink.command.internal;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.command.CommandCallback;
import com.winit.cloudlink.config.CommandExecutorOptions;

/**
 * ReferenceFactoryBean
 *
 * @author william.liangf
 * @export
 */
public class CommandExecutorCallbackBean<Payload> extends CommandExecutorOptions implements FactoryBean<CommandCallback<Command<Payload>>>, ApplicationContextAware {

    private static final long                 serialVersionUID = 213195494150089726L;

    private transient ApplicationContext      applicationContext;

    private CommandCallback<Command<Payload>> commandCallback;

    private transient String                  beanName;

    public CommandExecutorCallbackBean(CommandCallback<Command<Payload>> commandCallback){
        super(commandCallback.getCommandName(), commandCallback.getClass().getName());
        this.commandCallback = commandCallback;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public CommandCallback<Command<Payload>> getObject() throws Exception {
        return commandCallback;
    }

    @Override
    public Class<?> getObjectType() {
        return commandCallback == null ? CommandCallback.class : commandCallback.getClass();
    }

    public boolean isSingleton() {
        return true;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
