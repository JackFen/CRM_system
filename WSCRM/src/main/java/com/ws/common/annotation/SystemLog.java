package com.ws.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *  被该注解标识的方法，执行的时候会被AOP拦截，然后记录相关的操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    String value() default "";
}
