package com.yj.springboot.service.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * jackson配置（也可以直接在application.yaml中配置）
 */
@Configuration
public class JacksonConfig {

	@Bean
	public ObjectMapper getObjectMapper() {

		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 或者用自定义的,传入一个默认的日期序列化格式
		//DateFormat dateFormat = objectMapper.getDateFormat();
		//objectMapper.setDateFormat(new StandardDateTimeFormat(dateFormat));

		// 也可以在字段上使用@DateTimeFormat 和 @JsonFormat注解来反序列化和序列化
		//值为空不进行序列化
       //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 忽略json字符串中不识别的属性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 忽略无法转换的对象 “No serializer found for class com.xxx.xxx”
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		return objectMapper;
	}
}

