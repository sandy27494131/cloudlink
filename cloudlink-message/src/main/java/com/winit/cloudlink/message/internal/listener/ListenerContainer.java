package com.winit.cloudlink.message.internal.listener;

import org.springframework.context.ApplicationContext;

import com.winit.cloudlink.common.Lifecycle;

/**
 * Created by stvli on 2015/11/10.
 */
public interface ListenerContainer extends Lifecycle {
    void setApplicationContext(ApplicationContext applicationContext);
}
