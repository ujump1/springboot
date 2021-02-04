package com.yj.springboot.service.redis.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁自定义注解
 */
@Target(ElementType.METHOD) //注解在方法
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLockAnnotation {

    /**
     * 指定组成分布式锁的key
     */
    String lockRedisKey();

    /**
     * 等待时间
     */
    long waitTime();

    /**
     * 释放时间
     */
    long leaseTime();

    /**
     * 时间单位
     */
    TimeUnit timeUnit();
}

