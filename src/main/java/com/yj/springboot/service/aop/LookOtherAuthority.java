package com.yj.springboot.service.aop;

import java.lang.annotation.*;

/**
 * 查看他人权限注解
 * @author yj
 * @version 1.0
 * @date 2021/6/18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LookOtherAuthority {
    /**
     * 字段名
     */
    String name() default "";
}
