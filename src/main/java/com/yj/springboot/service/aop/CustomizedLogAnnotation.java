package com.yj.springboot.service.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 异常收集注解
 */
@Target(ElementType.METHOD) //注解在方法
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomizedLogAnnotation {

}

