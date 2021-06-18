package com.yj.springboot;


import com.yj.springboot.service.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class UserTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void testTransactional(){

		userService.testTransactional();
		System.out.println("123");
	}

	@Test
	public void testTransactiona2(){

		userService.testTran1();
		System.out.println("123");
	}

}
