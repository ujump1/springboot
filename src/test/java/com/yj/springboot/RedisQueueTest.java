package com.yj.springboot;

import com.yj.springboot.service.redis.queue.MessageConsumerService;
import com.yj.springboot.service.redis.queue.MessageProducerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisQueueTest extends BaseTest{
    @Autowired
    private MessageProducerService producer;

    @Autowired
    private MessageConsumerService consumer;

    /**
     * 测试
     */
    @Test
    public void test(){
        System.out.println("1234");
    }


}
