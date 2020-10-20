package com.yj.springboot.service.designPattern.strategy.service;


import com.yj.springboot.service.designPattern.strategy.entity.OrderInfo;

/**
 * 下单的策略模式接口
 */
public interface IOrderStrategyService {

	//预下单
	String  preCreateOrder(OrderInfo orderInfo);

}
