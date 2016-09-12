package com.winit.cloudlink.message.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * value=true时，消息消费失败将导致消息队列阻塞；反之不阻塞队列，不阻塞队列时消息处理失败将会丢失,需要由业务系统自行处理。
 * 
 * @version <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2015年11月23日 	Created
 *
 * </pre>
 * @since 1.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Block {

    /**
     * value=true时，消息消费失败将阻塞消息队列，反之不阻塞队列
     * 
     * @return
     */
    boolean value() default false;
}
