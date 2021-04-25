package com.yj.springboot.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("kafka")
public class KafkaController {

	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;

	@GetMapping("/sendMessage")
	public String sendMessage(String message) {
		kafkaTemplate.send("testKafka", "test", message);
		return "ok";
	}

	@GetMapping("/sendMessage1")
	public String sendMessage1(String message) {
		kafkaTemplate.send("test", "test", message);
		return "ok";
	}

	@GetMapping("/sendMessage2")
	public String sendMessage2(String message) {
		kafkaTemplate.send("test", "test1", message);
		return "ok";
	}
}
