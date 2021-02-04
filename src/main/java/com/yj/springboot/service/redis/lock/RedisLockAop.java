package com.yj.springboot.service.redis.lock;



import com.yj.springboot.service.utils.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁的 aop(同一个用户访问同时只能访问同一个方法，使用注解中的lockRedisKey和用户账号锁定)
 */
@Aspect
@Component
@Order(1) //使用@Order注解指定切面的优先级，值越小优先级越高,越先进去，越后出来（不知道是否可以理解为执行）
public class RedisLockAop {

    /**
     * 切点，拦截被 @RedisLockAnnotation 修饰的方法
     */
    @Pointcut("@annotation(com.yj.springboot.service.redis.lock.RedisLockAnnotation)")
    public void redisLockPoint() {
    }

    @Around("redisLockPoint()")
    @ResponseBody
    public Object checkLock(ProceedingJoinPoint pjp) throws Throwable {
        //当前线程名
        String threadName = Thread.currentThread().getName();
        //获取参数值列表
        Object[] objs = pjp.getArgs();
        // 获取参数名列表
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        // 拼接参数
        String parameter = "";
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for(String parameterName : parameterNames ){
            stringBuilder.append(parameterName).append(":").append((String)objs[i]);
            i++;
        }
        parameter = stringBuilder.toString();
        //获取该注解的实例对象
        RedisLockAnnotation annotation = ((MethodSignature) pjp.getSignature()).
                getMethod().getAnnotation(RedisLockAnnotation.class);
        // 生成分布式锁key的键名，以逗号分隔
        String lockRedisKey = annotation.lockRedisKey();
        // 等待时间（暂时不用)
        long waitTime = annotation.waitTime();
        // 释放时间
        long leaseTime = annotation.leaseTime();
        // 生成分布式锁key的键名，以逗号分隔
        TimeUnit timeUnit  = annotation.timeUnit();
        if (StringUtils.isEmpty(lockRedisKey)) {
          System.out.println(MessageFormat.format("线程{0}lockRedisKey设置为空，不加锁",threadName));
            // 直接执行业务操作
            return pjp.proceed();
        } else {
            //生成分布式锁key
            String key = "";
            String userId = "test";
            key = lockRedisKey+userId;
            // 使用当前线程id加锁
            String value = String.valueOf(Thread.currentThread().getId());
            if (RedisUtil.getLock(key, value,leaseTime, timeUnit)) {
                try {
                    System.out.println(MessageFormat.format("线程{0},key:{1},参数{2}获取锁成功", threadName,key,parameter));
                    // 执行业务操作
                    return  pjp.proceed();
                } finally {
                    // 释放锁
                    RedisUtil.releaseLock(key, value);
                }
            } else {
                System.out.println(MessageFormat.format("线程{0},key:{1},参数{2}获取锁成功", threadName,key,parameter));
                return "操作正在执行中，请稍后重试！";
            }
        }
    }
}

