package com.yj.springboot.service.redis.publish;


/**
 * @Author : YJ
 * @CreateTime : 2020/5/27
 * @Description :
 * @Point: Keep a good mood
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

/**
 * redis消息处理器
 */
@Component
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    /**
     * 接收到消息的方法，message就是指从主题获取的消息，主题配置在RedisMessageListener配置类做配置
     * @param message
     */
    public void receiveMessage(String message) throws InterruptedException {
        //testService.getData();
        // 模拟业务处理时间
        sleep(3000);
        System.out.println("自定义订阅者Received " + message );
    }
}

