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

    /**
     * 处理收到的监听消息
     *
     * @param record     消息纪录
     */
    // 这里可以设置id,group_id,默认情况下如果不设置group_id，则会用id重写group_id，除非设置idIsGroup=false
    // 这里的配置会重写kafkaConfig里面的通用配置，比如group_id
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
    @KafkaListener(id = "consumer3",topics = "test",idIsGroup = false)
    public void processMessageForTes(ConsumerRecord<String, String> record) {
        if (Objects.isNull(record)){
            return;
        }
        LogUtil.info("consumer3消费者消费信息 key='{}' content = '{}'", record.key(), record.value());
        String content = record.value();
        return;
    }
}
