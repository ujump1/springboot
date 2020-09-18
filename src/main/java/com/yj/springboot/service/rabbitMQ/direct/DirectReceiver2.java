package com.yj.springboot.service.rabbitMQ.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author : yj
 * @CreateTime : 2020/5/19
 * @Description :
 **/
@Component
@RabbitListener(queues = "TestDirectQueue")
public class DirectReceiver2 {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("DirectReceiver消费者2收到消息  : " + testMessage.toString());
    }

}
