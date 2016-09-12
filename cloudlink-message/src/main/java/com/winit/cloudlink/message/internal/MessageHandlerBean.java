package com.winit.cloudlink.message.internal;

import com.winit.cloudlink.config.MessageHandlerOptions;
import com.winit.cloudlink.message.handler.MessageHandler;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;

import java.lang.reflect.Method;


/**
 * ServiceFactoryBean
 *
 * @author william.liangf
 * @export
 */
public class MessageHandlerBean extends MessageHandlerOptions implements InitializingBean, DisposableBean, ApplicationContextAware, ApplicationListener, BeanNameAware {

    private static transient ApplicationContext SPRING_CONTEXT;

    private transient ApplicationContext applicationContext;

    private transient String beanName;

    private transient MessageHandler messageHandler;

    private transient boolean supportedApplicationListener;

    public MessageHandlerBean(MessageHandler messageHandler) {
        super(messageHandler.getMessageType(), messageHandler.getClass());
        this.messageHandler = messageHandler;
    }


    public static ApplicationContext getSpringContext() {
        return SPRING_CONTEXT;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        if (applicationContext != null) {
            SPRING_CONTEXT = applicationContext;
            try {
                Method method = applicationContext.getClass().getMethod("addApplicationListener", new Class<?>[]{ApplicationListener.class}); // 兼容Spring2.0.1
                method.invoke(applicationContext, new Object[]{this});
                supportedApplicationListener = true;
            } catch (Throwable t) {
                if (applicationContext instanceof AbstractApplicationContext) {
                    try {
                        Method method = AbstractApplicationContext.class.getDeclaredMethod("addListener", new Class<?>[]{ApplicationListener.class}); // 兼容Spring2.0.1
                        if (!method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        method.invoke(applicationContext, new Object[]{this});
                        supportedApplicationListener = true;
                    } catch (Throwable t2) {
                    }
                }
            }
        }
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void onApplicationEvent(ApplicationEvent event) {
    }


    @SuppressWarnings({"unchecked", "deprecation"})
    public void afterPropertiesSet() throws Exception {

    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public String getBeanName() {
        return beanName;
    }

    public void destroy() throws Exception {

    }

}