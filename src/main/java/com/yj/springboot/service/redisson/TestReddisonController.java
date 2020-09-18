package com.yj.springboot.service.redisson;


import com.alibaba.fastjson.JSONObject;
import com.yj.springboot.service.redisson.lock.RedissonLockAnnotation;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * @Author : yj
 * @CreateTime : 2020/5/13
 * @Description :
 **/
@RestController
public class TestReddisonController {

    @Autowired
    private RedissonClient redissonClient;

    @PostMapping(value = "testLock")
    @RedissonLockAnnotation(lockRedisKey = "orderNo",waitTime = 1000, leaseTime = 5000, timeUnit = TimeUnit.MILLISECONDS)
    public String testLock(String id,String orderNo ) throws InterruptedException {
        /**
         * 分布式锁key=orderNo;
         */
        //TODO 业务处理

        System.out.println("接收到的参数："+id+orderNo);
        System.out.println("执行相关业务...");
        System.out.println("执行相关业务.....");
        sleep(10000);
        System.out.println("执行相关业务......");

        return "success";
    }


}
