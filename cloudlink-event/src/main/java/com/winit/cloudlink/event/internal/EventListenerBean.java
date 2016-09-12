package com.winit.cloudlink.event.internal;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.winit.cloudlink.event.EventListener;
import com.winit.cloudlink.event.Event;
import com.winit.cloudlink.config.CommandExecutorOptions;

/**
 * ReferenceFactoryBean
 *
 * @author william.liangf
 * @export
 */
public class EventListenerBean<Param> extends CommandExecutorOptions implements FactoryBean<EventListener<Event<Param>>>, ApplicationContextAware {

    private static final long               serialVersionUID = 213195494150089726L;

    private EventListener<Event<Param>> eventListener;

    private transient ApplicationContext    applicationContext;

    private transient String                beanName;

    public EventListenerBean(EventListener<Event<Param>> eventListener){
        super(eventListener.getEventName(), eventListener.getClass().getName());
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public EventListener<Event<Param>> getObject() throws Exception {
        return eventListener;
    }

    @Override
    public Class<?> getObjectType() {
        return eventListener == null ? EventListener.class : eventListener.getClass();
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
