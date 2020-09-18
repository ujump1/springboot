package com.yj.springboot.service.redis.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author YJ
 * @createDate 2020/5/28
 * 消息队列生产者
 */
@Service
public class MessageProducerService {
    @Autowired
    private RedisTemplate redisTemplate;

   // @Value("${redis.queue.key}")
    private String queueKey= "queue";

    public Long sendMeassage(String message) {
        System.out.println("发送了" + message);
        return redisTemplate.opsForList().leftPush(queueKey, message);
    }

}
