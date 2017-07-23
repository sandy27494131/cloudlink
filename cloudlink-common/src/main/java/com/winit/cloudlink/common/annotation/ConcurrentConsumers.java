package com.winit.cloudlink.common.annotation;

import java.lang.annotation.*;

/**
 * 
 * 云链并发消费注解
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2016年4月6日 	Created
 *
 * </pre>
 * @since 1.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ConcurrentConsumers {

    /**
     * value值为并发消费数量，必须大于等于1，所有小于1的值默认都当做1来处理。
     * @return
     */
    int value() default 1;
}
