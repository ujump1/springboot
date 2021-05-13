package com.yj.springboot.service.designPattern.strategy.service.impl;

import com.yj.springboot.service.designPattern.strategy.entity.OrderInfo;
import com.yj.springboot.service.designPattern.strategy.service.IOrderStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工厂模式
 * @author YJ
 * @version 2020/10/20 1.0.0
 */
@Service
public class OrderStrategyContext {
	@Autowired
	private final Map<String, IOrderStrategyService> orderStrategyMap = new ConcurrentHashMap<>();

	/**这个可要可不要**/
//	public OrderStrategyContext(Map<String, IOrderStrategyService> strategyMap) {
//		this.orderStrategyMap.clear();
//		strategyMap.forEach((k, v)-> this.orderStrategyMap.put(k, v));
//	}

	public IOrderStrategyService getResource(OrderInfo orderInfo){
		return orderStrategyMap.get(orderInfo.getPlatFormType());
	}
}
