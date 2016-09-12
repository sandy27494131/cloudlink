package com.winit.cloudlink.spring;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

import com.winit.cloudlink.Cloudlink;
import com.winit.cloudlink.Configuration;
import com.winit.cloudlink.command.CommandExecutor;
import com.winit.cloudlink.command.CommandCallback;
import com.winit.cloudlink.common.utils.StringUtils;
import com.winit.cloudlink.config.Metadata;
import com.winit.cloudlink.event.EventListener;
import com.winit.cloudlink.message.MessageReturnedListener;
import com.winit.cloudlink.message.handler.MessageHandler;

public class CloudlinkFactoryBean implements FactoryBean<Cloudlink>, DisposableBean, InitializingBean, BeanPostProcessor, ApplicationContextAware {

    private static final String     KEY_ENABLE_MESSAGE_LISTENER = "enableMessageListener";

    private ApplicationContext      ApplicationContext;

    private Cloudlink               cloudlink;

    private String                  configLocation;

    private Properties              cloudlinkProperties;

    private Configuration           configuration;

    private boolean                 enableMessageListener       = true;

    private MessageReturnedListener messageReturnedListener;

    @Override
    public void setApplicationContext(ApplicationContext ApplicationContext) throws BeansException {
        this.ApplicationContext = ApplicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MessageHandler) {
            MessageHandler<?> messageHandler = (MessageHandler<?>) bean;
            if (StringUtils.isBlank(messageHandler.getMessageType())) {
                throw new BeanCreationException("the messageType property of '" + beanName + "' must not be blank.");
            }
        }
        if (bean instanceof CommandExecutor) {
            CommandExecutor<?> commandExecutor = (CommandExecutor<?>) bean;
            if (StringUtils.isBlank(commandExecutor.getCommandName())) {
                throw new BeanCreationException("the name property of '" + beanName + "' must not be blank.");
            }
        }
        if (bean instanceof CommandCallback) {
            CommandCallback<?> commandCallback = (CommandCallback<?>) bean;
            if (StringUtils.isBlank(commandCallback.getCommandName())) {
                throw new BeanCreationException("the commandName property of '" + beanName + "' must not be blank.");
            }
        }
        if (bean instanceof EventListener) {
            EventListener<?> commandCallback = (EventListener<?>) bean;
            if (StringUtils.isBlank(commandCallback.getEventName())) {
                throw new BeanCreationException("the eventName property of '" + beanName + "' must not be blank.");
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (enableMessageListener) {
            if (bean instanceof MessageHandler) {
                MessageHandler<?> messageHandler = (MessageHandler<?>) bean;
                cloudlink.registerMessageHandler(messageHandler);
                // MessageHandlerBean messageHandlerConfig = new
                // MessageHandlerBean(messageHandler);
                // getMetadata().addMessageHandlerOptions(messageHandlerConfig);
            }
            if (bean instanceof CommandExecutor) {
                CommandExecutor commandExecutor = (CommandExecutor) bean;
                cloudlink.registerCommandExecutor(commandExecutor);
                // CommandExecutorBean commandConfig = new
                // CommandExecutorBean(commandExecutor);
                // getMetadata().addCommandExecutorOptions(commandConfig);
            }
            if (bean instanceof CommandCallback) {
                CommandCallback<?> commandCallback = (CommandCallback<?>) bean;
                cloudlink.registerCommandCallback(commandCallback);
            }
            if (bean instanceof EventListener) {
                EventListener<?> eventListener = (EventListener<?>) bean;
                cloudlink.registerEventListener(eventListener);
            }
        }
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties properties = null;
        if (!StringUtils.isEmpty(this.configLocation)) {
            Resource resource = new ClassPathResource(configLocation);
            properties = PropertiesLoaderUtils.loadProperties(resource);

            // 是否启用消息监听
            if (null != properties) {
                String enabledListenter = properties.getProperty(KEY_ENABLE_MESSAGE_LISTENER, "true");
                this.enableMessageListener = Boolean.valueOf(enabledListenter);
            }

        } else {
            properties = new Properties();
        }
        if (null != cloudlinkProperties) {
            CollectionUtils.mergePropertiesIntoMap(cloudlinkProperties, properties);
        }
        configuration = new Configuration().configure(properties);
        cloudlink = configuration.getCloudlink();
        cloudlink.setMessageReturnedListener(messageReturnedListener);

    }

    @Override
    public void destroy() throws Exception {
        if (null != cloudlink) {
            cloudlink.shutdown();
        }
    }

    @Override
    public Cloudlink getObject() throws Exception {
        return cloudlink;
    }

    @Override
    public Class<?> getObjectType() {
        return null == cloudlink ? Cloudlink.class : cloudlink.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public Properties getCloudlinkProperties() {
        return cloudlinkProperties;
    }

    public void setCloudlinkProperties(Properties cloudlinkProperties) {
        this.cloudlinkProperties = cloudlinkProperties;
    }

    public Metadata getMetadata() {
        return configuration.getMetadata();
    }

    public boolean isEnableMessageListener() {
        return enableMessageListener;
    }

    public void setEnableMessageListener(boolean enableMessageListener) {
        this.enableMessageListener = enableMessageListener;
    }

    public void setMessageReturnedListener(MessageReturnedListener messageReturnedListener) {
        this.messageReturnedListener = messageReturnedListener;
    }

}
