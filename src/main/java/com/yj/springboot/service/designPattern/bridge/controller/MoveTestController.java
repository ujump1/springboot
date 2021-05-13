package com.yj.springboot.service.designPattern.bridge.controller;

import com.yj.springboot.service.designPattern.bridge.Human;
import com.yj.springboot.service.designPattern.bridge.Move;
import com.yj.springboot.service.designPattern.bridge.MoveInDesert;
import com.yj.springboot.service.designPattern.bridge.MoveInStreet;
import com.yj.springboot.service.designPattern.bridge.impl.HumanStrategyContext;
import com.yj.springboot.service.designPattern.strategy.entity.OrderInfo;
import com.yj.springboot.service.designPattern.strategy.service.IOrderStrategyService;
import com.yj.springboot.service.designPattern.strategy.service.impl.OrderStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 行走服务接口
 * @author YJ
 * @version 2021/5/12 1.0.0
 */
@RestController
public class MoveTestController {

	@Autowired
	private HumanStrategyContext humanStrategyContext;


	// 桥接模式主要用于两个不相关的维度的可以独立扩展，并且其中一个要使用另外一个的方法
    // 桥接模式和策略模式的不一样的地方在于：
    // 策略模式就是有一个接口，然后会有不同的实现，用的时候根据代码什么的获取需要使用哪个实现方法。
    // 桥接模式包含了策略模式，可以手动赋值用哪个策略。
	@GetMapping("/testBridge")
	public String testStrategy(String code) {
		Human human = humanStrategyContext.getResource(code);
		Move move = new MoveInDesert();
		// 可以设置用哪种人
		move.setHuman(human);
		move.walkInPlace();

		Move move1 = new MoveInStreet();
		move1.setHuman(human);
		move1.walkInPlace();


		return "ok";
	}
}
