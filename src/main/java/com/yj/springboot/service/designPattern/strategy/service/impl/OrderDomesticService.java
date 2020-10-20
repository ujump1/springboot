package com.yj.springboot.service.designPattern.strategy.service.impl;

import com.yj.springboot.service.designPattern.strategy.entity.OrderInfo;
import com.yj.springboot.service.designPattern.strategy.service.IOrderStrategyService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 国内订单实现
 * @author YJ
 * @version 2020/10/20 1.0.0
 */
@Component("Domestic")
public class OrderDomesticService implements IOrderStrategyService {

	@Override
	public String preCreateOrder(OrderInfo orderInfo) {
		System.out.println("***处理国内预下单的相关业务***");
		return orderInfo.getPlatFormType()+"-国内预下单";
	}
}
