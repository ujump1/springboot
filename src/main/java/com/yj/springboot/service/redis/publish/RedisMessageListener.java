package com.yj.springboot.service.redis.publish;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @Author : Yj
 * @CreateTime : 2020/5/27
 * @Description :
 * @Point: Keep a good mood
 **/
@Configuration
public class RedisMessageListener {

    // 话题
    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("chat");
    }

    //消费者
    @Bean
    public ReceiverListener consumeRedis() {
        return new ReceiverListener();
    }

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫chat 的通道
        // 这里使用的是适配器，自定义消费者receiver，指定方法为handleMessage，不指定的话默认就是handleMessage,推荐使用这种
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
        // 也可以使用这样
       // container.addMessageListener(listenerAdapter,topic());
        //这个container 可以添加多个 messageListener
        //container.addMessageListener(listenerAdapter, new PatternTopic("这里是监听的通道的名字"));
        // 还有一种是直接添加消费者,topic 可以用集合表示多个
        container.addMessageListener(consumeRedis(),topic());
        return container;
    }
    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
        //receiveMessage就是对应消费者那边的消费方法吗,而Receiver是自己弄的一个消费者类
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }


}
