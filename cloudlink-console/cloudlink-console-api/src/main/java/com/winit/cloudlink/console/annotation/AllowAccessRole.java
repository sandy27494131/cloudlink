package com.winit.cloudlink.console.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.winit.cloudlink.storage.cassandra.entity.User.Role;

/**
 * 
 * 访问控制注解，方法级覆盖类级
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * jianke.zhang 	1.0  		2016年1月12日 	Created
 *
 * </pre>
 * @since 1.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllowAccessRole {

    Role[] value();
}
