package com.yj.springboot.service.designPattern.bridge.impl;


import com.yj.springboot.service.designPattern.bridge.Human;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component("Old")
public class Old implements Human {

	@Override
	public void walk() {
		System.out.println("Old 拄拐杖走路");
	}
}
