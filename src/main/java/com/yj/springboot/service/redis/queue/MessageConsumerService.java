package com.yj.springboot.service.redis.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author YJ
 * @createDate 2020/5/28
 * 队列消费者(暂时不可用)
 */
@Service
public class MessageConsumerService extends Thread {
    @Autowired
    private RedisTemplate redisTemplate;

    private volatile boolean flag = true;

    //@Value("${redis.queue.key}")
    private String queueKey = "queue";

   // @Value("${redis.queue.pop.timeout}")
    private Long popTimeout = 1000L;

    @Override
    public void run() {
        try {
            String message;
            while(flag && !Thread.currentThread().isInterrupted()) {
                message = (String)redisTemplate.opsForList().rightPop(queueKey, popTimeout, TimeUnit.SECONDS);
                System.out.println("消息队列接收者接收到了" + message);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
