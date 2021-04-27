package com.yj.springboot.service.kafka.config;


import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * kafka生产者和消费者的配置
 */
@Configuration
public class KafkaConfig {

	//生产者配置
	private Map<String, Object> senderProps (){
		Map<String, Object> props = new HashMap<>();
		//连接地址（集群的话可以用逗号隔开)
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		//重试，0为不启用重试机制
		props.put(ProducerConfig.RETRIES_CONFIG, 1);
		//控制批处理大小，单位为字节
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		//批量发送，延迟为1毫秒，启用该功能能有效减少生产者发送消息次数，从而提高并发量
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		//生产者可以使用的总内存字节来缓冲等待发送到服务器的记录
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1024000);
		//键的序列化方式
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		//值的序列化方式
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		// 可以设置消息分区策略，不设置的话默认是DefaultPartitioner，如果有key,就按key来，没有的话就轮询策略
		// 也可以自己实现一个哈，实现org.apache.kafka.clients.producer.Partitioner接口就行了
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class);
		return props;
	}

	//根据senderProps填写的参数创建生产者工厂
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(senderProps());
	}

	//kafkaTemplate实现了Kafka发送接收等功能
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		KafkaTemplate template = new KafkaTemplate<>(producerFactory());
		return template;
	}


	//消费者通用配置参数
	private Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<>();
		//连接地址（集群的话可以用逗号隔开)
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		//默认的GroupID（可以在具体的消费者里面配,会重写)
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "bootKafka");
		//是否自动提交
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		//自动提交的频率
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		//Session超时设置
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		//键的反序列化方式
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		//值的反序列化方式
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		return props;
	}

	//根据consumerProps填写的参数创建消费者工厂
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerProps());
	}

	//ConcurrentKafkaListenerContainerFactory为创建Kafka监听器的工程类，这里只配置了消费者
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}


	// 创建AdminClient,可以用来操作kafka，比如用来创建和 查询topic
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> props = new HashMap<>();
		//配置Kafka实例的连接地址
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		KafkaAdmin admin = new KafkaAdmin(props);
		return admin;
	}

	@Bean
	public AdminClient adminClient() {
		return AdminClient.create(kafkaAdmin().getConfig());
	}


}
