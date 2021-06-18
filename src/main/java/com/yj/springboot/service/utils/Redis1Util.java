package com.yj.springboot.service.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @author YJ
 * @version 1.0.0 2021/6/16
 */
@Component
public class Redis1Util {


	private static RedisTemplate<String, Object> redisTemplate;

	/**
	 * 初始化注入
	 */
	@Autowired
	public Redis1Util(RedisTemplate<String, Object> redisTemplate) {
		Redis1Util.redisTemplate = redisTemplate;
	}


	/**
	 * 获取锁
	 *
	 * @param lockKey      key
	 * @param value        value
	 * @param timeout：释放时间
	 * @param unit：时间单位
	 * @return 是否成功
	 */
	public static Boolean getLock(String lockKey, String value, long timeout, TimeUnit unit) {
		Boolean getLock = redisTemplate.opsForValue().setIfAbsent(lockKey,value,timeout,unit);
		return getLock;
	}


	/**
	 * 释放锁
	 *
	 * @param lockKey
	 * @param value
	 * @return
	 */
	public static Boolean releaseLock(String lockKey, String value) {
		Boolean releaseLock = true;
		if(redisTemplate.opsForValue().get(lockKey).equals(value)){
			 releaseLock = redisTemplate.delete(lockKey);
		}
		return releaseLock;
	}
}