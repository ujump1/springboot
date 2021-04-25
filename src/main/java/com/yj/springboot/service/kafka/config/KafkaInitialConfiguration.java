package com.yj.springboot.service.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kafka topic的配置
 */
@Configuration
public class KafkaInitialConfiguration {
	/**
	 * 创建topic,不创建的话在发送消息时也会默认创建一个，只不过分区数只有1个
	 * @return
	 */
	@Bean
	public NewTopic initialTopic() {
		return new NewTopic("test",8, (short) 1 );
	}
}
