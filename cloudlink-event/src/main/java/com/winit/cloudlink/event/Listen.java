package com.winit.cloudlink.event;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by stvli on 2016/6/3.
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Listen {
    EventOperation[] value();
}
