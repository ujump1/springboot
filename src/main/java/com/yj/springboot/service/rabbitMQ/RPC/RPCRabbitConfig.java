package com.yj.springboot.service.rabbitMQ.RPC;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YJ
 * @createDate  2020/5/28
 * keep
 */
@Configuration
public class RPCRabbitConfig {

    //创建队列
    @Bean
    public Queue topicQueue1() {
        return new Queue("RPC1",true);
    }
    @Bean
    public Queue topicQueue2() {
        return new Queue("RPC2",true);
    }

    //创建交换机
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("RPC",true,false);
    }

    //交换机与队列进行绑定
    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("RPC1");
    }
    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("RPC2");
    }
}
