package com.yj.springboot.service.rabbitMQ.direct;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
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
public class DirectReceiver {
    // 只监听MAP的，不是map的比如字符串，list，user过来会报错，然后由下一个消费者取消费。
    @RabbitHandler
        public void process(Map testMessage) {
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
    }
}
