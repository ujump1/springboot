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

	@Override
	public void walk(String place) {
		System.out.println(MessageFormat.format("Old 在{0} 拄拐杖走路",place));
	}
}
