package com.yj.springboot.service.designPattern.strategy.service.impl;

import com.yj.springboot.service.designPattern.strategy.entity.OrderInfo;
import com.yj.springboot.service.designPattern.strategy.service.IOrderStrategyService;
import org.springframework.stereotype.Component;

/**
 * 特殊折扣实现
 * @author YJ
 * @version 2020/10/20 1.0.0
 */
@Component("Rebate")
public class OrderRebateService implements IOrderStrategyService {

	@Override
	public String preCreateOrder(OrderInfo orderInfo) {
		System.out.println("***处理国内特殊回扣预下单的相关业务***");
		return orderInfo.getPlatFormType()+"-特殊回扣预下单";
	}
}
