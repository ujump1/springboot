package com.yj.springboot.service.designPattern.bridge.impl;


import com.yj.springboot.service.designPattern.bridge.Human;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component("Young")
public class Young implements Human {

	@Override
	public void walk() {
		System.out.println("Young 在正常走路");
	}

	@Override
	public void walk(String place) {
		System.out.println(MessageFormat.format("Young 在{0} 正常走路",place));
	}
}
