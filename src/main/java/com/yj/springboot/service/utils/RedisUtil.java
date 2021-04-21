package com.yj.springboot.service.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类(手写的)
 * @author YJ
 * @version 1.0.0 2021/1/12
 */
@Component
// 需要注入service的话必须加@Component。如果在static静态方法中使用注入的service的话，必须要用本例中的注入方式，
//                                      如果不在static静态方法中使用注入的service中话，直接使用@Autowired注入即可
public class RedisUtil {
	private static final Long SUCCESS = 1L;

	private static StringRedisTemplate redisTemplate;

	/**
	 * 初始化注入
	 */
	@Autowired
	public RedisUtil(StringRedisTemplate redisTemplate) {
		RedisUtil.redisTemplate = redisTemplate;
	}

	/**
	 * 获取锁
	 *
	 * @param lockKey
	 * @param value
	 * @param expireTime：单位-秒
	 * @return SET key value [EX seconds] [PX millisecounds] [NX|XX]
	 * EX seconds:设置键的过期时间为second秒
	 * PX millisecounds:设置键的过期时间为millisecounds 毫秒
	 * NX:只在键不存在的时候,才对键进行设置操作
	 * XX:只在键已经存在的时候,才对键进行设置操作
	 * SET操作成功后,返回的是OK,失败返回null
	 */

	/**
	 * 获取锁
	 *
	 * @param lockKey      key
	 * @param value        value
	 * @param timeout：超时时间
	 * @param unit：时间单位
	 * @return 是否成功
	 */
	public static Boolean getLock(String lockKey, String value, long timeout, TimeUnit unit) {
		// redisTemplate.opsForValue().setIfAbsent(lockKey,value,timeout,unit); 这一句相当于下面这些操作
		if (!TimeUnit.MILLISECONDS.equals(unit)) {
			timeout = TimeoutUtils.toMillis(timeout, unit);
		}
		/**
		 * SET key value [EX seconds] [PX millisecounds] [NX|XX]
		 * EX seconds:设置键的过期时间为second秒
		 * PX millisecounds:设置键的过期时间为millisecounds 毫秒
		 * NX:只在键不存在的时候,才对键进行设置操作
		 * XX:只在键已经存在的时候,才对键进行设置操作
		 * SET操作成功后,返回的是OK,失败返回null
		 */
		String script = "return redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2], 'NX')";
		RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
		String result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value, String.valueOf(timeout));
		return "ok".equalsIgnoreCase(result);
	}


	/**
	 * 释放锁
	 *
	 * @param lockKey
	 * @param value
	 * @return
	 */
	public static Boolean releaseLock(String lockKey, String value) {
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
		Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value);
		return SUCCESS.equals(result);
	}
}