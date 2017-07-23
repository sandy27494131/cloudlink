
package com.winit.cloudlink.common.extension;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 提供信息用于{@link ExtensionLoader}生成自适应实例（Adaptive Instance）。
 *
 * @author stvliu
 * @see ExtensionLoader
 * @see Extension
 * @since 0.1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface FooAnnotation {
    String[] value() default {};
}
