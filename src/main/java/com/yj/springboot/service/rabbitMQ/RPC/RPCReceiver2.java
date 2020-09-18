package com.yj.springboot.service.rabbitMQ.RPC;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author : yj
 * @CreateTime : 2020/5/19
 * @Description :
 **/
// 因为定义的交换机是主题交换机，RPC调用算一个消费者，如果开启这个消费者的话，就会依次获取到信息消费，这样RPC消费者第二次就会获取不到回复了，所以这个要注释掉
//@Component
//public class RPCReceiver2{
//
//    @RabbitListener(queues = "RPC2")
//    @RabbitHandler
//    public void process(Message testMessage) {
//        System.out.println("RPC消费者2收到消息  : " + testMessage.toString());
//    }
//}