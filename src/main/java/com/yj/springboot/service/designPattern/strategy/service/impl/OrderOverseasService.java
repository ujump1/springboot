package com.yj.springboot.service.designPattern.strategy.service.impl;

import com.yj.springboot.service.designPattern.strategy.entity.OrderInfo;
import com.yj.springboot.service.designPattern.strategy.service.IOrderStrategyService;
import org.springframework.stereotype.Component;

/**
 * 海外订单实现
 * @author YJ
 * @version 2020/10/20 1.0.0
 */
@Component("Overseas")
public class OrderOverseasService implements IOrderStrategyService {

	@Override
	public String preCreateOrder(OrderInfo orderInfo) {
		System.out.println("***处理海外预下单的相关业务***");
		return orderInfo.getPlatFormType()+"-海外预下单";
	}
}
