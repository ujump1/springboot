package com.yj.springboot.service.rabbitMQ.RPC;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @Author : yj
 * @CreateTime : 2020/5/19
 * @Description :
 **/
@Component

public class RPCReceiver {
    @Autowired
    @Qualifier("firstRabbitTemplate")
    //@Resource(name = "firstRabbitTemplate")
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @RabbitListener(queues = "RPC1")
    @RabbitHandler
    public void process(Message testMessage) {
        System.out.println("RPC消费者1收到消息  : " + testMessage.toString());
        String message = "RPC调用成功";
        byte[] src = message.getBytes(Charset.forName("UTF-8"));
        String id=testMessage.getMessageProperties().getCorrelationId();
        MessageProperties mp = new MessageProperties();
        mp.setContentType("application/json");
        mp.setContentEncoding("UTF-8");
        mp.setCorrelationId(id);
        // 要回复message，因为需要头
        Message message1=new Message(src,mp);
        rabbitTemplate.convertAndSend("RPC",testMessage.getMessageProperties().getReplyTo() ,message1);
    }
}