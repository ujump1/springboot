package com.yj.springboot.service.designPattern.bridge.impl;


import com.yj.springboot.service.designPattern.bridge.Human;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component("Baby")
public class Baby implements Human {

	@Override
	public void walk() {
		System.out.println("Baby 在爬行");
	}

	@Override
	public void walk(String place) {
		System.out.println(MessageFormat.format("Baby 在{0} 爬行",place));
	}
}
