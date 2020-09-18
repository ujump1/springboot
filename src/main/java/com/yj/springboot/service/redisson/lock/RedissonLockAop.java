package com.yj.springboot.service.redisson.lock;


import com.alibaba.fastjson.JSONObject;

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
 * 分布式锁的 aop
 */
@Aspect
@Component
@Order(1) //使用@Order注解指定切面的优先级，值越小优先级越高,越先进去，越后出来（不知道是否可以理解为执行）
public class RedissonLockAop {

    /**
     * 切点，拦截被 @RedissonLockAnnotation 修饰的方法
     */
    @Pointcut("@annotation(com.yj.springboot.service.redisson.lock.RedissonLockAnnotation)")
    public void redissonLockPoint() {
    }

    @Around("redissonLockPoint()")
    @ResponseBody
    public Object checkLock(ProceedingJoinPoint pjp) throws Throwable {
        //当前线程名
        String threadName = Thread.currentThread().getName();
        System.out.println(MessageFormat.format("线程{0}------进入分布式锁aop------",threadName));
        //获取参数值列表
        Object[] objs = pjp.getArgs();
        // 获取参数名列表
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        //获取该注解的实例对象
        RedissonLockAnnotation annotation = ((MethodSignature) pjp.getSignature()).
                getMethod().getAnnotation(RedissonLockAnnotation.class);
        // 生成分布式锁key的键名，以逗号分隔
        String lockRedisKey = annotation.lockRedisKey();
        // 等待时间
        long waitTime = annotation.waitTime();
        // 生成分布式锁key的键名，以逗号分隔
        long leaseTime = annotation.leaseTime();
        // 生成分布式锁key的键名，以逗号分隔
        TimeUnit timeUnit  = annotation.timeUnit();
        if (StringUtils.isEmpty(lockRedisKey)) {
            System.out.println(MessageFormat.format("线程{0} lockRedisKey设置为空，不加锁",threadName));
            return pjp.proceed();
        } else {
            //生成分布式锁key
            String key = "";
            int i = 0;
            for(String parameterName : parameterNames ){
                if(parameterName.equals(lockRedisKey)){
                    key = (String) objs[i];
                    break;
                }
                i++;
            }
            System.out.println(MessageFormat.format("线程{0} 锁的key={1}", threadName,key));
            if (RedissonLockUtils.tryLock(key, waitTime, leaseTime, timeUnit)) {
                try {
                    System.out.println(MessageFormat.format("线程{0} 获取锁成功", threadName));

                    return  pjp.proceed();
                } finally {
                    if (RedissonLockUtils.isLocked(key)) {
                        System.out.println(MessageFormat.format("key={0}对应的锁被持有,线程{1}",key, threadName));

                        if (RedissonLockUtils.isHeldByCurrentThread(key)) {
                            System.out.println(MessageFormat.format("当前线程 {0} 保持锁定",threadName));
                            RedissonLockUtils.unlock(key);
                            System.out.println(MessageFormat.format("线程{1} 释放锁", threadName));

                        }

                    }


                }
            } else {
                System.out.println(MessageFormat.format("线程{0} 获取锁失败", threadName));
                return " GET LOCK FAIL";
            }
        }


    }
}

