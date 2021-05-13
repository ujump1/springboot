package com.yj.springboot.service.designPattern.factory.controller;

import com.yj.springboot.service.designPattern.bridge.Human;
import com.yj.springboot.service.designPattern.bridge.Move;
import com.yj.springboot.service.designPattern.bridge.MoveInDesert;
import com.yj.springboot.service.designPattern.bridge.MoveInStreet;
import com.yj.springboot.service.designPattern.bridge.impl.HumanStrategyContext;
import com.yj.springboot.service.designPattern.factory.Computer;
import com.yj.springboot.service.designPattern.factory.ComputerFactory;
import com.yj.springboot.service.designPattern.factory.factory.HuaWeiComputerFactory;
import com.yj.springboot.service.designPattern.factory.factory.ThinkPadComputerFactory;
import com.yj.springboot.service.designPattern.strategy.entity.OrderInfo;
import com.yj.springboot.service.designPattern.strategy.service.IOrderStrategyService;
import com.yj.springboot.service.designPattern.strategy.service.impl.OrderStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 电脑服务接口
 * @author YJ
 * @version 2021/5/12 1.0.0
 */
@RestController
public class ComputerTestController {



	// 这是个简单工厂模式，如果要看springboot整合工厂模式，可以去看策略模式和桥接模式下的
	@GetMapping("/testFactory")
	public String testFactory() {
		ComputerFactory computerFactory = new HuaWeiComputerFactory();
		Computer computer = computerFactory.makeComputer();
		computer.calculate();

		ComputerFactory computerFactory1 = new ThinkPadComputerFactory();
		Computer compute1 = computerFactory1.makeComputer();
		compute1.calculate();
		return "ok";
	}
}
