package com.yj.springboot.service.annotation;

import java.lang.annotation.*;

/**
 * <strong>实现功能:</strong>.
 * <p>平台默认检查权限，该注解用于一些不许进行权限检查的方法或类上</p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/5/15 10:59
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface IgnoreCheckAuth {
    boolean value() default true;
}