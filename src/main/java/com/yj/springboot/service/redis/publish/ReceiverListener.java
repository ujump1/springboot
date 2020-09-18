package com.yj.springboot.service.redis.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * @author YJ
 * createDate 2020/5/28
 */
public class ReceiverListener implements MessageListener {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        doBusiness(message);
    }

    /**
     * 打印 message body 内容
     * @param message
     */
    public void doBusiness(Message message) {
        Object value = stringRedisTemplate.getValueSerializer().deserialize(message.getBody());
        System.out.println("继承MessageListener订阅者consumer message: " + message.toString());
    }

}