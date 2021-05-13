package com.yj.springboot.service.designPattern.bridge.impl;


import com.yj.springboot.service.designPattern.bridge.Human;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component("Young")
public class Young implements Human {

	@Override
	public void walk() {
		System.out.println("Young 正常走路");
	}
}
