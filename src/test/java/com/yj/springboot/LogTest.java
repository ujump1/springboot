package com.yj.springboot;

import com.yj.springboot.service.service.UserServiceImpl;
import com.yj.springboot.service.utils.LogUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LogTest extends BaseTest {

	@Autowired
	UserServiceImpl userService;
	@Test
	public void logTest(){
		userService.testLog();
		System.out.println("123");
	}
}
