package com.yj.springboot.service.rabbitMQ;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yj.springboot.entity.User;
import com.yj.springboot.service.rabbitMQ.direct.DirectRabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author YJ
 * @createDate 2020-5-19
 */

@RestController
public class SendMessageController {

    @Autowired
    @Qualifier("firstRabbitTemplate")
    //@Resource(name = "firstRabbitTemplate")
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @Resource(name= "RPC")
    RabbitTemplate rabbitTemplateRPC;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message，hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/sendTopicMessageMan")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
        return "ok";
    }

    @GetMapping("/sendTopicMessageWoman")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "ok";
    }

    // 消息推送到server，但是在server里找不到交换机
    @GetMapping("/TestMessageAck")
    public String TestMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("non-existent-exchange", "TestDirectRouting", map);
        return "ok";
    }

    // 消息推送到server，找到交换机了，但是没找到队列
    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("lonelyDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    // 测试自定义消息结构,user类在消费者和生产者要一模一样，如果使用这个得话
    @GetMapping("/TestMessageUser")
    public String TestMessageUser() {
        User user= new User();
        user.setCode("001");
        user.setName("江大大");
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(user);
            // 如果是list的话
            //String jsonlist = mapper.writeValueAsString(userlist);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
      //  rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", user);
        // string
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", json);

        //还有一种时将string 发送的时候设置setContentType 让他变成map，消费端使用map接收，需要开启RabbitConfig中的SimpleRabbitListenerContainerFactory
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setContentType("application/json");
//        Message message = new Message(json.getBytes(),messageProperties);
//        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", message);

        return "ok";
    }

    // 测试list
    @GetMapping("/TestMessageList")
    public String TestMessageList() {
        List<String> list = new ArrayList<>();
        list.add("hello，this is a test");

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", list);
        return "ok";
    }

    // 测试RPC调用1，使用message
    @GetMapping("/TestMessageRPC")
    public String TestMessageRPC() {
        String s="开始RPC调用";
        byte[] src = s.toString().getBytes(Charset.forName("UTF-8"));
        MessageProperties mp = new MessageProperties();
        mp.setContentType("application/json");
        mp.setContentEncoding("UTF-8");
        mp.setContentLength((long)s.length());
        Message message = new Message(src,mp);

        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
       // Message message1=rabbitTemplateRPC.sendAndReceive("RPC", "RPC1",message);
        // 使用sendAndReceive会把ReplyTo和CorrelationId发送过去。
        // 对于发送Message，可以用send也可以用convertAndSend,但是convertAndSend返回的是一个object,直接是里面的内容,基本是byte，需要转换
        Message returnMessage = rabbitTemplateRPC.sendAndReceive("RPC", "RPC1",message);
        System.out.println("RPC调用收到并回复"+returnMessage.toString());
        return "RPC调用收到回复"+returnMessage.toString();
    }

    // 测试RPC调用2，使用String
    @GetMapping("/TestMessageRPCString")
    public String TestMessageRPC1String() {
        String s=("这是一条RPC调用");
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        // Message message1=rabbitTemplateRPC.sendAndReceive("RPC", "RPC1",message);
        byte[] returnMessage = (byte[]) rabbitTemplateRPC.convertSendAndReceive("RPC", "RPC1",s);
        System.out.println("RPC调用收到并回复"+new String(returnMessage));
        return "RPC调用收到回复"+new String(returnMessage);
    }
}
