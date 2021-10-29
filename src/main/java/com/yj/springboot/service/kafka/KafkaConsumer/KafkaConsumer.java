package com.yj.springboot.service.kafka.KafkaConsumer;


import com.yj.springboot.service.utils.LogUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <strong>实现功能:</strong>
 * <p>Kafka消费者</p>
 * 一个分区只能有一个消费者，一个消费者可以有多个分区，当消费者变化时，会重新分配
 * 消息根据key经过计算后放到不同的分区里面
 * @author yj
 * @version 1.0.1 2021/4/23
 */
@Component
public class KafkaConsumer {


    //kafka-consumer-groups.bat --bootstrap-server localhost:9092 --describe --group bootKafka
//    bootKafka       test            6          3               3               0               测试-0-aa74561c-49cb-404d-ba52-c971e303f1f1       /172.168.0.35   测试-0
//    bootKafka       test            0          6               6               0               测试-0-aa74561c-49cb-404d-ba52-c971e303f1f1       /172.168.0.35   测试-0
//    bootKafka       test            7          14              14              0               测试-0-aa74561c-49cb-404d-ba52-c971e303f1f1       /172.168.0.35   测试-0
//    bootKafka       test            5          0               0               0               测试-0-aa74561c-49cb-404d-ba52-c971e303f1f1       /172.168.0.35   测试-0
//    bootKafka       test            1          1               1               0               测试-0-aa74561c-49cb-404d-ba52-c971e303f1f1       /172.168.0.35   测试-0
//    bootKafka       test            4          0               0               0               测试-0-aa74561c-49cb-404d-ba52-c971e303f1f1       /172.168.0.35   测试-0
//    bootKafka       test            3          3               3               0               测试-0-aa74561c-49cb-404d-ba52-c971e303f1f1       /172.168.0.35   测试-0
//    bootKafka       test            2          0               0               0               测试-0-aa74561c-49cb-404d-ba52-c971e303f1f1       /172.168.0.35   测试-0
//    bootKafka       testKafka       0          5               5               0               consumer-1-03ca91e5-b5de-452d-b436-d8e87015f589 /172.168.0.35   consumer-1
    /**
     * 处理收到的监听消息
     *
     * @param record     消息纪录
     */
    // 这里可以设置id,group_id,默认情况下如果不设置group_id，则会用id重写group_id，除非设置idIsGroup=false
    // 这里的配置会重写kafkaConfig里面的通用配置，比如group_id
    // kafka 里面对应的有group consumer-id client-id ,其中group 等于groupId consumer-id client-id都是自动生成的，前缀由clientIdPrefix决定，后面则是自己生成的，和这个注解里面的id没有关系
    @KafkaListener(id = "consumer1",topics = "testKafka",idIsGroup = false)
    public void processMessage(ConsumerRecord<String, String> record) {
        if (Objects.isNull(record)){
            return;
        }
        LogUtil.info("consumer1消费者消费信息 key='{}' content = '{}'", record.key(), record.value());
        String content = record.value();
        return;
    }

    /**
     * 处理收到的监听消息
     *
     * @param record     消息纪录
     */
    // 这里可以设置id,group_id,默认情况下如果不设置group_id，则会用id重写group_id，除非设置idIsGroup=false
    // 这里的配置会重写kafkaConfig里面的通用配置，比如group_id
    @KafkaListener(id = "consumer2",topics = "testKafka",idIsGroup = false)
    public void processMessageForTestKafka(ConsumerRecord<String, String> record) {
        if (Objects.isNull(record)){
            return;
        }
        LogUtil.info("consumer2消费者消费信息 key='{}' content = '{}'", record.key(), record.value());
        String content = record.value();
        return;
    }

    /**
     * 处理收到的监听消息
     *
     * @param record     消息纪录
     */
    // 这里可以设置id,group_id,默认情况下如果不设置group_id，则会用id重写group_id，除非设置idIsGroup=false
    // 这里的配置会重写kafkaConfig里面的通用配置，比如group_id
    @KafkaListener(id = "consumer3",topics = "test",idIsGroup = false,clientIdPrefix = "测试")
    public void processMessageForTes(ConsumerRecord<String, String> record) {
        if (Objects.isNull(record)){
            return;
        }
        LogUtil.info("consumer3消费者消费信息 key='{}' content = '{}'", record.key(), record.value());
        String content = record.value();
        return;
    }
}
