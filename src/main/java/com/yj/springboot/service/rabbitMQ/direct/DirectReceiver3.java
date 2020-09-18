package com.yj.springboot.service.rabbitMQ.direct;

import com.rabbitmq.client.Channel;
import com.yj.springboot.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author : yj
 * @CreateTime : 2020/5/19
 * @Description :
 **/

// 不同的消息处理，收到的消息是map的话只有第二个会获取到因为类型不对，不会获取到的，而Message则可以收到全部类型的消息
    // @RabbitListener放在类上和方法上的区别就是以这个为例，放在类上表示这几个方法对应一个消费者，根据最先匹配到的类型来处理，
    // 放在方法上表示多个消费者了。
@Component
@RabbitListener(queues = "TestDirectQueue")
public class DirectReceiver3 {

    // 如果接收的是message的话， @RabbitListener需要放在发方法上
    @RabbitListener(queues = "TestDirectQueue")
    @RabbitHandler
    public void process(Message testMessage, Channel channel) {
        long deliveryTag = testMessage.getMessageProperties().getDeliveryTag();
        System.out.println("DirectReceiver消费者3收到消息message  : " + testMessage);
    }
    @RabbitHandler
    public void process(Map testMessage, Channel channel) {
        System.out.println("DirectReceiver消费者3收到消息map  : " + testMessage.toString());
    }

    @RabbitHandler
    public void process(String testMessage, Channel channel) {
        System.out.println("DirectReceiver消费者3收到消息String  : " + testMessage);
    }

    // 测试自定义Message(User)
    @RabbitHandler
    public void process(User testMessage, Channel channel) {
        System.out.println("DirectReceiver消费者3收到消息user  : " + testMessage.toString());
    }

    // List
    @RabbitHandler
    public void process(List<String> testMessage, Channel channel) {
        System.out.println("DirectReceiver消费者3收到消息list  : " + testMessage.toString());
    }
}
