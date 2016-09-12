package com.winit.cloudlink.command.internal;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.winit.cloudlink.command.CommandExecutor;
import com.winit.cloudlink.command.Command;
import com.winit.cloudlink.config.CommandExecutorOptions;

/**
 * ReferenceFactoryBean
 *
 * @author william.liangf
 * @export
 */
public class CommandExecutorBean<Param> extends CommandExecutorOptions implements FactoryBean<CommandExecutor<Command<Param>>>, ApplicationContextAware {

    private static final long               serialVersionUID = 213195494150089726L;

    private CommandExecutor<Command<Param>> commandExecutor;

    private transient ApplicationContext    applicationContext;

    private transient String                beanName;

    public CommandExecutorBean(CommandExecutor<Command<Param>> commandExecutor){
        super(commandExecutor.getCommandName(), commandExecutor.getClass().getName());
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public CommandExecutor<Command<Param>> getObject() throws Exception {
        return commandExecutor;
    }

    @Override
    public Class<?> getObjectType() {
        return commandExecutor == null ? CommandExecutor.class : commandExecutor.getClass();
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
