package com.yj.springboot.service.designPattern.bridge.impl;

import com.yj.springboot.service.designPattern.bridge.Human;
import com.yj.springboot.service.designPattern.strategy.entity.OrderInfo;
import com.yj.springboot.service.designPattern.strategy.service.IOrderStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * springboot整合工厂模式，这里的资源是实现接口的类
 * @author YJ
 * @version 2021/5/12 1.0.0
 */
@Service
public class HumanStrategyContext {
	@Autowired
	private final Map<String, Human> humanStrategyMap = new ConcurrentHashMap<>();

	/**这个可要可不要**/
//	public OrderStrategyContext(Map<String, IOrderStrategyService> strategyMap) {
//		this.orderStrategyMap.clear();
//		strategyMap.forEach((k, v)-> this.orderStrategyMap.put(k, v));
//	}

	public Human getResource(String code){
		return humanStrategyMap.get(code);
	}
}
