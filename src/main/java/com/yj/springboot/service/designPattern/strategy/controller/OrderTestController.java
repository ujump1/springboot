package com.yj.springboot.service.designPattern.strategy.controller;

import com.yj.springboot.service.designPattern.strategy.entity.OrderInfo;
import com.yj.springboot.service.designPattern.strategy.service.IOrderStrategyService;
import com.yj.springboot.service.designPattern.strategy.service.impl.OrderStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下单接口实现
 * @author YJ
 * @version 2020/10/20 1.0.0
 */
@RestController
public class OrderTestController {

	@Autowired
	private OrderStrategyContext orderStrategyContext;

	@PostMapping("/testStrategy")
	public String testStrategy(@RequestBody OrderInfo orderInfo) {
		IOrderStrategyService orderServiceImpl = orderStrategyContext.getResource(orderInfo);
		String resultTest = orderServiceImpl.preCreateOrder(orderInfo);
		return resultTest;
	}
}
